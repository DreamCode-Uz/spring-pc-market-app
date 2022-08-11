package uz.pdp.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.pcmarket.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    private final AttachmentService service;

    @Autowired
    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> files(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return service.getFiles(page, size);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
        return service.uploadFile(request, builder);
    }

    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<?> download(@PathVariable("attachmentId") Integer id, HttpServletResponse response) {
        return service.downloadFile(id, response);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<?> delete(@PathVariable("attachmentId") Integer id) {
        return service.deleteFile(id);
    }
}
