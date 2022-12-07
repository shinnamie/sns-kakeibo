package com.example.controller.kakeibo;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.form.kakeibo.AddKakeiboForm;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class KakeiboControllerSaveKakeiboTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	MessageSource messageSource;

	/** getRegisterKakeibo(家計簿を追加する画面(register.html)を表示)のテスト */

	@Test
	@DisplayName("家計簿を追加する画面(register.html)を表示することを期待します")
	void expectToShowRegister() throws Exception {

		// 準備(メッセージプロパティからタイトルを取得)
		String title = messageSource.getMessage("register.title", null, Locale.JAPAN);

		// 実行&検証
		mockMvc.perform(get("/kakeibo/registerKakeibo"))
				// ステータスコードが、OK (200)であることを検証
				.andExpect(status().isOk())
				// HTMLの表示内容に、タイトル名が含まれていることを確認
				.andExpect(content().string(containsString(title)))
				// 指定したHTMLを表示しているか検証
				.andExpect(view().name("kakeibo/register"));

	}

	/** saveKakeibo(家計簿の追加処理)のテスト */

	@Test
	@DisplayName("入力エラーがある時、エラー表示することを期待します")
	void whenThereIsError_expectToShowErrors() throws Exception {

		// 実行&検証
		mockMvc.perform(
				post("/kakeibo/saveKakeibo")
						// フォームに値を格納(null)
						.flashAttr("addKakeiboForm", new AddKakeiboForm())
						// CSRFトークンを自動挿入
						.with(csrf()))
				// エラーがあることを検証
				.andExpect(model().hasErrors())
				// 指定したHTMLを表示しているか検証
				.andExpect(view().name("kakeibo/register"));

	}

	@Test
	@DisplayName("正しい値が送られた時、登録が成功することを期待します")
	void whenValidInformation_expectToSuccess() throws Exception {

		// 準備(フォームに値を格納)
		AddKakeiboForm form = new AddKakeiboForm();
		form.setUserId(1);
		LocalDate date = LocalDate.now();
		form.setPaymentDate(date);
		form.setExpenseItemId(1);
		form.setExpenditureAmount(10000);
		form.setIncomeAmount(0);
		form.setUsedStore("コンビニ");
		form.setRemarks("お醤油");

		// 実行&検証
		mockMvc.perform(
				post("/kakeibo/saveKakeibo")
						// フォームに値を格納
						.flashAttr("addKakeiboForm", form)
						// CSRFトークンを自動挿入
						.with(csrf()))
				// エラーがないことを検証
				.andExpect(model().hasNoErrors())
				// 指定したURLに、リダイレクトすることを検証
				.andExpect(redirectedUrl("/kakeibo/list"))
				// ステータスコードが、Found (302)であることを検証
				.andExpect(status().isFound());

	}

}
