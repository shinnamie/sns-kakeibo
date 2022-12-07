package com.example.service.user.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.user.User;
import com.example.repository.user.LoginMapper;

@SpringBootTest
class LoginServiceImplTest {

	@Mock
	LoginMapper loginMapper;
	
	@InjectMocks
	LoginServiceImpl loginServiceImpl;

	@Test
	@DisplayName("正常系：入力されたメールアドレスとパスワードが適当であればUser情報を返す")
	void testLogin() throws Exception {

		// 期待するUser情報を生成
		User expectedUser = new User();
		expectedUser.setId(1L);
		expectedUser.setMailAddress("aaa@aaa");
		expectedUser.setPassword("test");
		doReturn(expectedUser).when(loginMapper).Login(anyString(), anyString());

		// serviceのMock作成
		User serivceUser = loginServiceImpl.login("aaa@aaa", "test");

		assertEquals(expectedUser.getId(), serivceUser.getId());
		assertEquals(expectedUser.getMailAddress(), serivceUser.getMailAddress());
		assertEquals(expectedUser.getPassword(), serivceUser.getPassword());
	}
	
	@Test
	@DisplayName("異常系：入力されたメールアドレスが無効でnullのUser情報を返す")
	void testLoginNullMailAddress() throws Exception {

		// 期待するUser情報を生成
		doReturn(null).when(loginMapper).Login(anyString(), anyString());

		// serviceのMock作成
		User serivceUser = loginServiceImpl.login("aaa@a", "test");

		assertNull(serivceUser);
	}
	
	@Test
	@DisplayName("異常系：入力されたパスワードが無効でnullのUser情報を返す")
	void testLoginNullPassword() throws Exception {

		// 期待するUser情報を生成
		doReturn(null).when(loginMapper).Login(anyString(), anyString());

		// serviceのMock作成
		User serivceUser = loginServiceImpl.login("aaa@aaa", "null");

		assertNull(serivceUser);
	}

}
