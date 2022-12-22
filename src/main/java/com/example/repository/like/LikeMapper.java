package com.example.repository.like;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.like.Like;

@Mapper
public interface LikeMapper {
	
	/**
	 * postにいいねする
	 * 
	 * @param　Like
	 * @return boolean
	 */
	public boolean markLike(Like like);

}
