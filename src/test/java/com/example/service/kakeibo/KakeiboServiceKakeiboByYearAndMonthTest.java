package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	private List<Kakeibo> kakeiboList;
	private Kakeibo kakeibo;

	@BeforeEach
	void setUp() throws Exception {
		// テスト準備(2022年11月のデータをセット)
		kakeiboList = new ArrayList<>();
		kakeibo = new Kakeibo();
		kakeibo.setId(1L);
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-04"));
		kakeibo.setExpenseItemId(2L);
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
	}

	@InjectMocks
	private KakeiboService kakeiboService;

	@Mock
	private KakeiboMapper kakeiboMapper;

	// 異常系

	@Test
	@DisplayName("該当年月が存在しない時、エラーメッセージを返す")
	void testNotYearAndMonth() throws Exception {
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		// 検証
		// year:2022 , month:12 を渡す
		assertNull(kakeiboService.findKakeiboByYearAndMonth("2022", "12"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}

	// 正常系

	@Test
	@DisplayName("年月共に入力されている時、リストを返す")
	void testFindKakeiboByYearAndMonth() throws Exception {
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		// year:2022 , month:11 を渡す
		List<Kakeibo> searchKakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "11");
		// 検証
		assertEquals(kakeiboList.getClass(), searchKakeiboList.getClass());
	}

	@Test
	@DisplayName("年のみ入力されている時のテスト")
	void testMontIsNull() throws Exception {
		
		when(kakeiboMapper.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		// year:2022, month:"" を渡す
		List<Kakeibo> searchKakeiboList = kakeiboService.findKakeiboByYearAndMonth("2022", "");
		// 検証
		assertEquals(kakeiboList.getClass(), searchKakeiboList.getClass());
	}

	@Test
	@DisplayName("正常な値の時、収支計算結果を表示する")
	void testMonthlyBalanceCalculationResult() throws Exception {

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
