package uz.itransition.personalcollectionmanagement.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.itransition.personalcollectionmanagement.entity.enums.TopicName;
import uz.itransition.personalcollectionmanagement.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.UUID;

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


    public Collection(String title, String imgUrl, String description, TopicName topicName, User owner) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.topicName = topicName;
        this.owner = owner;
    }

    public Collection(UUID id, String title, String imgUrl, String description, TopicName topicName, User owner) {
        super(id);
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.topicName = topicName;
        this.owner = owner;
    }

}
