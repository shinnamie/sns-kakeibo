package com.example.repository.post;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.post.Post;
import com.example.domain.user.User;

@SpringBootTest
class PostMapperTest {
	
	@Autowired
	PostMapper mapper;
	
	Post post1 = new Post();
	Post post2 = new Post();
	Post post3 = new Post();
	List<Post> expectedPostLists;
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
		LocalDateTime updateDateTime4 = LocalDateTime.parse("2022-04-10T19:34:50.63");
		LocalDateTime updateDateTime5 = LocalDateTime.parse("2022-05-10T19:34:50.63");
		LocalDateTime updateDateTime6 = LocalDateTime.parse("2022-06-10T19:34:50.63");
		user.setId(1L);
		user.setMailAddress("aaa@aaa");
		user.setPassword("test1");
		
		post1.setId(4L);
		post1.setContents("ボーナスやった！！(4)");
		post1.setInsertAt(insertDateTime4);
		post1.setUpdateAt(updateDateTime4);
		post1.setUser(user);
		
		post2.setId(5L);
		post2.setContents("ボーナスやった！！(5)");
		post2.setInsertAt(insertDateTime5);
		post2.setUpdateAt(updateDateTime5);
		post2.setUser(user);
		
		post3.setId(6L);
		post3.setContents("ボーナスやった！！(6)");
		post3.setInsertAt(insertDateTime6);
		post3.setUpdateAt(updateDateTime6);
		post3.setUser(user);
		
		expectedPostLists.add(post1);
		expectedPostLists.add(post2);
		expectedPostLists.add(post3);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("期待する投稿一覧のリストが返されているか")
	void testFindPostList() {
		
		List<Post> mapperPostList = mapper.findPostList(3L);
		
		//期待する投稿一覧リストの0,1番目から投稿IDを抽出する
		Long expectedPostId0 = expectedPostLists.get(0).getId();
		Long expectedPostId1 = expectedPostLists.get(1).getId();
		
		//mapperから返された投稿一覧リストの0,1番目から投稿IDを抽出する
		Long mapperPostId0 = mapperPostList.get(0).getId();
		Long mapperPostId1 = mapperPostList.get(1).getId();
		
		//投稿IDを比較する
		assertEquals(expectedPostId0, mapperPostId0);
		assertEquals(expectedPostId1, mapperPostId1);
		
	}
	
	@Test
	@DisplayName("存在しない掲示板番号(bordId)を渡した時、sizeが0のリストが返ってくるか")
	void testFindPostListSizeZero() {
		
		//存在しない掲示板番号(bordId)を引数に渡す
		List<Post> mapperPostList = mapper.findPostList(100L);
		
		//size()が0になっている場合正常にテストが通る
		assertEquals(0,mapperPostList.size());
		
	}
	
	@Test
	@DisplayName("投稿一覧のリストが投稿日順に並んで返されているか")
	void testFindPostListOrderByDate() {
		
		List<Post> mapperPostList = mapper.findPostList(3L);
		
		//期待する投稿一覧とmapperから返された投稿一覧の行番号が同じものを比較する
		assertEquals(expectedPostLists.get(0),mapperPostList.get(0));
		assertEquals(expectedPostLists.get(1),mapperPostList.get(2));
		assertEquals(expectedPostLists.get(2),mapperPostList.get(3));
	}

}
