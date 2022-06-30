package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment extends AbsEntity {

    @Column(columnDefinition = "text")
    private String body;

    @ManyToOne
    private User commentFrom;

    @ManyToOne
    private Item item;

}
