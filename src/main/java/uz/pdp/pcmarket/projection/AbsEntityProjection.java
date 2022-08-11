package uz.pdp.pcmarket.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.pcmarket.entity.Currency;
import uz.pdp.pcmarket.entity.Measurement;

@Projection(name = "abs", types = {Measurement.class, Currency.class})
public interface AbsEntityProjection {
    Integer getId();

    String getName();

    boolean isActive();
}
