package com.example.repository.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.user.User;

@SpringBootTest
class LoginMapperTest {

	@Autowired
	LoginMapper loginMapper;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("正常系：入力されたメールアドレスとパスワードが適当であればUser情報を返す")
	void testLogin() throws Exception {

		// 期待するUser情報を生成
		User expectedUser = new User();
		expectedUser.setId(1L);
		expectedUser.setMailAddress("aaa@aaa");
		expectedUser.setPassword("test");

		// mapperを通したUserを生成
		User mapperUser = loginMapper.Login("aaa@aaa", "test");

		assertEquals(expectedUser.getId(), mapperUser.getId());
		assertEquals(expectedUser.getMailAddress(), mapperUser.getMailAddress());
		assertEquals(expectedUser.getPassword(), mapperUser.getPassword());
	}

	@Test
	@DisplayName("異常系：入力されたメールアドレスが無効な場合nullを返す")
	void testLoginErrorMailAddress() throws Exception {

		// メールアドレスが無効な状態でmapperを通したUserを生成
		User mapperUser = loginMapper.Login("a@a", "test");

		assertNull(mapperUser);

	}

	@Test
	@DisplayName("異常系：入力されたパスワードが無効な場合nullを返す")
	void testLoginErrorPassword() throws Exception {

		// メールアドレスが無効な状態でmapperを通したUserを生成
		User mapperUser = loginMapper.Login("aaa@aaa", "miss");

		assertNull(mapperUser);

	}

}
