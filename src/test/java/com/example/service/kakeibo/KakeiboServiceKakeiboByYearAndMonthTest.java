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
class KakeiboServiceKakeiboByYearAndMonthTest {
	
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
	
	// 異常系
	
	@Test
	@DisplayName("該当年月が存在しない時、エラーメッセージを返す")
	void testNotYearAndMonth() throws Exception {
		// テスト準備(2022年11月のデータをセット)
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
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		// 検証
		assertNull(kakeiboService.findKakeiboByYearAndMonth("2022", "12"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	// 正常系

	@Test
	@DisplayName("年月共に入力されている時、リストを返す")
	void testFindKakeiboByYearAndMonth() throws Exception {
		// テスト準備(2022年11月のデータをセット)
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
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		// year:2022 , month:11 を渡す
		List<Kakeibo> searchKakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "11");
		// 検証
		assertEquals(kakeiboList.getClass(), searchKakeiboList.getClass());
	}
	
	@Test
	@DisplayName("年のみ入力されている時のテスト")
	void testMontIsNull() throws Exception {
		// テスト準備(2022年11月のデータをセット)
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
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		// year:2022, month:"" を渡す
		List<Kakeibo> searchKakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "");
		// 検証
		assertEquals(kakeiboList.getClass(), searchKakeiboList.getClass());
	}
	
	@Test
	@DisplayName("正常な値の時、収支計算結果を表示する")
	void testMonthlyBalanceCalculationResult() throws Exception {
		// テスト準備(2022年11月のデータをセット)
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
		
		// 収支結果に値をセット
		MonthlyBalanceCalculationResult calc = new MonthlyBalanceCalculationResult();
		calc.setTotalIncomeAmount(kakeibo.getIncomeAmount());
		calc.setTotalExpenditureAmount(kakeibo.getExpenditureAmount());
		calc.setBalanceCalculationResult(kakeibo.getIncomeAmount() - kakeibo.getExpenditureAmount());
		
		when(kakeiboMapper.monthlyBalanceCalculate(anyString(), anyString())).thenReturn(calc);
		// year:2022 , month:11 を渡す
		MonthlyBalanceCalculationResult result = kakeiboService.monthlyBalanceCalculate("2022", "11");
		// 検証
		assertEquals(result, calc);
	}

}
