
package com.example.service.kakeibo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
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

		// serviceのMock作成
		List<Kakeibo> serviceKakeiboList = service.totalByIncomeAndExpenditureBreakdown(anyString());

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

	@Test
	@DisplayName("正常系：受け取った月別の家計簿(List)から費目別の支出・収入をMapで返す")
	void testFindBreakdown() throws Exception {

		// 期待するList
		List<Kakeibo> kakeiboList = new ArrayList<>();
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setId(1);
		kakeibo.setSettlementDate(LocalDate.parse("2022-11-04"));
		kakeibo.setExpenseItemId(2);
		kakeibo.setExpenditureAmount(5000);
		kakeibo.setIncomeAmount(0);
		ExpenseItem expenseItem = new ExpenseItem();
		expenseItem.setId(4);
		expenseItem.setExpenseItemName("食費");
		kakeibo.setExpenseItem(expenseItem);
		Settlement settlement = new Settlement();
		settlement.setId(2);
		settlement.setSettlementName("現金");
		kakeibo.setSettlement(settlement);
		kakeiboList.add(kakeibo);

		// 期待するMap
		Map<String, Integer> expectedKakeiboItemMap = new HashMap<>();
		expectedKakeiboItemMap.put("食費", 5000);
		expectedKakeiboItemMap.put("総収入", 0);
		expectedKakeiboItemMap.put("総支出",5000);
		expectedKakeiboItemMap.put("収支", -5000);
		
		//serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> kakeiboItemMap = service.findBreakdown(kakeiboList);

		// 正常処理
		assertEquals(expectedKakeiboItemMap.getClass(), kakeiboItemMap.getClass());

	}
	
	@Test
	@DisplayName("異常系：受け取った月別の家計簿(List)から費目別の支出・収入をMapで返す")
	void testFindBreakdownNull() throws Exception {

		// 何も持たないList
		List<Kakeibo> kakeiboList = new ArrayList<>();
		
		//serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> kakeiboItemMap = service.findBreakdown(kakeiboList);

		// 正常処理
		assertNull(kakeiboItemMap);

	}

}
