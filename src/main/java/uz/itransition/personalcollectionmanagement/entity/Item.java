package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "items")
public class Item extends AbsEntity {

    @Column(nullable = false)
    private String name;

    private String imgUrl;


    @ManyToMany
    @ToString.Exclude
    private List<Tag> tags;

    @CreatedBy
    @ManyToOne(optional = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(name = "items_likes",
    joinColumns = @JoinColumn(name = "item_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<User> likes;

    @ManyToOne
    private Collection collection;


    public Item(String name, List<Tag> tags, User createdBy, List<User> likes, Collection collection) {
        this.name = name;
        this.tags = tags;
        this.createdBy = createdBy;
        this.likes = likes;
        this.collection = collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return getId() != null && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
