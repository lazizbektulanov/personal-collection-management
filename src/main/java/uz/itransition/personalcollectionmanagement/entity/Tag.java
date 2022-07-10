package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Indexed
@Entity(name = "tags")
public class Tag extends AbsEntity {

    @Field
    @Column(nullable = false)
    private String name;

//    @ManyToMany(mappedBy = "tags")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Item> itemTags;

//    @PreRemove
//    private void remove(){
//        for (Item itemTag : itemTags) {
//            itemTag.getTags().remove(this);
//        }
//    }
//
}
