package com.example.repository.post;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.post.Post;

@Mapper
public interface PostMapper {

	public List<Post> findPostList();

}
