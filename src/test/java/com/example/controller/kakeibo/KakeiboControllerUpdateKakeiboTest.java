package com.example.controller.kakeibo;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.Settlement;
import com.example.form.kakeibo.EditKakeiboForm;
import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
class KakeiboControllerUpdateKakeiboTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private KakeiboService kakeiboService;
	
	/** editKakeibo(家計簿を更新する画面(edit.html)を表示)のテスト */

	@Test
	@DisplayName("更新画面に遷移")
	void testEditKakeibo() throws Exception {
		// テスト準備
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
		
		when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
		
		// DB登録情報を表示するためセット
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setId(1L);
		editKakeiboForm.setPaymentDate(LocalDate.parse("2022-11-04"));
		editKakeiboForm.setExpenseItemId(2);
		editKakeiboForm.setExpenditureAmount(5000);
		editKakeiboForm.setIncomeAmount(0);
		editKakeiboForm.setExpenseItem(expenseItem);
		editKakeiboForm.setSettlement(settlement);
		
		// 検証&実行
		mockMvc.perform(get("/kakeibo/edit")
				.param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("editKakeiboForm", editKakeiboForm))
				.andExpect(view().name("kakeibo/edit"));
	}
	
	/** updateKakeibo(家計簿を更新する処理)のテスト */
	
	// 異常系
	
	@Test
	@DisplayName("決済日付がnullの時、更新に失敗する")
	void testPaymentDateIsNull() throws Exception {
		// パラメータに値をセット(決済日付以外)
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setPaymentDate(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/update")
				.flashAttr("editKakeiboForm", editKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("editKakeiboForm"))
		.andExpect(view().name("kakeibo/edit"));
	}
	
	@Test
	@DisplayName("費目IDがnullの時、更新に失敗する")
	void testExpenseItemIdIsNull() throws Exception {
		// パラメータに値をセット(費目ID以外)
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setExpenseItemId(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/update")
				.flashAttr("editKakeiboForm", editKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("editKakeiboForm"))
		.andExpect(view().name("kakeibo/edit"));
	}
	
	@Test
	@DisplayName("支出金額がnullの時、更新に失敗する")
	void testExpenditureAmountIsNull() throws Exception {
		// パラメータに値をセット(支出金額以外)
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setExpenditureAmount(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/update")
				.flashAttr("editKakeiboForm", editKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("editKakeiboForm"))
		.andExpect(view().name("kakeibo/edit"));
	}
	
	@Test
	@DisplayName("収入金額がnullの時、更新に失敗する")
	void testIncomeAmountIsNull() throws Exception {
		// パラメータに値をセット(収入金額以外)
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setIncomeAmount(null);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/update")
				.flashAttr("editKakeiboForm", editKakeiboForm))
		.andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("editKakeiboForm"))
		.andExpect(view().name("kakeibo/edit"));
	}
	
	// 正常系
	
	@Test
	@DisplayName("入力項目に問題がない時、更新に成功する")
	void testSuccess() throws Exception {
		
		Kakeibo kakeibo = new Kakeibo();
		
		EditKakeiboForm editKakeiboForm = new EditKakeiboForm();
		editKakeiboForm.setPaymentDate(LocalDate.parse("2022-11-04"));
		editKakeiboForm.setExpenseItemId(2);
		editKakeiboForm.setExpenditureAmount(5000);
		editKakeiboForm.setIncomeAmount(0);
		
		BeanUtils.copyProperties(editKakeiboForm, kakeibo);
		
		when(kakeiboService.updateKakeibo(kakeibo)).thenReturn(true);
		
		// 検証&実行
		mockMvc.perform(post("/kakeibo/update")
				.flashAttr("editKakeiboForm", editKakeiboForm))
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/kakeibo/list"));
	}

}
