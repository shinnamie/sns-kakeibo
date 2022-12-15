package com.example.controller.kakeibo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
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

	Kakeibo kakeibo = new Kakeibo();
	EditKakeiboForm editKakeiboForm = new EditKakeiboForm();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private KakeiboService kakeiboService;

	@BeforeEach
	void setUp() throws Exception {
		// テスト準備
		kakeibo.setId(1L);
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-04"));
		kakeibo.setExpenseItemId(2L);
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

		// DB登録情報を表示するためセット
		editKakeiboForm.setId(1L);
		editKakeiboForm.setPaymentDate(LocalDate.parse("2022-11-04"));
		editKakeiboForm.setExpenseItemId(2);
		editKakeiboForm.setExpenditureAmount(5000);
		editKakeiboForm.setIncomeAmount(0);
		editKakeiboForm.setExpenseItem(expenseItem);
		editKakeiboForm.setSettlement(settlement);
	}

	/** editKakeibo(家計簿を更新する画面(edit.html)を表示)のテスト */

	@Test
	@DisplayName("更新画面に遷移")
	void testEditKakeibo() throws Exception {
		
		
		when(kakeiboService.findByKakeiboId(anyLong())).thenReturn(kakeibo);
		
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
		// 決済日付をnullでセット
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
		// 費目IDをnullでセット
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
		// 支出金額をnullでセット
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
		// 収入以外をnullでセット
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

		// 更新する値をセット
		editKakeiboForm.setPaymentDate(LocalDate.parse("2022-11-11"));
		editKakeiboForm.setExpenseItemId(8);
		editKakeiboForm.setExpenditureAmount(3000);
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
