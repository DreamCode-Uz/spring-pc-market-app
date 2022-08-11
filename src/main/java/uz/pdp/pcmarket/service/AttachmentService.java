package uz.pdp.pcmarket.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.pcmarket.entity.Attachment;
import uz.pdp.pcmarket.entity.AttachmentContent;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.response.ApiError;
import uz.pdp.pcmarket.payload.response.AttachmentResponse;
import uz.pdp.pcmarket.repository.AttachmentContentRepository;
import uz.pdp.pcmarket.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FAILED_DEPENDENCY;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Service
public class AttachmentService {
    private final AttachmentRepository repository;
    private final AttachmentContentRepository contentRepository;

    @Value(value = "${attachment.download.url}")
    private String baseUrl;

    @Autowired
    public AttachmentService(AttachmentRepository repository, AttachmentContentRepository contentRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
    }

    public ResponseEntity<?> getFiles(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> uploadFile(MultipartHttpServletRequest request, ServletUriComponentsBuilder builder) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file == null) throw new NotFoundException("File Not Found");
        Attachment attachment = new Attachment(null, file.getOriginalFilename(), file.getSize(), file.getContentType());
        attachment = repository.save(attachment);
        AttachmentContent content;
        try {
            content = new AttachmentContent(file.getBytes(), attachment);
        } catch (IOException e) {
            repository.delete(attachment);
            return new ResponseEntity<>(new ApiError(FAILED_DEPENDENCY, "Attachment content error", e.getLocalizedMessage()), FAILED_DEPENDENCY);
        }
        contentRepository.save(content);
        String downloadUrl = builder.path(baseUrl).path(attachment.getId().toString()).toUriString();
        return new ResponseEntity<>(new AttachmentResponse(attachment.getName(), attachment.getContentType(), attachment.getSize(), downloadUrl), CREATED);
    }

    @SneakyThrows
    public ResponseEntity<?> downloadFile(Integer fileId, HttpServletResponse response) {
        Optional<Attachment> optional = repository.findById(fileId);
        if (!optional.isPresent()) throw new NotFoundException("Attachment not found");
        Attachment attachment = optional.get();
        Optional<AttachmentContent> optionalContent = contentRepository.findByAttachment_Id(fileId);
        if (!optionalContent.isPresent()) throw new NotFoundException("Attachment content not found");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"");
        response.setContentType(attachment.getContentType());
        FileCopyUtils.copy(optionalContent.get().getBytes(), response.getOutputStream());
        return ok().build();
    }

    public ResponseEntity<?> deleteFile(Integer id) {
        Optional<Attachment> optionalAttachment = repository.findById(id);
        if (!optionalAttachment.isPresent()) throw new NotFoundException("Attachment not found");
        Optional<AttachmentContent> contentOptional = contentRepository.findByAttachment_Id(id);
        if (contentOptional.isPresent()) {
            contentRepository.delete(contentOptional.get());
            repository.delete(optionalAttachment.get());
        }
        return noContent().build();
    }
}
