package uz.itransition.personalcollectionmanagement.projection.collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Collection;
import uz.itransition.personalcollectionmanagement.projection.item.ItemProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Projection(types = Collection.class)
public interface CollectionByIdProjection {

    UUID getId();

    String getTitle();

    String getDescription();

    String getImgUrl();

    String getTopicName();

    Timestamp getCreatedAt();

    @Value("#{@itemRepository.getItemsByCollectionId(target.id)}")
    List<CollectionItemsProjection> getCollectionItems();
}
