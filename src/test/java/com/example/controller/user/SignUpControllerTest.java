package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.example.domain.user.User;
import com.example.form.user.SignUpForm;
import com.example.service.user.SignUpService;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

	User user = new User();
	SignUpForm signUpForm = new SignUpForm();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SignUpService signUpService;

	@BeforeEach
	void setUp() throws Exception {
		user.setMailAddress("test@gmail.com");
		user.setPassword("testtest");

		signUpForm.setMailAddress("test@gmail.com");
		signUpForm.setPassword("testtest");
		signUpForm.setConfirmPassword("testtest");

	}


	@Test
	@DisplayName("新規ユーザー登録画面(user/signUp.html)に遷移するかのテスト")
	void testToSignUpPage() throws Exception{

		mockMvc.perform(get("/user/signUp"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/signUp"));

	}

	//正常系

	@Test
	@DisplayName("メールアドレス・パスワード・確認パスワードが正しく入力された時、ログイン画面へ遷移する")
	void testSuccess() throws Exception{


		// テスト
		when(signUpService.signUp(user)).thenReturn(true);

		// 検証
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpForm", signUpForm))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/user/login"))
		.andExpect(model().hasNoErrors());

	}

	//異常系

	@Test
	@DisplayName("メールアドレスが正しく入力されなかった時、新規ユーザー登録画面へ戻る")
	void testMailAddressFailure() throws Exception{

		// 準備
		// User情報セット(メールアドレス：異常）
		signUpForm.setMailAddress("aaa");

		// 検証
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpForm", signUpForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/signUp"));

	}


	@Test
	@DisplayName("パスワードが8文字未満の時、新規ユーザー登録画面へ戻る")
	void testPasswordMin() throws Exception{

		// 準備
		// User情報セット(パスワード：7文字）
		signUpForm.setPassword("testtes");

		// 検証
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpForm", signUpForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/signUp"));
	}
	@Test
	@DisplayName("パスワードが17文字以上の時、新規ユーザー登録画面へ戻る")
	void testPasswordMax() throws Exception{

		// 準備
		// User情報セット(パスワード：17文字）
		signUpForm.setPassword("testtesttesttesttestt");

		// 検証
		mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpForm", signUpForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/signUp"));
	}

	@Test
	@DisplayName("確認用パスワードが不一致の時、エラーメッセージを表示する")
	void testConfirmPasswordFalse() throws Exception{

		// 準備
		// User情報セット(確認用パスワード：異常）
		signUpForm.setConfirmPassword("test");

		// 検証
		MvcResult mvcResult = mockMvc.perform(post("/user/signUp")
				.flashAttr("signUpForm", signUpForm))
				.andExpect(status().isOk())
				.andExpect(view().name("user/signUp"))
				.andReturn();

		// Modelの格納情報を検証
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals("確認用パスワードはパスワードと同じものを入力してください", modelAndView.getModel().get("passwordErrorMessage"));
	}

}
