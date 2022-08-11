package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.pcmarket.entity.ProductProperties;
import uz.pdp.pcmarket.projection.ProductPropertyProjection;

@RepositoryRestResource(path = "product-prop", collectionResourceRel = "properties", excerptProjection = ProductPropertyProjection.class)
public interface ProductPropertiesRepository extends JpaRepository<ProductProperties, Integer> {
}
