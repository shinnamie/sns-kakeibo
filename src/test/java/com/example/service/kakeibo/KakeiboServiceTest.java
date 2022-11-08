package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.domain.kakeibo.Settlement;
import com.example.repository.kakeibo.KakeiboMapper;

@SpringBootTest
class KakeiboServiceTest {
	
	@InjectMocks
	private KakeiboService kakeiboService;
	
	@Mock
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
	
	@Test
	@DisplayName("年月が入力されていない時のテスト")
	void testYearAndMonthIsNull() throws Exception {
		when(kakeiboMapper.findKakeiboByYearAndMonth(null, null)).thenReturn(null);
		assertNull(kakeiboService.findKakeiboByYearAndMonth(null, null), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	@Test
	@DisplayName("月のみ入力された時のテスト")
	void testYearIsNull() throws Exception {
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		assertNull(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString()), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	@Test
	@DisplayName("該当年月がない時のテスト")
	void testNotYearAndMonth() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		assertNull(kakeiboService.findKakeiboByYearAndMonth("2022", "12"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}

	@Test
	@DisplayName("年月が入力されている時のテスト")
	void testFindKakeiboByYearAndMonth() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboMonthList);
		List<Kakeibo> kakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "11");
		assertEquals(kakeiboMonthList.getClass(), kakeiboList.getClass());
	}
	
	@Test
	@DisplayName("年のみ入力されている時のテスト")
	void testMontIsNull() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboMonthList);
		List<Kakeibo> kakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "");
		assertEquals(kakeiboMonthList.getClass(), kakeiboList.getClass());
	}
	
	@Test
	@DisplayName("収支計算結果が取得できているかのテスト")
	void testMonthlyBalanceCalculationResult() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
		monthlyBalanceCalculationResult.setTotalIncomeAmount(kakeibo.getIncomeAmount());
		monthlyBalanceCalculationResult.setTotalExpenditureAmount(kakeibo.getExpenditureAmount());
		monthlyBalanceCalculationResult.setBalanceCalculationResult(kakeibo.getIncomeAmount() - kakeibo.getExpenditureAmount());
		
		when(kakeiboMapper.monthlyBalanceCalculate(anyString(), anyString())).thenReturn(monthlyBalanceCalculationResult);
		MonthlyBalanceCalculationResult result = kakeiboService.monthlyBalanceCalculate("2022", "11");
		assertEquals(result, monthlyBalanceCalculationResult);
	}

}
