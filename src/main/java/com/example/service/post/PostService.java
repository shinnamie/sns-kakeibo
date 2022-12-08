package com.example.service.post;

import java.util.List;

import com.example.domain.post.Post;

public interface PostService {

	List<Post> postList(Long boardId);

}
