package com.example.repository.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.User;

@SpringBootTest
@Transactional
class SignUpMapperTest {

	@Autowired
	SignUpMapper mapper;

	User mitsuki = new User();

	@BeforeEach
	void setUp() throws Exception {
		// 事前情報の登録
		mitsuki.setMailAdress("test@net.com");
		mitsuki.setPassword("password");
		mitsuki.setName("高畑充希");
		mitsuki.setGender(2);
		mitsuki.setDateOfBirth(LocalDate.of(1991, 12, 14));
		mitsuki.setRemarks("女優です");
		mitsuki.setKakeiboList(null);
	}

	@Test
	@DisplayName("登録情報が正しいとき、登録が成功することを期待する")
	void whenValidInformation_signupIsSuccess() throws Exception {
		// 実行
		boolean actual = mapper.signUp(mitsuki);

		// 検証
		assertTrue(actual);
	}

	@Test
	@DisplayName("メールアドレスが既に登録されているとき、例外が発生し、登録が失敗することを期待する")
	void whenMailAddressIsNotUnique_signupIsFail() throws Exception {
		// 準備
		mapper.signUp(mitsuki);

		// 実行 & 検証
		assertThrows(Exception.class, () -> mapper.signUp(mitsuki));
	}

	@Test
	@DisplayName("メールアドレスがnullのとき、例外が発生し、登録が失敗することを期待する")
	void whenMailAddressIsnull_signupIsFail() {
		// 準備
		mitsuki.setMailAdress(null);

		// 実行 & 検証
		//		assertEquals(null, mapper.signUp(mitsuki));
		assertThrows(Exception.class, () -> mapper.signUp(mitsuki));
	}

	@Test
	@DisplayName("パスワードがnullのとき、例外が発生し、登録が失敗することを期待する")
	void whenPasswordIsnull_signupIsFail() {
		// 準備
		mitsuki.setPassword(null);

		// 実行 & 検証
		assertThrows(Exception.class, () -> mapper.signUp(mitsuki));
	}

	@Test
	@DisplayName("名前がnullのとき、例外が発生し、登録が失敗することを期待する")
	void whenNameIsnull_signupIsFail() {
		// 準備
		mitsuki.setName(null);

		// 実行 & 検証
		assertThrows(Exception.class, () -> mapper.signUp(mitsuki));
	}

}
