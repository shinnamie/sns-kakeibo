package com.example.repository.like;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.like.Like;
import com.example.domain.post.Post;

@Mapper
public interface LikeMapper {
	
	/**
	 * postにいいねする
	 * 
	 * @param　Like
	 * @return boolean
	 */
	public boolean markLike(Like like);
	
	/**
	 * いいねを取り消す
	 * 
	 * @param　Like
	 * @return boolean
	 */
	public boolean removeLike(Like like);
	
	/**
	 * いいね数を計算する
	 * 
	 * @param　Like
	 * @return Integer
	 */
	public Integer totallingLike(Long postId);

}
