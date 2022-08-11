package uz.pdp.pcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {
    @NotBlank(message = "name must not be null or empty")
    private String name;
    private boolean active;
    @NotBlank(message = "categoryId must not be null or empty")
    private Integer categoryId;
}
