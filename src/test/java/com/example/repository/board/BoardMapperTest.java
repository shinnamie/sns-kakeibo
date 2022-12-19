package com.example.repository.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.board.Board;
import com.example.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class BoardMapperTest {
	
	@Autowired
	BoardMapper mapper;
	
	Board board1 = new Board();
	Board board2 = new Board();
	Board board3 = new Board();
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
		
		board2.setId(2L);
		board2.setName("あなたの収入を見せて");
		board2.setDescription("自分の収入を見せる掲示板");
		board2.setUser(user);
		
		board3.setId(3L);
		board3.setName("あなたの支出を見せて");
		board3.setDescription("自分の支出を見せる掲示板");
		board3.setUser(user);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("期待する掲示板が返されているか")
	void testFindPostList() {
		
		List<Board> mapperBoardList = mapper.findBoardList();
						
		//Listのsizeを比較する
		assertEquals(3, mapperBoardList.size());
		
	}
	
	@Test
	@DisplayName("掲示板が降順で表示されているか")
	void testOrderBy() {
		
		List<Board> mapperBoardList = mapper.findBoardList();
				
		//期待する掲示板IDとmapperから返された掲示板一覧のIDを比較する
		assertEquals(board3.getId(), mapperBoardList.get(0).getId());
		assertEquals(board2.getId(), mapperBoardList.get(1).getId());
		assertEquals(board1.getId(), mapperBoardList.get(2).getId());
	}
}
