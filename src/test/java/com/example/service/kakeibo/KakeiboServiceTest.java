
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
		assertEquals(mapperKakeiboList, serviceKakeiboList);

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
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-04"));
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
		expectedKakeiboItemMap.put("総支出", 5000);
		expectedKakeiboItemMap.put("収支", -5000);

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> kakeiboItemMap = service.findBreakdown(kakeiboList);

		// 正常処理
		assertEquals(expectedKakeiboItemMap, kakeiboItemMap);

	}

	@Test
	@DisplayName("異常系：受け取った月別の家計簿(List)から費目別の支出・収入をMapで返す")
	void testFindBreakdownNull() throws Exception {

		// 何も持たないList
		List<Kakeibo> kakeiboList = new ArrayList<>();

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> kakeiboItemMap = service.findBreakdown(kakeiboList);

		// 正常処理
		assertNull(kakeiboItemMap);

	}

	@Test
	@DisplayName("正常系：受け取ったMapから総収入・総支出・収支だけ取り出す")
	void testTotalAmountMap() throws Exception {

		// 期待するMapを生成
		Map<String, Integer> expectedTotalAmountMap = new HashMap<>();
		expectedTotalAmountMap.put("総収入", 0);
		expectedTotalAmountMap.put("総支出", 5000);
		expectedTotalAmountMap.put("収支", -5000);

		// serviceクラスを通すMapを生成
		Map<String, Integer> toServiceTotalAmountMap = new HashMap<>();
		toServiceTotalAmountMap.put("総収入", 0);
		toServiceTotalAmountMap.put("総支出", 5000);
		toServiceTotalAmountMap.put("収支", -5000);
		toServiceTotalAmountMap.put("食費", 5000); // このメソッドでは取り除かれるはずの”食費”も追加

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> totalAmountMap = service.totalAmountMap(toServiceTotalAmountMap);

		// 正常処理
		assertEquals(expectedTotalAmountMap, totalAmountMap);

	}

	@Test
	@DisplayName("異常系：受け取ったMapから総収入・総支出・収支だけ取り出す")
	void testTotalAmountMapNull() throws Exception {

		// 期待するMapを生成
		Map<String, Integer> expectedTotalAmountMap = new HashMap<>();

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> totalAmountMap = service.totalAmountMap(expectedTotalAmountMap);

		// null処理
		assertNull(totalAmountMap);

	}

	@Test
	@DisplayName("正常系：受け取ったMapから費目だけ取り出す")
	void testItemExpenseMap() throws Exception {

		// 期待するMapを生成
		Map<String, Integer> expectedItemExpenseMap = new HashMap<>();
		expectedItemExpenseMap.put("食費", 5000);
		expectedItemExpenseMap.put("税・社会保障費", 10000);

		// serviceクラス通すMapを生成
		Map<String, Integer> toServiceItemExpenseMap = new HashMap<>();
		toServiceItemExpenseMap.put("総収入", 0);// このメソッドでは取り除かれるはずの”総収入・総支出・収支”も追加
		toServiceItemExpenseMap.put("総支出", 5000);
		toServiceItemExpenseMap.put("収支", -5000);
		toServiceItemExpenseMap.put("食費", 5000);
		toServiceItemExpenseMap.put("税・社会保障費", 10000);

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> itemExpenseMap = service.itemExpenseMap(toServiceItemExpenseMap);

		// 正常処理
		assertEquals(expectedItemExpenseMap, itemExpenseMap);

	}

	@Test
	@DisplayName("異常系：受け取ったMapから費目だけ取り出す")
	void testItemExpenceMapNull() throws Exception {

		// 期待するMapを生成
		Map<String, Integer> expectedItemExpenseMap = new HashMap<>();

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> itemExpenseMap = service.totalAmountMap(expectedItemExpenseMap);

		// null処理
		assertNull(itemExpenseMap);

	}

	@Test
	@DisplayName("正常系：受け取ったMapを<String,Integer> → <String,Double>に変換する")
	void testIntegerToDouble() throws Exception {

		// 期待するMapを生成
		Map<String, Double> integerToDoubleMap = new HashMap<>();
		integerToDoubleMap.put("食費", 5000.0);
		integerToDoubleMap.put("税・社会保障費", 10000.0);

		// サービスクラスに渡すMapを生成
		Map<String, Integer> integerMap = new HashMap<>();
		integerMap.put("食費", 5000);
		integerMap.put("税・社会保障費", 10000);

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Double> doubleMap = service.integerToDouble(integerMap);

		// 正常処理
		assertEquals(integerToDoubleMap, doubleMap);

	}

	@Test
	@DisplayName("異常系：受け取ったMapを<String,Integer> → <String,Double>に変換する")
	void testIntegerToDoubleNull() throws Exception {

		// サービスクラスに渡すMapを生成
		Map<String, Integer> integerMap = new HashMap<>();

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Integer> itemExpenseMap = service.totalAmountMap(integerMap);

		// null処理
		assertNull(itemExpenseMap);

	}

	@Test
	@DisplayName("正常系：Map内の費目別の割合を計算し、Map<費目名,割合>を返す")
	void testCalculatePercentage() throws Exception {
		
		// 期待するMapを生成
		Map<String, Double> expectedPercentageMap = new HashMap<>();
		expectedPercentageMap.put("食費", 50.0);

		// サービスクラスに渡すMapを生成
		Map<String, Double> percentageMap = new HashMap<>();
		percentageMap.put("食費", 5000.0);
		percentageMap.put("総支出", 10000.0);

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Double> serviceThroughPercentageMap = service.calculatePercentage(percentageMap);

		// 正常処理
		assertEquals(percentageMap, serviceThroughPercentageMap);

	}
	
	@Test
	@DisplayName("異常系：Map内の費目別の割合を計算し、Map<費目名,割合>を返す")
	void testCalculatePercentageMapNull() throws Exception {

		// サービスクラスに渡すMapを生成
		Map<String, Double> percentageMap = new HashMap<>();

		// serviceクラスのメソッドを通したMapを取得
		Map<String, Double> serviceThroughPercentageMap = service.calculatePercentage(percentageMap);

		// null処理
		assertNull(serviceThroughPercentageMap);

	}

}
