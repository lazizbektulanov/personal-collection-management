package uz.itransition.personalcollectionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.itransition.personalcollectionmanagement.entity.Like;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like,UUID> {

    Integer countAllByItemId(UUID item_id);

    boolean existsByUserIdAndItemId(UUID user_id, UUID item_id);

    @Transactional
    void deleteByUserIdAndItemId(UUID user_id, UUID item_id);

}
