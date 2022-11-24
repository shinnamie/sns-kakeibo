package com.example.findAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.repository.kakeibo.KakeiboMapper;
import com.example.service.kakeibo.KakeiboService;




@SpringBootTest
class FindKakeiboListServiceTest {

	@InjectMocks
	KakeiboService service;

	@Mock
	KakeiboMapper mapper;

	@Test
	@DisplayName("家計簿一覧表示のサービスクラステスト（正常）")
	void testKakeiboList() throws Exception {
		List<Kakeibo> kakeiboList = new ArrayList<>();
		Kakeibo kakeibo1 = new Kakeibo();
		kakeibo1.setId(1);
		kakeibo1.setPaymentDate(LocalDate.parse("2022-11-04"));
		kakeibo1.setExpenseItemId(2);
		kakeibo1.setExpenditureAmount(5000);
		kakeibo1.setIncomeAmount(0);
		ExpenseItem expenseItem1 = new ExpenseItem();
		expenseItem1.setId(4);
		expenseItem1.setExpenseItemName("食費");
		kakeibo1.setExpenseItem(expenseItem1);
		Settlement settlement1 = new Settlement();
		settlement1.setId(2);
		settlement1.setSettlementName("現金");
		kakeibo1.setSettlement(settlement1);
		kakeiboList.add(kakeibo1);
		
		when(service.findKakeiboList()).thenReturn(kakeiboList);
		
		//正常処理
		assertEquals(mapper.findAll() , kakeiboList);   
	}

	@Test
	@DisplayName("家計簿一覧表示のサービスクラステスト（異常）")
	void testKakeiboList2() throws Exception {
		
		when(service.findKakeiboList()).thenReturn(null);
		
		List<Kakeibo> kakeiboList2 = mapper.findAll();

		//異常処理
		assertNotEquals(mapper.findAll() , kakeiboList2);
	}
}
