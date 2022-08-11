package uz.pdp.pcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {

    @NotBlank(message = "The name must not be null or empty")
    private String name;
    private boolean active;
    @NotNull(message = "price must not be null")
    private Double price;
    @NotNull(message = "Amount must not be null")
    private Double amount;
    @NotNull(message = "currencyId must not be null")
    private Integer currencyId;
    @NotNull(message = "measurementId must not be null")
    private Integer measurementId;
}
