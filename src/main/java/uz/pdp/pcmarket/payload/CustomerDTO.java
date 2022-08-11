package uz.pdp.pcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CustomerDTO {
    @NotBlank(message = "fullName must not be null or empty")
    private String fullName;

    @NotBlank(message = "phoneNumber must not be null or empty")
    private String phoneNumber;

    @NotBlank(message = "email must not be null or empty")
    private String email;

    @NotNull(message = "customersId must not be null or empty")
    private Set<Integer> productsId;
}
