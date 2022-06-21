package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Item;

import java.util.List;
import java.util.UUID;

@Projection(types = Item.class)
public interface ItemByIdProjection {

    UUID getId();
    String getItemName();
    String getItemImgUrl();
    UUID getItemCollectionId();
    String getItemCollectionTitle();
    UUID getAuthorId();
    String getAuthorName();
    Long getItemLikes();
    @Value("#{@tagRepository.findTagsByItemId(target.id)}")
    List<TagProjection> getTagsByItemId();
}
