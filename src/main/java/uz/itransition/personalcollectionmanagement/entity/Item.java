package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;


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

    @ManyToOne
    private ItemCollection collection;

    @ManyToMany
    @ToString.Exclude
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(name = "items_likes",
    joinColumns = @JoinColumn(name = "item_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<User> likes;

    public Item(String name, ItemCollection collection, List<Tag> tags, List<User> likes) {
        this.name = name;
        this.collection = collection;
        this.tags = tags;
        this.likes = likes;
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
