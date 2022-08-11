package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.pcmarket.entity.Measurement;
import uz.pdp.pcmarket.projection.AbsEntityProjection;

@RepositoryRestResource(path = "measurement", excerptProjection = AbsEntityProjection.class)
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
