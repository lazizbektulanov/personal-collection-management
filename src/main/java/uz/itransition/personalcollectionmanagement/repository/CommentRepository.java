package uz.itransition.personalcollectionmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.personalcollectionmanagement.entity.Comment;
import uz.itransition.personalcollectionmanagement.projection.CommentProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {


    @Query(nativeQuery = true,
    value = "select " +
            "cast(c.id as varchar) as id," +
            "c.body as body," +
            "cast(c.comment_from_id as varchar) as senderId," +
            "u.full_name as senderFullName," +
            "u.profile_img_url as senderProfileImgUrl," +
            "c.created_at as commentSentAt " +
            "from comments c " +
            "join users u on c.comment_from_id = u.id " +
            "where c.item_id=:itemId")
    List<CommentProjection> getCommentsByItemId(UUID itemId);

    Integer countAllByItemId(UUID item_id);
}
