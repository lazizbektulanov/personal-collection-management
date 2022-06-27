package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.itransition.personalcollectionmanagement.entity.enums.TopicName;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "collections")
public class Collection extends AbsEntity {

    @Column(nullable = false)
    private String title;

    private String imgUrl;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TopicName topicName;


    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @Column(nullable = false)
    private boolean isAccessible = true;



    public Collection(String title, String description, TopicName topicName, User owner) {
        this.title = title;
        this.description = description;
        this.topicName = topicName;
        this.owner = owner;
    }



    public Collection(String title, String imgUrl, String description, TopicName topicName, User owner) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.topicName = topicName;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Collection that = (Collection) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
