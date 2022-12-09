package com.example.controller.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.service.post.PostService;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerGetPostListTest {
	
	List<Post> postList = new ArrayList<>();
	Post post = new Post();
	Board board = new Board();
	User user = new User();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PostService postService;
	
	@BeforeEach
	void setUp() throws Exception {
		// テスト準備
		
		user.setId(1L);
		user.setMailAddress("aaa@gmail.com");
		user.setPassword("zzzzzzzz");
		user.setName("山田太郎");
		
		board.setId(1L);
		
		post.setId(1L);
		post.setContents("本日の支出");
		post.setInsertAt(LocalDateTime.now());
		post.setUpdateAt(LocalDateTime.now());
		post.setUser(user);
		postList.add(post);
	}
	
	
	/** 表示テスト */

	@Test
	@DisplayName("投稿一覧画面に遷移")
	void testGetPostList() throws Exception {
		
		
		when(postService.selectPostList(anyLong())).thenReturn(postList);
		
		// 検証&実行
		mockMvc.perform(get("/board/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/topic"))
		.andReturn();
	}
	
	// 正常系

	@Test
	@DisplayName("投稿一覧を正常に表示する")
	void testShowPostList() throws Exception {
		
		when(postService.selectPostList(anyLong())).thenReturn(postList);
		
		// 検証&実行
		MvcResult result = mockMvc.perform(get("/board/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/topic"))
		.andReturn();
		
		ModelAndView mav = result.getModelAndView();
		assertEquals(mav.getModel().get("postList") , postList);
	}
	
	//異常系
	
	@Test
	@DisplayName("投稿一覧が空の場合、メッセージを表示する")
	void testIdNull() throws Exception {
		
		List<Post> emptyPostList = new ArrayList<>();
		
		when(postService.selectPostList(anyLong())).thenReturn(emptyPostList);
		
		// 検証&実行
		MvcResult result = mockMvc.perform(get("/board/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/topic"))
		.andReturn();
		
		ModelAndView mav = result.getModelAndView();
		assertEquals("まだ投稿がありません", mav.getModel().get("message"));
	}
	
	
}
