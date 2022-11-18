package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.repository.kakeibo.KakeiboMapper;

@SpringBootTest
class KakeiboServiceUpdateKakeiboTest {
	
	@InjectMocks
	private KakeiboService kakeiboService;
	
	@Mock
	private KakeiboMapper kakeiboMapper;
	
	/** 入力値チェックに問題がない場合、更新処理ができるかのテスト */
	
	// 正常系

	@Test
	@DisplayName("入力項目に問題がない時、更新に成功する")
	void testSuccess() throws Exception {
		// テスト準備
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
		
		when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
		
		// 更新後の値をセット
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-11"));
		kakeibo.setExpenseItemId(8);
		kakeibo.setExpenditureAmount(3000);
		kakeibo.setIncomeAmount(0);
		
		// Mapperクラス実行
		kakeiboMapper.update(kakeibo);
		
		// 検証(更新後の値になっているか)
		assertEquals(kakeibo.getPaymentDate(), LocalDate.parse("2022-11-11"));
		assertEquals(kakeibo.getExpenseItemId(), 8);
		assertEquals(kakeibo.getExpenditureAmount(), 3000);
		assertEquals(kakeibo.getIncomeAmount(), 0);
	}

}
