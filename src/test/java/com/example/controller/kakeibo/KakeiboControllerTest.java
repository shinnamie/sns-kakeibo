package com.example.controller.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.domain.kakeibo.Settlement;
import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
class KakeiboControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private KakeiboService kakeiboService;

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
	@DisplayName("年別・月別集計画面に遷移")
	void testGetKakeiboByYearAndMonth() throws Exception {
		mockMvc.perform(get("/kakeibo/kakeiboByYearAndMonth"))
		.andExpect(status().isOk())
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
	
	@Test
	@DisplayName("年月が入力されていない時のテスト")
	void testYearAndMonthIsNull() throws Exception {
		when(kakeiboService.findKakeiboByYearAndMonth(null, null)).thenReturn(null);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("message"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	@Test
	@DisplayName("月のみ入力された時のテスト")
	void testYearIsNull() throws Exception {
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("month", "11"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("message"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	@Test
	@DisplayName("該当年月がない時のテスト")
	void testNotYearAndMonth() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "12"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("message"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	@Test
	@DisplayName("年月共に入力されている時のテスト")
	void testYearAndMonthIsNotNull() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboMonthList);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "11"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("kakeiboMonthList"), kakeiboMonthList);
	}
	
	@Test
	@DisplayName("年のみ入力されている時のテスト")
	void testMonthIsNull() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboMonthList);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("kakeiboMonthList"), kakeiboMonthList);
	}
	
	@Test
	@DisplayName("収支計算結果の値が格納されているかのテスト")
	void testMonthlyBalanceCalculationResult() throws Exception {
		// テスト準備
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
		monthlyBalanceCalculationResult.setTotalIncomeAmount(kakeibo.getIncomeAmount());
		monthlyBalanceCalculationResult.setTotalExpenditureAmount(kakeibo.getExpenditureAmount());
		monthlyBalanceCalculationResult.setBalanceCalculationResult(kakeibo.getIncomeAmount() - kakeibo.getExpenditureAmount());
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboMonthList);
		when(kakeiboService.monthlyBalanceCalculate(anyString(), anyString())).thenReturn(monthlyBalanceCalculationResult);
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "11"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals(modelAndView.getModel().get("monthlyBalanceCalculationResult"), monthlyBalanceCalculationResult);
	}

}
