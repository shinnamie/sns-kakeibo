package com.example.repository.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.post.Post;

@Mapper
public interface PostMapper {

	/*
	 * 掲示板IDでトピックごとの投稿一覧を取得
	 */
	public List<Post> findPostList(Long boardId);

	public boolean deletePost(Long postId) throws Exception;

}
