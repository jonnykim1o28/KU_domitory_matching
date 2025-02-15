package com.suvey.suvey.domain.user.dto;


import com.suvey.suvey.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private String nickname;

    private String password;

    private String token;

    public UserEntity userDTOToEntity() {

        UserEntity user = UserEntity.builder().id(this.id)
                .nickname(this.nickname)
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();

        return user;
    }

}
