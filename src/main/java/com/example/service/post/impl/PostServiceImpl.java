package com.example.service.post.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.post.Post;
import com.example.repository.post.PostMapper;
import com.example.service.post.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	/*
	 * 掲示板IDでトピックごとの投稿一覧を取得
	 */
	@Override
	public List<Post> selectPostList(Long boardId) {
		return postMapper.findPostList(boardId);
	}
	
}
