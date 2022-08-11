package uz.pdp.pcmarket.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.pcmarket.entity.ProductProperties;

@Projection(types = {ProductProperties.class})
public interface ProductPropertyProjection {

    Integer getId();

    String getColor();

    Double getWeight();
}
