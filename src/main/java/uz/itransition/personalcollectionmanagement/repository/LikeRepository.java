package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Like;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like,UUID> {

    Integer countAllByItemId(UUID item_id);
}
