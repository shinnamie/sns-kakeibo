package com.example.service.post;

import java.util.List;

import com.example.domain.post.Post;

public interface PostService {

	List<Post> selectPostList(Long boardId);
	
	public boolean savePost(Post post);
	
	public boolean deletePost(Post post);
	
}
