package com.example.service.follow;

import java.util.List;

import com.example.domain.user.User;
import com.example.form.follow.RemoveFollowForm;

public interface FollowService {

	public List<User> selectFollowingList(Long id);
	
	public List<User> selectFollowedList(Long id);
	
	public User selectMyPage(Long id);
	
	public boolean deleteFollowing(RemoveFollowForm removeFollowForm);
}
