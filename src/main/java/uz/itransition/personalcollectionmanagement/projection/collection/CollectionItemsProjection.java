package uz.itransition.personalcollectionmanagement.projection.collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.projection.customfield.CustomFieldValueProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Projection(types = Item.class)
public interface CollectionItemsProjection {

    UUID getId();

    String getName();

    Timestamp getCreatedAt();

    @Value("#{@likeRepository.countAllByItemId(target.id)}")
    Integer getItemLikesNumber();

    @Value("#{@commentRepository.countAllByItemId(target.id)}")
    Integer getItemCommentsNumber();

    @Value("#{@customFieldValueRepository.getCustomFieldValues(target.id)}")
    List<CustomFieldValueProjection> getCustomFieldValues();
}
