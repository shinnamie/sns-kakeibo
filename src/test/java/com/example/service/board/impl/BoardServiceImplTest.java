package com.example.service.board.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.board.Board;
import com.example.domain.user.User;
import com.example.repository.board.BoardMapper;

@SpringBootTest
class BoardServiceImplTest {
	
	@InjectMocks
	private BoardServiceImpl boardServiceImpl;
	
	@Mock
	private BoardMapper mapper;
	
	Board board1 = new Board();
	Board board2 = new Board();
	List<Board> boardList = new ArrayList<Board>();
	User user = new User();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		user.setId(1L);
		user.setMailAddress("aaa@aaa");
		user.setPassword("test1");
		user.setName("山田太郎");
		
		board1.setId(1L);
		board1.setName("節約しろや");
		board1.setDescription("節約しないやつは許さない掲示板");
		board1.setUser(user);
		boardList.add(board1);
		
		board1.setId(2L);
		board1.setName("あなたの収入を見せて");
		board1.setDescription("自分の収入を見せる掲示板");
		board1.setUser(user);
		boardList.add(board2);
				
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("掲示板一覧のリストを取得できているか")
	void testSelectBoardList() {
		
		//期待するPost情報を取得
		doReturn(boardList).when(mapper).findBoardList();
		
		// serviceのMock作成
		List<Board> serviceBoardList = boardServiceImpl.selectBoardList();
		
		assertEquals(boardList, serviceBoardList);
	}

}
