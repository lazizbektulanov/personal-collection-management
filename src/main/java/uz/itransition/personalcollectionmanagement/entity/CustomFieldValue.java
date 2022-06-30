package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "custom_field_values")
public class CustomFieldValue extends AbsEntity {

    private String fieldValue;

    @ManyToOne
    private Item item;

    @ManyToOne
    private CustomField customField;


}
