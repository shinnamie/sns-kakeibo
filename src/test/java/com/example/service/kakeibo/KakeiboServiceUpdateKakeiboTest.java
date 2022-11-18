package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
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
	
	private Kakeibo kakeibo;
	
	@BeforeEach
	void setUp() throws Exception {
		kakeibo = new Kakeibo();
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
		
		when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
		
		// 更新後の値をセット
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-11"));
		kakeibo.setExpenseItemId(8);
		kakeibo.setExpenditureAmount(3000);
		kakeibo.setIncomeAmount(0);
		
		// Mapperクラス実行
		kakeiboMapper.update(kakeibo);
		System.out.println(kakeibo);
		
		// 更新後を取得
		Kakeibo updateKakeibo = kakeiboService.findByKakeiboId(1L);
		System.out.println(updateKakeibo);
		
		// 検証(更新後の値になっているか)
		assertEquals(kakeibo.getPaymentDate(), updateKakeibo.getPaymentDate());
		assertEquals(kakeibo.getExpenseItemId(), updateKakeibo.getExpenseItemId());
		assertEquals(kakeibo.getExpenditureAmount(), updateKakeibo.getExpenditureAmount());
		assertEquals(kakeibo.getIncomeAmount(), updateKakeibo.getIncomeAmount());
	}
	
	// 異常系
	
		@Test
		@DisplayName("決済日時がnullの時、更新に失敗する")
		void testPaymentDateIsNull() throws Exception {
			
			when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
			
			// 更新後の値をセット
			kakeibo.setPaymentDate(null);
		
			// 検証
			boolean autual = kakeiboMapper.update(kakeibo);
			assertFalse(autual);
		}
		
		@Test
		@DisplayName("費目IDがnullの時、更新に失敗する")
		void testExpenseItemIdIsNull() throws Exception {
			
			when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
			
			// 更新後の値をセット
			kakeibo.setExpenseItemId(null);
		
			// 検証
			boolean autual = kakeiboMapper.update(kakeibo);
			assertFalse(autual);
		}
		
		@Test
		@DisplayName("支出金額がnullの時、更新に失敗する")
		void testError() throws Exception {
			
			when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
			
			// 更新後の値をセット
			kakeibo.setExpenditureAmount(null);
		
			// 検証
			boolean autual = kakeiboMapper.update(kakeibo);
			assertFalse(autual);
		}
		
		@Test
		@DisplayName("収入金額がnullの時、更新に失敗する")
		void testIncomeAmounIsNull() throws Exception {
			
			when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
			
			// 更新後の値をセット
			kakeibo.setIncomeAmount(null);
		
			// 検証
			boolean autual = kakeiboMapper.update(kakeibo);
			assertFalse(autual);
		}

}
