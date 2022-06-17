package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Collection;

import java.util.UUID;

@Projection(types = Collection.class)
public interface ItemCollectionsProjection {

    UUID getId();
    String getTitle();
}
