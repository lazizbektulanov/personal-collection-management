package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.*;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Indexed
@Entity(name = "items")
public class Item extends AbsEntity {


    @Field(name = "name", store = Store.YES)
    @Column(nullable = false)
    private String name;

    private String imgUrl;

    @IndexedEmbedded
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @IndexedEmbedded
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User createdBy;

    @IndexedEmbedded
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection collection;

    @IndexedEmbedded
    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    private List<Comment> comments;


    public Item(String name, List<Tag> tags, User createdBy, Collection collection) {
        this.name = name;
        this.tags = tags;
        this.createdBy = createdBy;
        this.collection = collection;
    }

}
