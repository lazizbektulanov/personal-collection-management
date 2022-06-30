package uz.itransition.personalcollectionmanagement.projection.collection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Collection;

import java.util.UUID;

@Projection(types = Collection.class)
public interface CollectionProjection {

    UUID getId();

    String getTitle();

    String getImgUrl();

    String getTopic();

    UUID getAuthorId();

    String getAuthorName();

    String getAuthorProfileImgUrl();

    Long getItemsCount();
}
