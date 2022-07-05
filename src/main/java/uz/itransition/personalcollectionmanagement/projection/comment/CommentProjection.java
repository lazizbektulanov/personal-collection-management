package uz.itransition.personalcollectionmanagement.projection.comment;


import org.springframework.data.rest.core.config.Projection;
import uz.itransition.personalcollectionmanagement.entity.Comment;

import java.sql.Timestamp;
import java.util.UUID;

@Projection(types = Comment.class)
public interface CommentProjection {

    UUID getId();
    String getBody();
    UUID getSenderId();
    String getSenderFullName();
    String getSenderProfileImgUrl();
    Timestamp getCommentSentAt();

}
