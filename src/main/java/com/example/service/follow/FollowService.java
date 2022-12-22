package com.example.service.follow;

import java.util.List;

import com.example.domain.user.User;

public interface FollowService {

	public List<User> selectFollowingList(Long followingId);
	
	public List<User> selectFollowedList(Long followedId);
	
}
