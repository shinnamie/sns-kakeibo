package com.example.service.user.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.user.User;
import com.example.repository.user.SignUpMapper;

@SpringBootTest
class SignUpServiceImplTest {

	@InjectMocks
	SignUpServiceImpl service;

	@Mock
	SignUpMapper mapper;

	User mitsuki = new User();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("値が正しいとき、登録が成功しTrueを返すことを期待する")
	void whenValidInformation_signupIsSuccess() throws Exception {
		// 準備
		when(mapper.signUp(mitsuki)).thenReturn(true);
		// 実行
		boolean actual = service.signUp(mitsuki);
		// 検証
		assertTrue(actual);
	}

	@Test
	@DisplayName("値が不正なとき、登録が失敗しFalseを返すことを期待する")
	void whenInValidInformation_signupIsFail() throws Exception {
		// 準備
		when(mapper.signUp(mitsuki)).thenThrow(Exception.class);
		// 実行
		boolean actual = service.signUp(mitsuki);
		// 検証
		assertFalse(actual);
	}

}
