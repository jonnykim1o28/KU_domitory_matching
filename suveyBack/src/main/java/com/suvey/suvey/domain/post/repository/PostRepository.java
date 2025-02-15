package com.suvey.suvey.domain.post.repository;

import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {


    public List<PostEntity> findByUserId(String userId);

    public List<PostEntity> findByGroupNum(Integer groupNum);

    public Page<PostEntity> findAllByGroup(GroupEntity group, Pageable pageable);


}
