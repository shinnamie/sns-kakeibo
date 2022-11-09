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
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    
    
	/**
     * 家計簿一覧表示画面の検証
     */
	
	@Test
	@DisplayName("一覧表示のテスト")
	void findAllTest() throws Exception{
		List<Kakeibo> kakeiboList = new ArrayList<>();
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
		kakeiboList.add(kakeibo);
		
        when(kakeiboService.findKakeiboList()).thenReturn(kakeiboList);
		
		MvcResult mvcResult = mockMvc.perform(get("/kakeibo/list"))
        .andExpect(status().isOk())
        .andExpect(view().name("kakeibo/list")) 
        .andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		assertEquals(mav.getModel().get("kakeiboList"), kakeiboList);
		
	}
	
}
