package uz.pdp.pcmarket.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentResponse {
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String downloadUrl;
}
