package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.pcmarket.entity.Currency;
import uz.pdp.pcmarket.projection.AbsEntityProjection;

@RepositoryRestResource(path = "currency", excerptProjection = AbsEntityProjection.class)
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
