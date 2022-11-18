package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.Kakeibo;

@SpringBootTest
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
	@DisplayName("正常系：選択された月の家計簿情報を算出する")
	void testFindKakeiboByYearAndMonth() throws Exception {
		List<Kakeibo> kakeiboList = mapper.totalIncomeAndExpenditureBreakdown("2022-11");
		assertTrue(0 <= kakeiboList.size());
		assertNotNull(kakeiboList.get(0));
	}

}
