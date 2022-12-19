package com.example.service.post.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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

import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.repository.post.PostMapper;

@SpringBootTest
class PostServiceImplTest {

	@InjectMocks
	private PostServiceImpl serviceImpl;

	@Mock
	private PostMapper mapper;

	Post post1 = new Post();
	Post post2 = new Post();
	Post post3 = new Post();
	List<Post> postLists = new ArrayList<Post>();
	User user = new User();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		LocalDateTime insertDateTime4 = LocalDateTime.parse("2022-04-09T19:34:50.63");
		LocalDateTime insertDateTime5 = LocalDateTime.parse("2022-05-09T19:34:50.63");
		LocalDateTime insertDateTime6 = LocalDateTime.parse("2022-06-09T19:34:50.63");
		LocalDateTime updateDateTime = LocalDateTime.parse("2022-11-10T19:34:50.63");
		user.setId(1L);
		user.setMailAddress("aaa@aaa");
		user.setPassword("test1");

		post1.setId(4L);
		post1.setContents("ボーナスやった！！(4)");
		post1.setInsertAt(insertDateTime4);
		post1.setUpdateAt(updateDateTime);
		post1.setUser(user);

		post2.setId(5L);
		post2.setContents("ボーナスやった！！(5)");
		post2.setInsertAt(insertDateTime5);
		post2.setUpdateAt(updateDateTime);
		post2.setUser(user);

		post3.setId(6L);
		post3.setContents("ボーナスやった！！(6)");
		post3.setInsertAt(insertDateTime6);
		post3.setUpdateAt(updateDateTime);
		post3.setUser(user);

		postLists.add(post1);
		postLists.add(post2);
		postLists.add(post3);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("投稿一覧のリストを取得できているか")
	void testSelectPostList() {
		
		//期待するPost情報を取得
		doReturn(postLists).when(mapper).findPostList(anyLong());
		
		// serviceのMock作成
		List<Post> servicePostLists = serviceImpl.selectPostList(3L);
		
		assertEquals(postLists, servicePostLists);
	}

	@Test
	@DisplayName("新規投稿に成功したとき、戻り値でTrueを返すことを期待する")
	void whenSavePostIsSuccess_expectedToReturnTrue() throws Exception {
		// 準備
		when(mapper.savePost(post1)).thenReturn(true);
		// 実行
		var actual = serviceImpl.savePost(post1);
    // 検証
		assertTrue(actual);
  }
  
	@DisplayName("投稿の削除に成功したとき、戻り値としてTrueを返す")
	void whenDeletePostIsSuccess_returnTrue() throws Exception {
		// 準備
		when(mapper.deletePost(1L)).thenReturn(true);
		// 実行
		boolean actual = serviceImpl.deletePost(1L);
		// 検証
		assertTrue(actual);
	}

	@Test
	@DisplayName("新規投稿に失敗したとき、戻り値でFalseを返すことを期待する")
	void whenSavePostIsFailed_expectedToReturnFalse() throws Exception {
		// 準備
		when(mapper.savePost(post1)).thenThrow(Exception.class);
		// 実行
		var actual = serviceImpl.savePost(post1);
    // 検証
		assertFalse(actual);
   }
    
	@DisplayName("投稿の削除に失敗したとき、戻り値としてFalseを返す")
	void whenDeletePostIsFailed_returnFalse() throws Exception {
		// 準備
		when(mapper.deletePost(1L)).thenThrow(Exception.class);
		// 実行
		boolean actual = serviceImpl.deletePost(1L);
		// 検証
		assertFalse(actual);
	}

}
