package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.ProductProperties;

public interface ProductPropertiesRepository extends JpaRepository<ProductProperties, Integer> {
}
