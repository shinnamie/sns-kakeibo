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

//	@Test
//	@DisplayName("収支内訳表示のテスト")
//	void testBreakdownIncomeBalance() throws Exception{
//		
//		Map<String,Integer> totalAmountMap = new HashMap<>();
//		totalAmountMap.put("総収入", 0);
//		totalAmountMap.put("総支出", 128465);
//		totalAmountMap.put("収支", -1288465);
//		
//		Map<String,Integer> itemExpenceMap = new HashMap<>();
//		itemExpenceMap.put("税・社会保障", 29000);
//		itemExpenceMap.put("美容", 3000);
//		itemExpenceMap.put("健康・医療", 4550);
//		itemExpenceMap.put("衣服", 1220);
//		itemExpenceMap.put("水道・光熱費", 8000);
//		itemExpenceMap.put("自動車", 12245);
//		itemExpenceMap.put("食費", 5450);
//		itemExpenceMap.put("住宅", 6500);
//		
//		Map<String,Double> rateMap = new HashMap<>();
//		rateMap.put("税・社会保障", 22.57);
//		rateMap.put("美容", 2.34);
//		rateMap.put("健康・医療", 3.54);
//		rateMap.put("衣服", 0.95);
//		rateMap.put("水道・光熱費", 6.23);
//		rateMap.put("自動車", 9.53);
//		rateMap.put("食費", 4.24);
//		rateMap.put("住宅", 50.6);
//		
//		MvcResult result = mockMvc.perform(get("/kakeibo/breakdown-income-balance")
//				.param("date","2022-08-16"))
//				// ステータスコードがOK(200)であるかのテスト
//				.andExpect(status().isOk())
//				// 指定されたHTMLを表示できているかのテスト
//				.andExpect(view().name("kakeibo/breakdown-income-balance"))
//				.andReturn();
//		
////		doReturn(null).when(service).totalIncomeAndExpenditureBreakdown(anyString());
//		ModelAndView mavTotalAmountMap = result.getModelAndView();
//		assertEquals(mavTotalAmountMap.getModel().get("totalAmountMap"),totalAmountMap);
//		
////		ModelAndView mavMonthlyBalanceCalculationMap = result.getModelAndView();
////		assertEquals(mavMonthlyBalanceCalculationMap.getModel().get("itemExpenceMap"));
////		
////		ModelAndView mavDateMap = result.getModelAndView();
////		assertEquals(mavDateMap.getModel().get("rateMap"));
////		
////		ModelAndView mavfirst = result.getModelAndView();
////		assertEquals(firstDateAndLastDate,mavDateMap.getModel().get("firstDateAndLastDate"));
//		
//	}
	
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
