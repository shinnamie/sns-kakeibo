package com.example.repository.user;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.domain.user.User;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LoginMapperTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	LoginMapper loginMapper;

	User expectedUser = new User();

	//DbSetupに必要な処理
	private static final Operation DELETE_ALL_USER = Operations.deleteAllFrom("test_users"); // 初期化(delete)するテーブル
	private static final Operation INSERT_USER = Operations.insertInto("test_users") // 初期投入データ
			.columns("id", "mail_address", "password", "name", "gender", "date_of_birth", "remarks")
			.values(1L, "aaa@aaa", "test", "一郎", 1, "2001-01-01", "特になし").build();

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

		Destination destination = new DataSourceDestination(dataSource); // 勝手にapplication.propertiesのdatasource(yml)の情報を取得
		new DbSetup(destination, Operations.sequenceOf(DELETE_ALL_USER, INSERT_USER)).launch(); // 初期化とデータ投入が実行される
		
		User loginUser = loginMapper.Login("aaa@aaa", "test"); // 

		// 期待するUser情報を生成
		expectedUser.setId(1L);
		expectedUser.setMailAddress("aaa@aaa");
		expectedUser.setPassword("test");

		assertEquals(expectedUser.getId(), loginUser.getId());
		assertEquals(expectedUser.getMailAddress(), loginUser.getMailAddress());
		assertEquals(expectedUser.getPassword(), loginUser.getPassword());
	}
	
	

}
