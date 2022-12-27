package com.example.service.like.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.like.Like;
import com.example.domain.post.Post;
import com.example.repository.like.LikeMapper;
import com.example.service.like.LikeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LikeServiceImpl implements LikeService {

	@Autowired
	public LikeMapper likeMapper;

	/**
	 * postにいいねする
	 * 
	 * @param Like
	 * @return boolean
	 */
	@Override
	public boolean markLike(Like like) {
		try {
			likeMapper.markLike(like);
			return true;
		} catch (Exception e) {
			log.error("いいねで例外が発生しました: {}", e);
			return false;
		}
	}

	/**
	 * いいねを取り消す
	 * 
	 * @param Like
	 * @return boolean
	 */
	@Override
	public boolean removeLike(Like like) {
		try {
			likeMapper.removeLike(like);
			return true;
		} catch (Exception e) {
			log.error("いいねを取り消すで例外が発生しました: {}", e);
			return false;
		}
	}

	/**
	 * いいね数を計算する
	 * 
	 * @param List<Post>
	 * @return List<Integer>
	 */
	@Override
	public List<Integer> totallingLike(List<Post> postList) {
		List<Integer> likeCountList = new ArrayList<>();
		
		for(Post post: postList){
			  Integer likes = likeMapper.totallingLike(post.getId());
			  likeCountList.add(likes);
			}
		
		return likeCountList;
	}

}
