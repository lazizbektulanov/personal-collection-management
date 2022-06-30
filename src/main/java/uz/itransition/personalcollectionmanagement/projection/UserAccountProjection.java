package uz.itransition.personalcollectionmanagement.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.User;

import java.util.UUID;

@Projection(types = User.class)
public interface UserAccountProjection {

    UUID getId();

    String getFullName();

    String getBio();

    String getProfileImgUrl();
}
