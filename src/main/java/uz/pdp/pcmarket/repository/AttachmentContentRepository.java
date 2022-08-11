package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pcmarket.entity.AttachmentContent;

import java.util.Optional;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    Optional<AttachmentContent> findByAttachment_Id(Integer attachment_id);
}
