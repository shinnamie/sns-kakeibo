package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.repository.kakeibo.KakeiboMapper;

@SpringBootTest
class KakeiboServiceUpdateKakeiboTest {

	private Kakeibo kakeibo;

	@BeforeEach
	void setUp() throws Exception {
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
	}

	@InjectMocks
	private KakeiboService kakeiboService;

	@Mock
	private KakeiboMapper kakeiboMapper;

	/** 入力値チェックに問題がない場合、更新処理ができるかのテスト */

	// 正常系

	@Test
	@DisplayName("入力項目に問題がない時、更新に成功する")
	void testSuccess() throws Exception {

		// 更新後の値をセット
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-11"));
		kakeibo.setExpenseItemId(8L);
		kakeibo.setExpenditureAmount(3000);
		kakeibo.setIncomeAmount(0);

		when(kakeiboMapper.update(kakeibo)).thenReturn(true);

		// 検証
		boolean actual = kakeiboService.updateKakeibo(kakeibo);
		assertTrue(actual);
	}

	// 異常系

	@Test
	@DisplayName("決済日付がnullの時、更新に失敗する")
	void testPaymentDateIsNull() throws Exception {

		// 決済日付をnullでセット
		kakeibo.setPaymentDate(null);

		when(kakeiboMapper.update(kakeibo)).thenThrow(new DataIntegrityViolationException(null));

		// 検証
		boolean autual = kakeiboService.updateKakeibo(kakeibo);
		assertFalse(autual);
	}

	@Test
	@DisplayName("費目IDがnullの時、更新に失敗する")
	void testExpenseItemIdIsNull() throws Exception {

		// 費目IDをnullでセット
		kakeibo.setExpenseItemId(null);

		when(kakeiboMapper.update(kakeibo)).thenThrow(new DataIntegrityViolationException(null));

		// 検証
		boolean autual = kakeiboService.updateKakeibo(kakeibo);
		assertFalse(autual);
	}

	@Test
	@DisplayName("支出金額がnullの時、更新に失敗する")
	void testError() throws Exception {

		// 支出金額をnullでセット
		kakeibo.setExpenditureAmount(null);

		when(kakeiboMapper.update(kakeibo)).thenThrow(new DataIntegrityViolationException(null));

		// 検証
		boolean autual = kakeiboService.updateKakeibo(kakeibo);
		assertFalse(autual);
	}

	@Test
	@DisplayName("収入金額がnullの時、更新に失敗する")
	void testIncomeAmounIsNull() throws Exception {

		// 収入金額をnullでセット
		kakeibo.setIncomeAmount(null);

		when(kakeiboMapper.update(kakeibo)).thenThrow(new DataIntegrityViolationException(null));

		// 検証
		boolean autual = kakeiboService.updateKakeibo(kakeibo);
		assertFalse(autual);
	}

}
