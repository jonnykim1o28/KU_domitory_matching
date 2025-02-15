package com.suvey.suvey.domain.comment.dto;

import com.suvey.suvey.domain.comment.entity.CommentEntity;

import java.time.LocalDateTime;

public class CommentDTO {

    private String id;

    private String userId;

    private String writerNickname;

    private String content;

    private LocalDateTime time;

    public CommentDTO(CommentEntity comment) {
        this.writerNickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.time = comment.getTime();
//        this.userId = comment.getUserId();
    }

    public CommentEntity commentDTOToEntity() {
        CommentEntity comment = CommentEntity.builder()
                .id(this.id)
                .content(this.content)
//                .userId(this.userId)
                .build();

        return comment;
    }
}
