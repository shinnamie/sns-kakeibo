package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.kakeibo.Kakeibo;

@SpringBootTest
@Transactional
class KakeiboMapperTest {

	@Autowired
	KakeiboMapper mapper;

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
	@DisplayName("ユーザIDを渡したとき、適切なリストが得られることを期待する")
	void whenGiveUserId_expectedToGetKakeiboList() {
		// 準備
		// テスト用ユーザIDの準備
		var userId = 100;
		// テストデータを100件作成
		for (int i = 1; i <= 100; i++) {
			var kakeibo = new Kakeibo();
			kakeibo.setUserId(userId);
			LocalDate date = LocalDate.now();
			kakeibo.setPaymentDate(date);
			kakeibo.setExpenseItemId(1);
			kakeibo.setExpenditureAmount(10000);
			kakeibo.setIncomeAmount(0);
			mapper.saveKakeibo(kakeibo);
		}
		// 実行
		var kakeiboList = mapper.findAll(100L);
		// 検証
		for (var kakeibo : kakeiboList) {
			assertEquals(userId, kakeibo.getUserId());
		}
	}

	@Test
	@DisplayName("選択された月の家計簿情報を算出する")
	void testFindKakeiboByYearAndMonth() throws Exception {
		List<Kakeibo> kakeiboList = mapper.totalIncomeAndExpenditureBreakdown("2022-11");
		assertTrue(0 <= kakeiboList.size());
	}

}
