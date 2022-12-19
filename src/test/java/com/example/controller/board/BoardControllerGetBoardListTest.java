package com.example.controller.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.example.service.board.impl.BoardServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerGetBoardListTest {
	
	List<Board> boardList = new ArrayList<>();
	Board board = new Board();
	User user = new User();
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BoardServiceImpl boardServiceImpl;
	
	@BeforeEach
	void setUp() throws Exception {
		// テスト準備
		
		user.setId(1L);
		user.setMailAddress("aaa@gmail.com");
		user.setPassword("zzzzzzzz");
		user.setName("山田太郎");
		
		board.setId(1L);
		board.setName("節約しろや");
		board.setDescription("節約しないやつは許さない掲示板");
		board.setUser(user);
		boardList.add(board);
	}
	
	
	/** 表示テスト */
	
	// 正常系

	@Test
	@DisplayName("掲示板一覧を正常に表示する")
	void testShowBoardList() throws Exception {
		
		when(boardServiceImpl.selectBoardList()).thenReturn(boardList);
		
		// 検証&実行
		MvcResult result = mockMvc.perform(get("/board/"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/list"))
		.andReturn();
		
		ModelAndView mav = result.getModelAndView();
		assertEquals(boardList , mav.getModel().get("boardList"));
	}
	
	//異常系
	
	@Test
	@DisplayName("掲示板一覧が空の場合、メッセージを表示する")
	void testBoardListEmpty() throws Exception {
		
		List<Board> emptyBoardList = new ArrayList<>();
		
		when(boardServiceImpl.selectBoardList()).thenReturn(emptyBoardList);
		
		// 検証&実行
		MvcResult result = mockMvc.perform(get("/board/"))
		.andExpect(status().isOk())
		.andExpect(view().name("board/list"))
		.andReturn();
		
		ModelAndView mav = result.getModelAndView();
		assertEquals("まだ掲示板のリストが存在しません", mav.getModel().get("message"));
	}
	
	
}
