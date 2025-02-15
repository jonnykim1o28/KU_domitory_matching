package com.suvey.suvey.domain.post.entity;

import com.suvey.suvey.domain.comment.entity.CommentEntity;
import com.suvey.suvey.domain.group.entity.GroupEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    private LocalDateTime date;

    private Integer groupNum;

    private String postContent;

    private String postTitle;

    private File file;

    @ManyToOne
    private GroupEntity group;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;
}
