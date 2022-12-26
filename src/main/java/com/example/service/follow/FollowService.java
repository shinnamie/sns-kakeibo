package com.example.service.follow;

import java.util.List;

import com.example.domain.user.User;

public interface FollowService {

	public List<User> selectFollowingList(Long id);
	
	public List<User> selectFollowedList(Long id);
	
	public User selectMyPage(Long id);
}
