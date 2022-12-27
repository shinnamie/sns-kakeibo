package com.example.service.like;

import java.util.List;

import com.example.domain.like.Like;
import com.example.domain.post.Post;

public interface LikeService {
	
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
	public List<Integer> totallingLike(List<Post> postList);

}
