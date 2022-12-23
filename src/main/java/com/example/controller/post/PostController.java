package com.example.controller.post;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.form.post.DeletePostForm;
import com.example.form.post.NewPostForm;
import com.example.service.post.PostService;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	HttpSession session;

	@Autowired
	PostService postService;

	@ModelAttribute
	private NewPostForm newPostForm() {
		return new NewPostForm();
	}

	@ModelAttribute
	private DeletePostForm deletePostForm() {
		return new DeletePostForm();
	}

	/**
	 * post新規投稿
	 *
	 * @param NewPostForm
	 * @return 投稿した掲示板のurl
	 */
	@PostMapping("/newPost")
	public String getNewPost(@Validated NewPostForm newPostForm, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 入力値チェック
		if (result.hasErrors()) {
			Long nowBoardId = newPostForm.getBoardId();
			model.addAttribute("errorMessage", "投稿が完了しませんでした");
			return "/board/" + nowBoardId;
		}

		// 投稿情報をPostオブジェクトに詰める
		Post newPost = new Post();

		User postUser = new User();
		postUser.setId(newPostForm.getUserId());
		newPost.setUser(postUser);

		Board nowBoard = new Board();
		Long nowBoardId = newPostForm.getBoardId();
		nowBoard.setId(nowBoardId);
		newPost.setBoard(nowBoard);

		newPost.setContents(newPostForm.getContents());

		// 投稿する
		postService.savePost(newPost);

		// 投稿完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "投稿が完了しました");

		return "redirect:/board/" + nowBoardId;
	}

	/**
	 * post削除
	 *
	 * @param deletePostForm
	 * @return 削除した投稿のある掲示板のurl
	 */
	@PostMapping("/deletePost")
	public String deletePost(@Validated DeletePostForm deletePostForm, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 入力値チェック
		if (result.hasErrors()) {
			Long nowBoardId = deletePostForm.getBoardId();
			model.addAttribute("errorMessage", "削除が完了しませんでした");
			return "/board/" + nowBoardId;
		}

		// 削除対象の投稿情報をPostオブジェクトに詰める
		Post deletePost = new Post();

		User postUser = new User();
		postUser.setId(deletePostForm.getUserId());
		deletePost.setUser(postUser);

		Board nowBoard = new Board();
		Long nowBoardId = deletePostForm.getBoardId();
		nowBoard.setId(nowBoardId);
		deletePost.setBoard(nowBoard);

		deletePost.setId(deletePostForm.getPostId());

		// 削除する
		postService.deletePost(deletePost);

		// 削除完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "削除が完了しました");

		return "redirect:/board/" + nowBoardId;
	}

}
