package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.projection.ProfileProjection;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,
    value = "select " +
            "cast(u.id as varchar) as id," +
            "u.full_name as fullName," +
            "u.username as username," +
            "u.email as email," +
            "u.profile_img_url as profileImgUrl," +
            "u.bio as bio," +
            "(select count(c.id) from collections c " +
            "where c.owner_id=u.id) as collectionsNumber," +
            "(select count(i.id) from items i " +
            "where i.created_by_id=u.id) as itemsNumber " +
            "from users u " +
            "where u.id=:userId")
    ProfileProjection getUserProfile(UUID userId);
}
