package uz.pdp.pcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryDTO {
    @NotNull(message = "name must not be null")
    private String name;

    private boolean active;

    private Integer categoryId;
}
