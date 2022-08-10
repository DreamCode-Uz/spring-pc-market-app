package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
