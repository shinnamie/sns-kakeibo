package com.example.service.post.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.post.Post;
import com.example.repository.post.PostMapper;
import com.example.service.kakeibo.KakeiboService;
import com.example.service.post.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
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
	
	@Override
	public boolean savePost(Post post) {
		try {
			postMapper.savePost(post);
			return true;
		} catch (Exception e) {
			log.error("postの新規投稿で例外が発生しました: {}", e);
			return false;
		}
	}
	
	@Override
	public boolean deletePost(Post post) {
		try {
			postMapper.deletePost(post);
			return true;
		} catch (Exception e) {
			log.error("postの削除で例外が発生しました: {}", e);
			return false;
		}
	}
	
}
