package uz.itransition.personalcollectionmanagement.entity;


import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "custom_fields")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"field_name","collection_id"})
})
public class CustomField extends AbsEntity {

    @Column(name = "field_name")
    private String fieldName;

//    @Enumerated(EnumType.STRING)
    private String fieldType;

    @ManyToOne
    @JoinColumn(name = "collection_id")
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
