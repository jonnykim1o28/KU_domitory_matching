package com.suvey.suvey.domain.comment.entity;

import com.suvey.suvey.domain.post.entity.PostEntity;
import com.suvey.suvey.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class CommentEntity {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

//    private String userId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private PostEntity post;

//    private String postId;

    private String content;

    private LocalDateTime time;

}
