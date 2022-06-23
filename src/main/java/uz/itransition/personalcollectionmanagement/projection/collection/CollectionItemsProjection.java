package uz.itransition.personalcollectionmanagement.projection.collection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;

import java.sql.Timestamp;
import java.util.UUID;

@Projection(types = Item.class)
public interface CollectionItemsProjection {

    UUID getId();

    String getName();

    Timestamp getCreatedAt();

    Integer getItemLikesNumber();

    Integer getItemCommentsNumber();
}
