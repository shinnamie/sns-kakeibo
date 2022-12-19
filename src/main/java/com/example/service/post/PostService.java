package com.example.service.post;

import java.util.List;

import com.example.domain.post.Post;
import com.example.domain.post.PostData;

public interface PostService {

	List<Post> selectPostList(Long boardId);
	
	public boolean savePost(Post post);

}
