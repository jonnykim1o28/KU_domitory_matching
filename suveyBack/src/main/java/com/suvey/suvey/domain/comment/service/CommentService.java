package com.suvey.suvey.domain.comment.service;

import com.suvey.suvey.domain.comment.entity.CommentEntity;
import com.suvey.suvey.domain.comment.repository.CommentRepository;
import com.suvey.suvey.domain.user.entity.UserEntity;
import com.suvey.suvey.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;




    public List<CommentEntity> retrieveCommentByPostId(String Id) {
        return commentRepository.findByPostId(Id);
    }

    public void createComment(CommentEntity comment) {

        commentRepository.save(comment);
    }

    public void deleteComment(CommentEntity comment) {
        commentRepository.delete(comment);
    }

    public UserEntity getUser(String userId) {
        return userRepository.findById(userId).get();
    }
}
