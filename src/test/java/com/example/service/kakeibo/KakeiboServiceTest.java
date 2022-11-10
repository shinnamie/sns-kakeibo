package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
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

//	@Test
//	@DisplayName("費目名・費目別支出・総収入・総支出・収支)をMapで返すテスト")
//	void testFindBreakdown() throws Exception{
//		//費目別(費目名・費目別支出・総収入・総支出・収支)を格納するmapを生成
//		Map<String,Integer> map = new HashMap<>();
//		
//		//月別(収入、支出、収支)
//		家賃＋食費=0000
//		map.put(総支出, 0000)
//		
//		//今日の年月を渡して今月の費目別(費目名、支出、収入)を返す （ここで収入も。kaakeiboのドメイン。kakeiboの総支出と総収入をkakeiboでもってきて、拡張for文でまわして）
//		when(kakeiboMapper.findPaymentOfItem("yearAndMonth").thenReturn(kakeiboList);
//		
//		assertEquals();
//	}
	
	@Test
	@DisplayName("費目とその割合をMapで返すテスト")
	void calcExpeditureRate() throws Exception{
		//map生成
		Map<String,Integer> monthlyBalanceCalculationMap = new HashMap<>();
		
	}
	
//	@Test
//	@DisplayName("登録されている家計簿情報を年別・月別に集計し、Listで返すテスト")
//	void testFindKakeiboByYearAndMonth() throws Exception{
//		//List生成
//		List<Kakeibo> kakeiboList = new ArrayList<Kakeibo>();
//		Kakeibo kakeibo = new Kakeibo();
//		kakeibo.setId(1);
//		kakeibo.setUserId(1);
//		kakeibo.setSettlementDate(LocalDate.now());
//		kakeibo.setExpenseItemId(1);
//		kakeibo.setExpenditureAmount(1000);
//		kakeibo.setIncomeAmount(1000);
//		kakeibo.setSettlementId(1);
//		kakeibo.setUsedStore("コンビニ");
//		kakeibo.setRemarks("備考");
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		kakeibo.setInsertAt(timestamp);
//		kakeibo.setUpdateAt(timestamp);
//		ExpenseItem expenseItem = new ExpenseItem();
//		expenseItem.setId(1);
//		expenseItem.setExpenseItemName("水");
//		kakeibo.setExpenseItem(expenseItem);
//		Settlement settlement = new Settlement();
//		settlement.setId(1);
//		settlement.setSettlementName("クレジットカード");
//		kakeibo.setSettlement(settlement);
//		kakeiboList.add(kakeibo);
//		
//	}

}
