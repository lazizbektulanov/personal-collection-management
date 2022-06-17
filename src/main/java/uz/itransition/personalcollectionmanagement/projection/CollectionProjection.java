package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Collection;

import java.util.UUID;

@Projection(types = Collection.class)
public interface CollectionProjection {

    UUID getCollectionId();
    String getCollectionTitle();
    String getCollectionImgUrl();
    String getCollectionTopic();
    UUID getCollectionAuthorId();
    String getCollectionAuthorName();
    Long getCollectionItemsCount();
}
