package com.suvey.suvey.domain.comment.controller;

import com.suvey.suvey.domain.comment.dto.CommentDTO;
import com.suvey.suvey.domain.comment.entity.CommentEntity;
import com.suvey.suvey.domain.comment.service.CommentService;
import com.suvey.suvey.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/suvey/comment")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<?> retrieveByPostId(@RequestParam String postId) {
        List<CommentEntity> commentEntities = commentService.retrieveCommentByPostId(postId);

        List<CommentDTO> commentDTOs = commentEntities.stream().map(CommentDTO::new).collect(Collectors.toList());

        ResponseDTO responseDTO = ResponseDTO.<CommentDTO>builder().data(commentDTOs).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping
    public void deleteComment(@AuthenticationPrincipal String userId, @RequestBody CommentDTO commentDTO) {
        CommentEntity comment = commentDTO.commentDTOToEntity();
        if(comment.getUser().getId().equals(userId)){
            commentService.deleteComment(comment);
        }
    }


    @PostMapping
    public void createComment(@AuthenticationPrincipal String userId, @RequestBody CommentDTO commentDTO) {
        CommentEntity comment = commentDTO.commentDTOToEntity();

        comment.setUser(commentService.getUser(userId));

        commentService.createComment(comment);
    }

}
