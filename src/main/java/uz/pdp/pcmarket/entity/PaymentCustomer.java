package uz.pdp.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "payment_customer")
public class PaymentCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    private Customer customer;

    @OneToMany
    private Set<Product> products;

    @Column(nullable = false, name = "created_at")
    private Date createdDate;

    @Column(nullable = false)
    private Double price;
}
