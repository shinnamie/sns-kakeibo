package com.example.findAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import static org.mockito.Mockito.when;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
public class GetListControllerTest {
	
	List<Kakeibo> kakeiboList;
	Kakeibo kakeibo1;
	Kakeibo kakeibo2;

	
    @MockBean
    private KakeiboService kakeiboService;
    
    
    

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		kakeiboList = new ArrayList<>();
		kakeibo1 = new Kakeibo();
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
		
		kakeibo2 = new Kakeibo();
		kakeibo2.setId(2);
		kakeibo2.setPaymentDate(LocalDate.parse("2022-11-04"));
		kakeibo2.setExpenseItemId(2);
		kakeibo2.setExpenditureAmount(5000);
		kakeibo2.setIncomeAmount(0);
		ExpenseItem expenseItem2 = new ExpenseItem();
		expenseItem2.setId(4);
		expenseItem2.setExpenseItemName("食費");
		kakeibo2.setExpenseItem(expenseItem2);
		Settlement settlement2 = new Settlement();
		settlement2.setId(2);
		settlement2.setSettlementName("現金");
		kakeibo2.setSettlement(settlement2);
		kakeiboList.add(kakeibo2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    
    
	/**
     * 家計簿一覧表示画面の検証
     */
	
	@Test
	@DisplayName("一覧表示のテスト（正常）")
	void findAllTest() throws Exception{
		
		
		
		
        when(kakeiboService.findKakeiboList()).thenReturn(kakeiboList);
		
		MvcResult mvcResult = mockMvc.perform(get("/kakeibo/list"))
        .andExpect(status().isOk())
        .andExpect(view().name("kakeibo/list")) 
        .andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		assertEquals(mav.getModel().get("kakeiboList"), kakeiboList);

	}
	@Test
	@DisplayName("一覧表示のテスト（異常）")
	void findAllErrorTest() throws Exception{

		when(kakeiboService.findKakeiboList()).thenReturn(null);

		MvcResult mvcResult = mockMvc.perform(get("/kakeibo/list"))
				.andExpect(view().name("kakeibo/list"))
				.andReturn();

		ModelAndView mav = mvcResult.getModelAndView();
		assertEquals(mav.getModel().get("kakeiboList"), null);

		
	}
}
