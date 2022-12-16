package com.example.controller.post;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domain.user.User;
import com.example.service.post.impl.PostServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

	static final MockHttpSession mockHttpSession = new MockHttpSession();
	static final User user = new User();
	static final Long postId = 1L;
	static final Long boardId = 1L;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PostServiceImpl postService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// ユーザー情報の準備
		user.setId(1L);
		// Session情報セット
		mockHttpSession.setAttribute("user", user);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("投稿の削除に成功したとき、成功メッセージを表示する")
	void whenDeletePostIsSuccess_showSuccessMessage() throws Exception {
		// 準備
		when(postService.deletePost(postId)).thenReturn(true);
		// 実行&検証
		mockMvc.perform(post("/deletePost")
				.flashAttr("postId", "postId")
				.flashAttr("boardId", "boardId")
				.session(mockHttpSession)
				)
		.andExpect(redirectedUrl("/board/" + boardId))
		.andExpect(status().isFound())
		.andExpect(content().string(contains("投稿を削除しました")));
	}

	@Test
	@DisplayName("投稿の削除に失敗したとき、失敗メッセージを表示する")
	void whenDeletePostIsFailed_showFailMessage() throws Exception {
		// 準備
		when(postService.deletePost(postId)).thenReturn(false);
		// 実行&検証
		mockMvc.perform(post("/deletePost")
				.flashAttr("postId", "postId")
				.flashAttr("boardId", "boardId")
				.session(mockHttpSession)
				)
		.andExpect(redirectedUrl("/board/" + boardId))
		.andExpect(status().isFound())
		.andExpect(content().string(contains("投稿の削除に失敗しました")));
	}

}
