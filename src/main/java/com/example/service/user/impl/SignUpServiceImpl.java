package com.example.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.User;
import com.example.repository.user.SignUpMapper;
import com.example.service.user.SignUpService;

@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	private SignUpMapper mapper;

	@Override
	public boolean signUp(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
