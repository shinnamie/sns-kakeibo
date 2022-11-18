package com.example.controller.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.service.kakeibo.KakeiboService;

//import org.junit.runner.RunWith;

@SpringBootTest
@AutoConfigureMockMvc
class KakeiboControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private KakeiboService service;
	
	@InjectMocks
	private KakeiboController controller;
	

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
	@DisplayName("正常系：収支内訳表示のテスト")
	void testBreakdownIncomeBalance() throws Exception{
		
		LocalDate date = LocalDate.now();
		doReturn("2022/11/01 - 2022/11/30").when(service).getFirstDayAndLastDay(date);
		
		String yearAndMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));

		//Listを準備
		List<Kakeibo> kakeiboList = new ArrayList<>();
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setId(1);
		kakeibo.setSettlementDate(LocalDate.parse("2022-11-13"));
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
		
		when(service.totalByIncomeAndExpenditureBreakdown(yearAndMonth)).thenReturn(kakeiboList);
		
		Map<String,Integer> kakeiboMap = new HashMap<>();
		kakeiboMap.put("総収入", 0);
		kakeiboMap.put("総支出", 5000);
		kakeiboMap.put("収支", -5000);
		kakeiboMap.put("食費", 5000);
		doReturn(kakeiboMap).when(service).findBreakdown(kakeiboList);
		
		Map<String,Integer> totalAmountMap = new HashMap<>();
		totalAmountMap.put("総収入", 0);
		totalAmountMap.put("総支出", 5000);
		totalAmountMap.put("収支", -5000);
		doReturn(totalAmountMap).when(service).totalAmountMap(kakeiboMap);
				
		Map<String,Integer> itemExpenseMap = new HashMap<>();
		itemExpenseMap.put("食費", 5000);
		doReturn(itemExpenseMap).when(service).itemExpenseMap(kakeiboMap);
		
		Map<String,Double> rateMap = new HashMap<>();
		Map<String,Double> integerToDoubleMap = new HashMap<>();
		integerToDoubleMap.put("食費", 5000.0);
		rateMap.put("食費", 100.0);
		doReturn(integerToDoubleMap).when(service).integerToDouble(kakeiboMap);
		doReturn(rateMap).when(service).culcRate(integerToDoubleMap);

		
		MvcResult result = mockMvc.perform(get("/kakeibo/breakdown")
				.param("date","2022-11-16"))
				// ステータスコードがOK(200)であるかのテスト
				.andExpect(status().isOk())
				// 指定されたHTMLを表示できているかのテスト
				.andExpect(view().name("kakeibo/breakdown-income-balance"))
				.andReturn();
		
		ModelAndView mavTotalAmountMap = result.getModelAndView();
		assertEquals(totalAmountMap,mavTotalAmountMap.getModel().get("totalAmountMap"));
		
		ModelAndView mavItemExpenseMap = result.getModelAndView();
		assertEquals(itemExpenseMap,mavItemExpenseMap.getModel().get("itemExpenceMap"));
		
		ModelAndView mavRateMap = result.getModelAndView();
		assertEquals(rateMap,mavRateMap.getModel().get("rateItemMap"));
		
		ModelAndView mavfirst = result.getModelAndView();
		assertEquals("2022/11/01 - 2022/11/30",mavfirst.getModel().get("firstDateAndLastDate"));
		
	}
	
	@Test
	@DisplayName("異常系：収支内訳表示のテスト")
	void testBreakdownIncomeBalanceNull() throws Exception{
		
		LocalDate date = LocalDate.now();
		doReturn("2022/11/01 - 2022/11/30").when(service).getFirstDayAndLastDay(date);
		
		String yearAndMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
		List<Kakeibo> kakeiboList = null;
		when(service.totalByIncomeAndExpenditureBreakdown(yearAndMonth)).thenReturn(kakeiboList);
		
		MvcResult result = mockMvc.perform(get("/kakeibo/breakdown")
				.param("date","2022-11-16"))
				// ステータスコードがOK(200)であるかのテスト
				.andExpect(status().isOk())
				// 指定されたHTMLを表示できているかのテスト
				.andExpect(view().name("kakeibo/breakdown-income-balance"))
				.andReturn();
		
		ModelAndView mavTotalAmountMap = result.getModelAndView();
		ModelAndView message = result.getModelAndView();
		assertEquals("2022/11/01 - 2022/11/30",mavTotalAmountMap.getModel().get("firstDateAndLastDate"));
		assertEquals("該当月のデータが存在しません。",message.getModel().get("message"));
		
	}

}
