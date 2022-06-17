package uz.itransition.personalcollectionmanagement.projection;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;

import java.util.List;
import java.util.UUID;

@Projection(types = Item.class)
public interface ItemProjection {

    UUID getId();
    String getItemName();
    String getItemImgUrl();
    @Value("#{@collectionRepository.getCollectionsByItemId(target.id)}")
    List<ItemCollectionsProjection> getItemCollections();
    UUID getAuthorId();
    String getAuthorName();
    Long getItemLikes();

}
