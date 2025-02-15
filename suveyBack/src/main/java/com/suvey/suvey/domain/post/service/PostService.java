package com.suvey.suvey.domain.post.service;

import com.suvey.suvey.domain.group.entity.GroupEntity;
import com.suvey.suvey.domain.post.entity.PostEntity;
import com.suvey.suvey.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public PostEntity getPostById(String id) {
        return postRepository.findById(id).get();
    }

    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
    }


    public Page<PostEntity> getPosts(GroupEntity group, Pageable pageable){
        return postRepository.findAllByGroup(group, pageable);
    }


    public void save(PostEntity post) {
        postRepository.save(post);
    }

    public PostEntity update(final PostEntity post) {

        final Optional<PostEntity> original = postRepository.findById(post.getId());

        original.ifPresent(pt -> {
            pt.setId(post.getId());
            pt.setDate(post.getDate());
            pt.setPostContent(post.getPostContent());
            pt.setPostTitle(post.getPostTitle());
            postRepository.save(pt);
        });

        return postRepository.findById(post.getId()).get();
    }

    public void deletePost(PostEntity post) {
        postRepository.delete(post);
    }


    public List<PostEntity> getPostByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public List<PostEntity> getPostByGroupNum(Integer groupNum) {
        return postRepository.findByGroupNum(groupNum);
    }


}
