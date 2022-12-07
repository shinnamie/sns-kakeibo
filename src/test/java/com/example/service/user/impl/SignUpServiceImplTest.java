package com.example.service.user.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	void test() {
		// 準備
		when(mapper.signUp(mitsuki)).thenReturn(true);
		// 実行
		boolean actual = service.signUp(mitsuki);
		// 検証
		assertTrue(actual);
	}

}
