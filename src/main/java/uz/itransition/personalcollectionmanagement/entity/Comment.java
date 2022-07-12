package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.search.annotations.*;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Indexed
@Entity(name = "comments")
public class Comment extends AbsEntity {

    @Field
    @Column(columnDefinition = "text")
    private String body;

    @ManyToOne
    private User commentFrom;

    @ContainedIn
    @ManyToOne
    private Item item;

}
