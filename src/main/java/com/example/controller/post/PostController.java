package com.example.controller.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.form.post.DeletePostForm;
import com.example.form.post.NewPostForm;
import com.example.service.post.impl.PostServiceImpl;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	HttpSession session;

	@Autowired
	PostServiceImpl postServiceImpl;

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
	public String getNewPost(NewPostForm newPostForm,Model model, RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 投稿情報をPostオブジェクトに詰める
		Post newPost = new Post();
		newPost.setUser(user);
		Board nowBoard = new Board();
		Long nowBoardId = newPostForm.getBoardId();
		nowBoard.setId(nowBoardId);
		newPost.setBoard(nowBoard);
		newPost.setContents(newPostForm.getContents());

		// 投稿する
		postServiceImpl.savePost(newPost);

		// 投稿完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "投稿が完了しました");

		return "redirect:/board/" + nowBoardId;
	}

	/**
	 * post削除
	 *
	 * @param NewPostForm
	 * @return 削除した投稿のある掲示板のurl
	 */
	@PostMapping("/deletePost")
	public String deletePost(DeletePostForm deletePostForm, Model model, RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 削除対象の投稿情報をPostオブジェクトに詰める
		Post targetDeletePost = new Post();
		Board nowBoard = new Board();
		Long nowBoardId = deletePostForm.getBoardId();
		nowBoard.setId(nowBoardId);
		targetDeletePost.setId(deletePostForm.getId());
		targetDeletePost.setBoard(nowBoard);

		// 削除する
		postServiceImpl.deletePost(targetDeletePost);

		// 削除完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "削除が完了しました");

		return "redirect:/board/" + nowBoardId;
	}

}
