package uz.pdp.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, name = "content_type")
    private String contentType;

    public Attachment(String name, Long size, String contentType) {
        this.name = name;
        this.size = size;
        this.contentType = contentType;
    }
}
