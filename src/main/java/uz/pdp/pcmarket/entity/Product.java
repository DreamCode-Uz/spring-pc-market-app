package uz.pdp.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.pcmarket.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "product")
public class Product extends AbsEntity {

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Measurement measurement;

    public Product(Integer id, String name, boolean active, Double price, Double amount, Currency currency, Measurement measurement) {
        super(id, name, active);
        this.price = price;
        this.amount = amount;
        this.currency = currency;
        this.measurement = measurement;
    }
    public Product(String name, boolean active, Double price, Double amount, Currency currency, Measurement measurement) {
        super(name, active);
        this.price = price;
        this.amount = amount;
        this.currency = currency;
        this.measurement = measurement;
    }
}
