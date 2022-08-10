package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
