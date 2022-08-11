package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pcmarket.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
