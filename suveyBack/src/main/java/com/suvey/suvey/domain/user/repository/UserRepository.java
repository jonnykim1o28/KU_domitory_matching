package com.suvey.suvey.domain.user.repository;

import com.suvey.suvey.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    public UserEntity findByNicknameAndPassword(String nickname, String password);

    public UserEntity findByNickname(String nickname);

    public Optional<UserEntity> findByEmail(String email);

    public String findPasswordByNickname(String nickname);
}
