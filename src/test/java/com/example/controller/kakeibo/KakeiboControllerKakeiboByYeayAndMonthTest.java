package com.example.controller.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import com.example.form.kakeibo.SearchKakeiboForm;
import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
class KakeiboControllerKakeiboByYeayAndMonthTest {
	
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
	
	/** getKakeiboByYearAndMonth(年別・月別集計結果を表示する画面(kakeiboByYearAndMonth.html)を表示)のテスト */

	@Test
	@DisplayName("年別・月別集計画面に遷移")
	void testGetKakeiboByYearAndMonth() throws Exception {
		// 検証&実行
		mockMvc.perform(get("/kakeibo/kakeiboByYearAndMonth"))
		.andExpect(status().isOk())
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
	
	/** postKakeiboByYearAndMonth(年別・月別集計結果処理)のテスト */
	
	// 異常系
	
	@Test
	@DisplayName("該当年月が存在しない時、エラーメッセージを返す")
	void testNotYearAndMonth() throws Exception {
		// テスト準備(2022年11月のデータをセット)
		List<Kakeibo> kakeiboMonthList = new ArrayList<>();
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
		kakeiboMonthList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(null);
		// 検証&実行
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "12"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		// modelの格納情報をセット
		ModelAndView modelAndView = mvcResult.getModelAndView();
		// 検証
		assertEquals(modelAndView.getModel().get("message"), "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
	}
	
	// 正常系
	
	@Test
	@DisplayName("年月共に入力されている時、リストを返す")
	void testYearAndMonthIsNotNull() throws Exception {
		// テスト準備(2022年11月のデータをセット)
		List<Kakeibo> kakeiboList = new ArrayList<>();
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
		kakeiboList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		// 検証&実行
		// year:2022 , month:11 を渡す
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "11"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		// modelの格納情報をセット
		ModelAndView modelAndView = mvcResult.getModelAndView();
		// 検証
		assertEquals(modelAndView.getModel().get("kakeiboList"), kakeiboList);
	}
	
	@Test
	@DisplayName("年のみ入力されている時のテスト")
	void testMonthIsNull() throws Exception {
		// テスト準備(2022年11月のデータをセット)
		List<Kakeibo> kakeiboList = new ArrayList<>();
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
		kakeiboList.add(kakeibo);
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
	
		// 検証&実行
		// year:2022 , month:"" を渡す
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		// modelの格納情報をセット
		ModelAndView modelAndView = mvcResult.getModelAndView();
		// 検証
		assertEquals(modelAndView.getModel().get("kakeiboList"), kakeiboList);
	}
	
	@Test
	@DisplayName("正常な値の時、収支計算結果を表示する")
	void testMonthlyBalanceCalculationResult() throws Exception {
		// テスト準備(2022年11月のデータをセット)
		List<Kakeibo> kakeiboList = new ArrayList<>();
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
		kakeiboList.add(kakeibo);
		
		// 収支結果に値をセット
		MonthlyBalanceCalculationResult calc = new MonthlyBalanceCalculationResult();
		calc.setTotalIncomeAmount(kakeibo.getIncomeAmount());
		calc.setTotalExpenditureAmount(kakeibo.getExpenditureAmount());
		calc.setBalanceCalculationResult(kakeibo.getIncomeAmount() - kakeibo.getExpenditureAmount());
		
		when(kakeiboService.findKakeiboByYearAndMonth(anyString(), anyString())).thenReturn(kakeiboList);
		when(kakeiboService.monthlyBalanceCalculate(anyString(), anyString())).thenReturn(calc);
		// 検証&実行
		// year:2022 , month:11 を渡す
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.param("year", "2022")
				.param("month", "11"))
				.andExpect(status().isOk())
				.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"))
				.andReturn();
		// modelの格納情報をセット
		ModelAndView modelAndView = mvcResult.getModelAndView();
		// 検証
		assertEquals(modelAndView.getModel().get("result"), calc);
	}
	
	/** 入力値チェックに関する項目 */
	
	// 異常系
	
	@Test
	@DisplayName("年を入力しなかった時、エラーとなる(入力値チェック)")
	void testNull() throws Exception {
		SearchKakeiboForm searchKakeiboForm = new SearchKakeiboForm();
		searchKakeiboForm.setYear(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.flashAttr("searchKakeiboForm", searchKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("searchKakeiboForm"))
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
	
	@Test
	@DisplayName("月のみ入力した時、エラーとなる(入力値チェック)")
	void testYearIsNull() throws Exception {
		SearchKakeiboForm searchKakeiboForm = new SearchKakeiboForm();
		searchKakeiboForm.setMonth("11");
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.flashAttr("searchKakeiboForm", searchKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("searchKakeiboForm"))
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
	
	// 正常系
	
	@Test
	@DisplayName("年月共に入力した時、正常に表示される(入力値チェック)")
	void testYearAndMonth() throws Exception {
		SearchKakeiboForm searchKakeiboForm = new SearchKakeiboForm();
		searchKakeiboForm.setYear("2022");
		searchKakeiboForm.setMonth("11");
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.flashAttr("searchKakeiboForm", searchKakeiboForm))
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
	
	@Test
	@DisplayName("年のみ入力した時、正常に表示される(入力値チェック)")
	void testYear() throws Exception {
		SearchKakeiboForm searchKakeiboForm = new SearchKakeiboForm();
		searchKakeiboForm.setYear("2022");
		searchKakeiboForm.setMonth(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/kakeiboByYearAndMonth")
				.flashAttr("searchKakeiboForm", searchKakeiboForm))
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("kakeibo/kakeiboByYearAndMonth"));
	}
}
