package com.example.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.User;
import com.example.repository.user.SignUpMapper;
import com.example.service.user.SignUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	private SignUpMapper mapper;

	/** ユーザー登録 */
	@Override
	public boolean signUp(User user) {
		try {
			mapper.signUp(user);
			log.info("登録が成功しました");
			return true;
		} catch (Exception e) {
			log.error("登録が失敗しました");
			return false;
		}
	}
	/** メアドチェック */
	@Override
	public User findByEmail(String mailAddress) {
		User user = new User();
		try {
			user = mapper.findByEmail(mailAddress);
			log.info("ユーザー:{}" , user);
			log.info("登録が成功しました");
			
		} catch (Exception e) {
			log.error("メールアドレス重複により登録が失敗しました");
		}
		return user;
	}
	

}
