package com.suvey.suvey.domain.post.controller;

import com.suvey.suvey.domain.group.service.GroupService;
import com.suvey.suvey.domain.post.dto.PostDTO;
import com.suvey.suvey.domain.post.entity.PostEntity;
import com.suvey.suvey.domain.post.service.PostService;
import com.suvey.suvey.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suvey")
@Slf4j
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<?> retrieve() {
        List<PostEntity> postEntities = postService.getAllPost();

        List<PostDTO> postDTOs = postEntities.stream().map(PostDTO::postEntityToDTO).collect(Collectors.toList());

        ResponseDTO<PostDTO> responseDTO = ResponseDTO.<PostDTO>builder().data(postDTOs).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/postView")
    public ResponseEntity<?> retrieveById(@RequestParam String postId) {
        PostEntity post = postService.getPostById(postId);

        PostDTO postDTO = PostDTO.postEntityToDTO(post);

        return ResponseEntity.ok().body(postDTO);
    }


    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDTO postDTO) {
        PostEntity post = postDTO.postDTOToEntity();

        try {
            post.setDate(LocalDateTime.now());
            post.setGroup(groupService.getGroupByNum(postDTO.getGroupNum()));
            post.setUserId(userId);
            postService.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Post field is empty");
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal String userId, @RequestBody PostDTO dto) {

        PostEntity post = dto.postDTOToEntity();

        post.setUserId(userId);
        PostEntity postEntity = postService.update(post);
        PostDTO returnDTO = PostDTO.postEntityToDTO(postEntity);

        return ResponseEntity.ok().body(returnDTO);
    }

    @DeleteMapping
    public void deletePost(@AuthenticationPrincipal String userId, @RequestBody PostDTO postDTO) {

        PostEntity post = postDTO.postDTOToEntity();

        postService.deletePost(post);

    }




}
