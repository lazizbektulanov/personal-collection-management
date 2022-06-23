package uz.itransition.personalcollectionmanagement.projection;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.projection.collection.CollectionProjection;

import java.util.List;
import java.util.UUID;

@Projection(types = User.class)
public interface ProfileProjection {

    UUID getId();

    String getFullName();

    String getUsername();

    String getEmail();

    String getProfileImgUrl();

    String getBio();

    Integer getCollectionsNumber();

    Integer getItemsNumber();

    @Value("#{@collectionRepository.getUserCollections(target.id)}")
    List<CollectionProjection> getUserCollections();
}
