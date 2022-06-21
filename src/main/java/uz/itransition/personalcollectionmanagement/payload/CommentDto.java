package uz.itransition.personalcollectionmanagement.payload;


import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CommentDto {

    private UUID id;
    private String body;
    private String ItemId;
    private UUID senderId;
    private String senderFullName;
    private String senderProfileImgUrl;
    private Timestamp commentSentAt;

}
