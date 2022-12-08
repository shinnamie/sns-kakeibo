package com.example.service.post.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.post.Post;
import com.example.service.post.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Override
	public List<Post> postList(Long boardId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
