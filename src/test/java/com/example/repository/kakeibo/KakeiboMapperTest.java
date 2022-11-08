package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
	@DisplayName("正しい値を渡すと家計簿の登録が成功する")
	void whenValidInformation_expectToInsertKakeibo() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(1);
		kakeibo.setUsedStore("コンビニ");
		kakeibo.setRemarks("お醤油");
		// 実行
		boolean actual = mapper.saveKakeibo(kakeibo);
		// 検証 (actualの値がtrueなら成功)
		assertTrue(actual);
	}

}
