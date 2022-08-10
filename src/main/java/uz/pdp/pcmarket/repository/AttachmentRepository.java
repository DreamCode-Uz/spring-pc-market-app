package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
