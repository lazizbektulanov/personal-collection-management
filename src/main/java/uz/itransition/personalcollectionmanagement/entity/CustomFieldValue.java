package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private CustomField customField;


}
