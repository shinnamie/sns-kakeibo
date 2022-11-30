package com.example.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.User;
import com.example.repository.user.LoginMapper;
import com.example.service.user.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper mapper;

	/**
	 * ログイン処理
	 * 成功時：ユーザー情報を1件取得
	 * 失敗時：mail_addressが無効・passwordが無効
	 * 
	 * @param mailAddress,password
	 * @return User
	 */
	@Override
	public User login(String mailAddress, String password) {
		User loginUser = mapper.Login(mailAddress,password);
		return loginUser;
	}

}
