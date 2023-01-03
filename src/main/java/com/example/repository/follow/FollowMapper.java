package com.example.repository.follow;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.User;
import com.example.form.follow.RemoveFollowForm;

@Mapper
public interface FollowMapper {

	public List<User> findFollowingList(Long id);
	
	public List<User> findFollowedList(Long id);
	
	public User findMyPage(Long id);
	
	public boolean unfollow(RemoveFollowForm removeFollowForm);
}
