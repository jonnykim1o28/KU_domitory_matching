package com.suvey.suvey;

import com.suvey.suvey.domain.comment.repository.CommentRepository;
import com.suvey.suvey.domain.post.repository.PostRepository;
import com.suvey.suvey.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteCommentTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testCreateAndDeletePostWithComments() {


    }

}
