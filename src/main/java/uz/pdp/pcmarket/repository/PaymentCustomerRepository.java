package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pcmarket.entity.PaymentCustomer;

@Repository
public interface PaymentCustomerRepository extends JpaRepository<PaymentCustomer, Integer> {
}
