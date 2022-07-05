package uz.itransition.personalcollectionmanagement.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import uz.itransition.personalcollectionmanagement.entity.Comment;
import uz.itransition.personalcollectionmanagement.entity.Item;
import uz.itransition.personalcollectionmanagement.entity.User;
import uz.itransition.personalcollectionmanagement.payload.CommentDto;
import uz.itransition.personalcollectionmanagement.projection.comment.CommentProjection;
import uz.itransition.personalcollectionmanagement.repository.CommentRepository;
import uz.itransition.personalcollectionmanagement.repository.ItemRepository;
import uz.itransition.personalcollectionmanagement.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final UserRepository userRepository;


    public void leaveComment(CommentDto commentDto, Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        Optional<Item> item = itemRepository.findById(UUID.fromString(commentDto.getItemId()));
        if (!item.isPresent() || !user.isPresent())
            throw new ResourceNotFoundException("User or item not found");
        User currentUser = user.get();
        Comment savedComment = commentRepository.save(new Comment(
                commentDto.getBody().trim(),
                currentUser,
                item.get()
        ));
        commentDto.setId(savedComment.getId());
        commentDto.setSenderId(currentUser.getId());
        commentDto.setSenderFullName(currentUser.getFullName());
        commentDto.setSenderProfileImgUrl(currentUser.getProfileImgUrl());
        commentDto.setCommentSentAt(savedComment.getCreatedAt());
        messagingTemplate.convertAndSendToUser(
                commentDto.getItemId(),
                "/item-comments",
                commentDto
        );
    }

    public List<CommentProjection> getItemComments(UUID itemId) {
        return commentRepository.getCommentsByItemId(itemId);
    }
}
