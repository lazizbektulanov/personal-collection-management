package uz.itransition.personalcollectionmanagement.projection.item;


import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;

import java.util.UUID;

@Projection(types = Item.class)
public interface ItemProjection {

    UUID getId();

    String getItemName();

    String getItemImgUrl();

    UUID getItemCollectionId();

    String getItemCollectionTitle();

    UUID getAuthorId();

    String getAuthorName();

    String getAuthorProfileImgUrl();

    Long getItemLikes();

}
