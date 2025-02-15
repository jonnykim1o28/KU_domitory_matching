package com.suvey.suvey.domain.post.dto;

import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String id;

    private String link;

    private String date;

    private String postTitle;

    private Integer groupNum;

    private String postContent;

    private GroupEntity group;

    public PostDTO(final PostEntity post) {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        this.id = post.getId();
        this.date = post.getDate().format(formatter);
        this.groupNum = post.getGroupNum();
        this.postContent = post.getPostContent();
        this.postTitle = post.getPostTitle();


    }

    public PostEntity postDTOToEntity() {
        PostEntity postEntity = PostEntity.builder()
                .id(this.id)
                .groupNum(this.groupNum)
                .postContent(this.postContent)
                .postTitle(this.postTitle)
                .build();

        return postEntity;
    }

    public static PostDTO postEntityToDTO(PostEntity postEntity) {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return PostDTO.builder().id(postEntity.getId())
                .date(postEntity.getDate().format(formatter))
                .groupNum(postEntity.getGroupNum())
                .postContent(postEntity.getPostContent())
                .postTitle(postEntity.getPostTitle())
                .build();
    }
}
