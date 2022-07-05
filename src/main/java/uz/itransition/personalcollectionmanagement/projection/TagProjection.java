package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Tag;

import java.util.UUID;

@Projection(types = Tag.class)
public interface TagProjection {

    UUID getId();
    String getName();

}
