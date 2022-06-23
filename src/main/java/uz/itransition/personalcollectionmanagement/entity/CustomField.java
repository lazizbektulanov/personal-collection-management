package uz.itransition.personalcollectionmanagement.entity;


import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.enums.CustomFieldType;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "custom_fields")
public class CustomField extends AbsEntity {

    private String fieldName;

    private String value;

    @Enumerated(EnumType.STRING)
    private CustomFieldType fieldType;

    @ManyToOne
    private Collection collection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomField that = (CustomField) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
