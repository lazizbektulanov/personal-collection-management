package uz.itransition.personalcollectionmanagement.projection;


import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;

import java.util.UUID;

@Projection(types = Item.class)
public interface ItemProjection {

    UUID getItemId();
    String getItemName();
    String getItemImgUrl();
    UUID getCollectionId();
    String getCollectionTitle();
    UUID getAuthorId();
    String getAuthorName();

}
