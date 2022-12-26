package com.example.repository.follow;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.User;

@Mapper
public interface FollowMapper {

	public List<User> findFollowingList(Long id);
	
	public List<User> findFollowedList(Long id);
	
	public User findMyPage(Long id);
}
