package com.example.controller.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
class KakeiboControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private KakeiboService service;

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
	@DisplayName("収支内訳表示のテスト")
	void testBreakdownIncomeBalance() throws Exception{
		//mapにテスト用の値を格納
		
		when(service.totalByIncomeAndExpenditureBreakdown("2022-08")).thenReturn(breakdownMap);
		
		MvcResult result = mockMvc.perform(get("/kakeibo/breakdown-income-balance")
				.param("date","2022-08-16"))
				// ステータスコードがOK(200)であるかのテスト
				.andExpect(status().isOk())
				// 指定されたHTMLを表示できているかのテスト
				.andExpect(view().name("kakeibo/breakdown-income-balance"))
				.andReturn();
		
		ModelAndView mavItemMap = result.getModelAndView();
		assertEquals(mavItemMap.getModel().get("totalAmountMap"));
		
		ModelAndView mavMonthlyBalanceCalculationMap = result.getModelAndView();
		assertEquals(mavMonthlyBalanceCalculationMap.getModel().get("itemExpenceMap"));
		
		ModelAndView mavDateMap = result.getModelAndView();
		assertEquals(mavDateMap.getModel().get("rateMap"));
		
	}

}
