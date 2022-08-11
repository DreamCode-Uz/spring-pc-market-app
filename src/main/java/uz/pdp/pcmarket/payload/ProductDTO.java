package uz.pdp.pcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductDTO {

    @NotBlank(message = "The name must not be null or empty")
    private String name;
    private boolean active;
    @NotBlank(message = "price must not be null or empty")
    private Double price;
    @NotBlank(message = "Amount must not be null or empty")
    private Double amount;
    @NotBlank(message = "currencyId must not be null or empty")
    private Integer currencyId;
    @NotBlank(message = "measurementId must not be null or empty")
    private Integer measurementId;
}
