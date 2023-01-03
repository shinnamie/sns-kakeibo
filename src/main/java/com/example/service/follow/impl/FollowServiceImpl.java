package com.example.service.follow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.User;
import com.example.form.follow.RemoveFollowForm;
import com.example.repository.follow.FollowMapper;
import com.example.service.follow.FollowService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FollowServiceImpl implements FollowService{

	@Autowired
	private FollowMapper followMapper;


	//プロフィール機能
	@Override
	public User selectMyPage(Long id) {

		return followMapper.findMyPage(id);
	}
	/*
	 * ユーザーIDでフォローリスト一覧を取得
	 */
	@Override
	public List<User> selectFollowingList(Long id) {

		return followMapper.findFollowingList(id);
	}

	//フォロワーリスト一覧取得
	@Override
	public List<User> selectFollowedList(Long id) {

		return followMapper.findFollowedList(id);
	}
	//フォロー解除
	@Override
	public boolean deleteFollowing(RemoveFollowForm removeFollowForm) {
		try {
			followMapper.unfollow(removeFollowForm);
			return true;
		} catch (Exception e) {
			log.error("例外が発生しました: {}", e);
			return false;
		}
	}

}
