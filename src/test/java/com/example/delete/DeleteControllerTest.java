package com.example.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.example.service.kakeibo.KakeiboService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteControllerTest {

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


	@Test
	@DisplayName("家計簿削除のテスト（正常）")
	void deleteTrueTest() throws Exception{
		
		when(kakeiboService.deleteKakeibo(anyLong())).thenReturn(true);
		
		//正常系
		mockMvc.perform(post("/kakeibo/delete")
		.param("id" , "1"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/kakeibo/list"));
	}
	
	
	@Test
	@DisplayName("家計簿削除のテスト（異常）")
	void deleteFalseTest() throws Exception{
		
		when(kakeiboService.deleteKakeibo(anyLong())).thenReturn(false);
		
//		//異常系
		MvcResult mvcResult = mockMvc.perform(post("/kakeibo/delete")
		.param("id" , "1"))
		.andExpect(status().isOk())
		.andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		assertEquals("削除に失敗しました" , mav.getModel().get("errorMessage"));
		
	}
}
