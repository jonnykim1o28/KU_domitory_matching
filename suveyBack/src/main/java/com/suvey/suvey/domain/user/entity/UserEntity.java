package com.suvey.suvey.domain.user.entity;


import com.suvey.suvey.domain.desired_user.DesiredUser;
import com.suvey.suvey.domain.user_info.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String email;

    private String nickname;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private UserInfo information;

    @OneToOne(cascade = CascadeType.ALL)
    private DesiredUser desiredUser;


    public void updateInfo(UserInfo info){
        this.information = info;
    }

    public void updateDesiredUser(DesiredUser desiredUser){
        this.desiredUser = desiredUser;
    }




}
