package com.suvey.suvey.domain.comment.repository;

import com.suvey.suvey.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String> {


    public List<CommentEntity> findByPostId(String id);
}
