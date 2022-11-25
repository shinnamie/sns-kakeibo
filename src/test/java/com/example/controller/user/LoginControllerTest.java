package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.user.User;
import com.example.form.user.LoginForm;
import com.example.service.user.LoginService;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
	
	static User user = new User();
	LoginForm loginForm = new LoginForm();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private LoginService loginService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		user.setMailAddress("test@gmail.com");
		user.setPassword("testtest");
	}

	@Test
	@DisplayName("ログイン画面(user/login.html)に遷移するかのテスト")
	void testToLoginPage() throws Exception {
		
		mockMvc.perform(get("/user/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("user/login"));
	}
	
	// 正常系
	
	@Test
	@DisplayName("メールアドレス・パスワード共に正しく入力された時、家計簿一覧画面に遷移する")
	void testSuccess() throws Exception {
		
		// 準備
		// User情報セット(メールアドレス：○ パスワード：○)
		loginForm.setMailAddress("test@gmail.com");
		loginForm.setPassword("testtest");
		
		// Session情報セット
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute("user", user);
		
		// テスト
		when(loginService.login(anyString(), anyString())).thenReturn(user);
		
		// 検証
		MvcResult mvcResult = mockMvc.perform(post("/user/login")
				.flashAttr("loginForm", loginForm)
				.session(mockHttpSession))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/kakeibo/list"))
		.andExpect(model().hasNoErrors())
		.andReturn();
		// Sessionの格納情報を検証
		HttpSession httpSession = mvcResult.getRequest().getSession();
		assertEquals(user, httpSession.getAttribute("user"));
	}
	
	// 異常系
	
	@Test
	@DisplayName("メールアドレスが一致しない時、エラーメッセージが表示される")
	void testMailAddressNotMatch() throws Exception {
		
		// 準備
		// User情報セット(メールアドレス：不一致 パスワード：○)
		loginForm.setMailAddress("aaa@gmail.com");
		loginForm.setPassword("testtest");
		
		// テスト
		when(loginService.login(anyString(), anyString())).thenReturn(null);
		
		// 検証
		MvcResult mvcResult = mockMvc.perform(post("/user/login").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk())
				.andExpect(view().name("user/login"))
				.andReturn();
		// Modelの格納情報を検証
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals("メールアドレスまたはパスワードが不正です", modelAndView.getModel().get("errorMessage"));
	}
	
	@Test
	@DisplayName("パスワードが一致しない時、エラーメッセージが表示される")
	void testPasswordNotMatch() throws Exception {
		
		// 準備
		// User情報セット(メールアドレス：○ パスワード：不一致)
		loginForm.setMailAddress("test@gmail.com");
		loginForm.setPassword("testtesttest");
		
		// テスト
		when(loginService.login(anyString(), anyString())).thenReturn(null);
		
		// 検証
		MvcResult mvcResult = mockMvc.perform(post("/user/login").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk())
				.andExpect(view().name("user/login"))
				.andReturn();
		// Modelの格納情報を検証
		ModelAndView modelAndView = mvcResult.getModelAndView();
		assertEquals("メールアドレスまたはパスワードが不正です", modelAndView.getModel().get("errorMessage"));
	}
	
	@Test
	@DisplayName("メールアドレスがnullの時、ログイン画面に戻る")
	void testMailAddressIsNull() throws Exception {
		
		// 準備
		// User情報セット(メールアドレス：null)
		loginForm.setMailAddress(null);
		
		// 検証
		mockMvc.perform(post("/user/login")
				.flashAttr("loginForm", loginForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/login"));
	}

	@Test
	@DisplayName("メールアドレスの形式が正しくない時、ログイン画面に戻る")
	void testMailAddressIsDifferentFormat() throws Exception {
		
		// 準備
		// User情報セット(メールアドレス：× パスワード：○)
		loginForm.setMailAddress("testAddress");
		loginForm.setPassword("testtest");
		
		// 検証
		mockMvc.perform(post("/user/login")
				.flashAttr("loginForm", loginForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/login"));
	}
	
	@Test
	@DisplayName("パスワードがnullの時、ログイン画面に戻る")
	void testPasswordIsNull() throws Exception {
		
		// 準備
		// User情報セット(パスワード：null)
		loginForm.setPassword(null);
		
		// 検証
		mockMvc.perform(post("/user/login")
				.flashAttr("loginForm", loginForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("user/login"));
	}

}
