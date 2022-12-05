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

	@Override
	public User login(String mailAddress, String password) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
