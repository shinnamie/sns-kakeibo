package com.example.service.like;

import com.example.domain.like.Like;

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

}
