package uz.itransition.personalcollectionmanagement.entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
//@AllArgsConstructor
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

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public CustomField(UUID fieldId,String fieldName, String fieldType, Collection collection) {
        super(fieldId);
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.collection = collection;
    }
}
