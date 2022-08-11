package uz.pdp.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] bytes;

    @ManyToOne(optional = false)
    private Attachment attachment;

    public AttachmentContent(byte[] bytes, Attachment attachment) {
        this.bytes = bytes;
        this.attachment = attachment;
    }
}
