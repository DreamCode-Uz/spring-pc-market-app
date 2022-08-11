package uz.pdp.pcmarket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.pcmarket.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
public class Currency extends AbsEntity {

    public Currency(String name, boolean active) {
        super(name, active);
    }
}
