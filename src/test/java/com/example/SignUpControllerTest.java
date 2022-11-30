package com.example;

import static org.mockito.ArgumentMatchers.anyString;
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
		// 新規登録情報セット
		user.setMailAdress("test@gmail.com");
		user.setPassword("testtest");
		
		signUpForm.setMailAddress("test@gmail.com");
		signUpForm.setPassword("testtest");
		signUpForm.setConfirmPassword("testtest");
		
	}

	
	@Test
	@DisplayName("新規ユーザー登録画面(user/signUp.html)に遷移するかのテスト")
	void testToSignUpPage() throws Exception{
		
		mockMvc.perform(get("user/signUp"))
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
	
}
