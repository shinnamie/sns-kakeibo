package com.example.service.like.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.like.Like;
import com.example.repository.like.LikeMapper;
import com.example.service.like.LikeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	public LikeMapper likeMapper;

	/**
	 * postにいいねする
	 * 
	 * @param　Like
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

}
