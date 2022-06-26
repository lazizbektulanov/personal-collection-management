package uz.itransition.personalcollectionmanagement.projection;


import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.User;

import java.sql.Timestamp;
import java.util.UUID;

@Projection(types = User.class)
public interface UserProjection {

    UUID getId();

    String getFullName();

    String getEmail();

    Boolean getIsActive();

    String getProfileImgUrl();

    Timestamp getLastLoginTime();

    String getRole();
}
