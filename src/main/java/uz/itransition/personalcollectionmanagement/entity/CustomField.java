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

}
