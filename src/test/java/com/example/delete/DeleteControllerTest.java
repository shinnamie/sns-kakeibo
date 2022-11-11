package com.example.delete;

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

import com.example.domain.kakeibo.Kakeibo;
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
	void deleteTest() throws Exception{
		// テスト準備
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setId(1);
		
		//正常系
		kakeiboService.deleteKakeibo(kakeibo.getId());
		mockMvc.perform(post("/kakeibo/delete")
		.param("id" , "1"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/kakeibo/list"));
	}
	
	
	@Test
	@DisplayName("家計簿削除のテスト（異常）")
	void deleteTest2() throws Exception{
		// テスト準備
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setId(1);
		
		//異常系
		kakeiboService.deleteKakeibo(kakeibo.getId());
		mockMvc.perform(post("/kakeibo/delete")
		.param("id" , "null"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/kakeibo/list"));
	}
}
