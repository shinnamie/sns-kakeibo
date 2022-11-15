
package com.example.service.kakeibo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.repository.kakeibo.KakeiboMapper;

@SpringBootTest
class KakeiboServiceTest {

	@Mock // モックオブジェクトとして使用することを宣言
	KakeiboMapper mapper;

	@InjectMocks // モックオブジェクトの注入
	KakeiboService service;

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
	@DisplayName("正常系：選択された月の費目別の支出・収入をListで返す")
	void testTotalByIncomeAndExpenditureBreakdown() throws Exception {

		// mapperのMock作成
		List<Kakeibo> mapperKakeiboList = new ArrayList<>();
		Kakeibo mapperKakeibo = new Kakeibo();
		mapperKakeibo.setExpenditureAmount(110000);
		mapperKakeiboList.add(mapperKakeibo);
		doReturn(mapperKakeiboList).when(mapper).totalIncomeAndExpenditureBreakdown(anyString());
		// 値
		System.out.println(mapperKakeiboList);

		// serviceのMock作成
		List<Kakeibo> serviceKakeiboList = service.totalByIncomeAndExpenditureBreakdown(anyString());
		// 値
		System.out.println(serviceKakeiboList);

		// 正常処理
		assertEquals(mapperKakeiboList.getClass(), serviceKakeiboList.getClass());

	}

	@Test
	@DisplayName("異常系：選択された月の費目別の支出・収入のデータが無い場合、nullを返す")
	void testTotalByIncomeAndExpenditureBreakdownNull() throws Exception {

		// mapperのMock作成
		doReturn(null).when(mapper).totalIncomeAndExpenditureBreakdown(anyString());

		// serviceのMock作成
		List<Kakeibo> serviceKakeiboList = service.totalByIncomeAndExpenditureBreakdown(anyString());

		// 処理
		assertNull(serviceKakeiboList);

	}

}
