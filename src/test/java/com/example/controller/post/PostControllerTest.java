package com.example.controller.post;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
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

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.form.post.NewPostForm;
import com.example.domain.user.User;
import com.example.service.post.impl.PostServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

	static final MockHttpSession mockHttpSession = new MockHttpSession();
	static final User user = new User();
	static final Board board = new Board();

	NewPostForm newPostForm = new NewPostForm();
	Post newPost = new Post();
  
	static final Long postId = 1L;
	static final Long boardId = 1L;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PostServiceImpl service;
	PostServiceImpl postService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// ユーザー情報の準備
		user.setId(1L);
		user.setMailAddress("test@gmail.com");
		user.setPassword("validPassword");
		// 掲示板情報の準備
		board.setId(1L);
		// Session情報セット
		mockHttpSession.setAttribute("user", user);
	}

	@BeforeEach
	void setUp() throws Exception {
		// 有効なフォーム情報を準備
		newPostForm.setUserId(user.getId());
		newPostForm.setBoardId(board.getId());
		newPostForm.setContents("テストデータ");
	}

	@Test
	@DisplayName("投稿の新規登録画面に遷移することを期待する")
	void showNewPostPageIsSuccess() throws Exception {
		// 準備
		var emptyForm = new NewPostForm();
		// 実行&検証
		var result = mockMvc.perform(get("/board/" + board.getId() + "/newPost")
				.flashAttr("newPostForm", emptyForm)
				.session(mockHttpSession))
				.andExpect(view().name("newPost"))
				.andExpect(status().isOk())
				.andReturn();
		var model = result.getModelAndView();
		// 空のフォームと掲示板IDがModelに格納されていることを確認
		assertEquals(emptyForm, model.getModel().get("newPostForm"));
		assertEquals(board.getId(), model.getModel().get("boardId"));
	}

	@Test
	@DisplayName("フォームの値が正しいとき、新規投稿が成功し投稿一覧にリダイレクトする")
	void whenValidInformation_savePostIsSuccess() throws Exception {
		// 準備
		when(service.savePost(newPost)).thenReturn(true);
		// 実行&検証
		mockMvc.perform(post("/newPost")
				.flashAttr("newPostForm", newPostForm)
				.session(mockHttpSession)
				)
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/board/" + board.getId()))
		.andExpect(status().isFound())
		.andExpect(content().string(containsString("新規投稿に成功しました")));
	}

	@Test
	@DisplayName("ユーザIDがNULLなとき、新規投稿が失敗し新規投稿画面に遷移する")
	void whenUserIdIsNull_savePostIsFailed() throws Exception {
		// 準備
		newPostForm.setUserId(null);
		// 実行&検証
		mockMvc.perform(post("/newPost")
				.flashAttr("newPostForm", newPostForm)
				.session(mockHttpSession))
				.andExpect(model().hasErrors())
				.andExpect(view().name("newPost"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("新規投稿に失敗しました")));
	}

	@Test
	@DisplayName("掲示板IDがNULLなとき、新規投稿が失敗し新規投稿画面に遷移する")
	void whenBoardIdIsNull_savePostIsFailed() throws Exception {
		// 準備
		newPostForm.setBoardId(null);
		// 実行&検証
		mockMvc.perform(post("/newPost")
				.flashAttr("newPostForm", newPostForm)
				.session(mockHttpSession))
				.andExpect(model().hasErrors())
				.andExpect(view().name("newPost"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("新規投稿に失敗しました")));
	}

	@Test
	@DisplayName("内容(コンテンツ)がNULLなとき、新規投稿が失敗し新規投稿画面に遷移する")
	void whenContentsIsNull_savePostIsFailed() throws Exception {
		// 準備
		newPostForm.setContents(null);
		// 実行&検証
		mockMvc.perform(post("/newPost")
				.flashAttr("newPostForm", newPostForm)
				.session(mockHttpSession))
				.andExpect(model().hasErrors())
				.andExpect(view().name("newPost"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("新規投稿に失敗しました")));
	}

	@Test
	@DisplayName("投稿の削除に成功したとき、成功メッセージを表示する")
	void whenDeletePostIsSuccess_showSuccessMessage() throws Exception {
		// 準備
		when(postService.deletePost(postId)).thenReturn(true);
		// 実行&検証
		mockMvc.perform(post("/deletePost")
				.param("postId", "1L")
				.param("boardId", "1L")
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
				.param("postId", "1L")
				.param("boardId", "1L")
				.session(mockHttpSession)
				)
		.andExpect(redirectedUrl("/board/" + boardId))
		.andExpect(status().isFound())
		.andExpect(content().string(contains("投稿の削除に失敗しました")));
	}

}
