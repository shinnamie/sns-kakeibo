package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

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
class KakeiboMapperPostKakeiboByYearAndMonthTest {
	
	@Autowired
	private KakeiboMapper kakeiboMapper;

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
	
	// 正常系

	@Test
	@DisplayName("2022年8月が入力された時、11件の結果を返す")
	void testFindKakeiboByYearAndMonth() throws Exception {
		List<Kakeibo> kakeiboList = kakeiboMapper.findKakeiboByYearAndMonth("2022", "8");
		assertEquals(11, kakeiboList.size());
	}
	
	@Test
	@DisplayName("2022年のみ入力された時、16件の結果を返す")
	void testMontIsNull() throws Exception {
		List<Kakeibo> kakeiboList = kakeiboMapper.findKakeiboByYearAndMonth("2022", "");
		assertEquals(16, kakeiboList.size());
	}
	
	@Test
	@DisplayName("該当年月がない時、0件の結果を返す")
	void testNotYearAndMonth() throws Exception {
		List<Kakeibo> kakeiboList = kakeiboMapper.findKakeiboByYearAndMonth("2000", "1");
		assertEquals(0, kakeiboList.size());
	}
	
	@Test
	@DisplayName("該当年がない時、0件の結果を返す")
	void testNotYear() throws Exception {
		List<Kakeibo> kakeiboList = kakeiboMapper.findKakeiboByYearAndMonth("2000", "");
		assertEquals(0, kakeiboList.size());
	}
}