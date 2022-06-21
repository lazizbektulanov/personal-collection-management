package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tags")
public class Tag extends AbsEntity {

    @Column(nullable = false)
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return getId() != null && Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
