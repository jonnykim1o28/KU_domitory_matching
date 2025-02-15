package com.suvey.suvey.domain.group.dto;

import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.post.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDTO {
    private String id;

    private String title;

    private Integer num;

    private Integer totalPages;

    private List<PostEntity> posts;



    public GroupDTO(final GroupEntity group) {
        this.id = group.getId();
        this.title = group.getTitle();
        this.num = group.getNum();
        this.posts = group.getPostEntities();
    }

    public static GroupEntity GroupDTOToEntity(final GroupDTO groupDTO) {
        GroupEntity group = GroupEntity.builder().postEntities(groupDTO.posts)
                .title(groupDTO.title)
                .num(groupDTO.num)
                .id(groupDTO.id)
                .build();

        return group;
    }


}
