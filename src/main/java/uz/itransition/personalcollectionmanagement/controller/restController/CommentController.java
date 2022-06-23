package uz.itransition.personalcollectionmanagement.controller.restController;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import uz.itransition.personalcollectionmanagement.payload.CommentDto;
import uz.itransition.personalcollectionmanagement.projection.CommentProjection;
import uz.itransition.personalcollectionmanagement.service.CommentService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @MessageMapping("/userComment")
    public void leaveComment(CommentDto commentDto, Principal principal){
        commentService.leaveComment(commentDto,principal);
    }

    @GetMapping("/api/item-comments/{itemId}")
    public List<CommentProjection> getItemComments(@PathVariable UUID itemId){
        return commentService.getItemComments(itemId);
    }
}
