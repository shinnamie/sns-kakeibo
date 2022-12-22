package com.example.controller.like;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.board.Board;
import com.example.domain.like.Like;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.form.like.LikeForm;
import com.example.service.like.LikeService;

@Controller
@RequestMapping("/like")
public class LikeContoroller {
	
	@Autowired
	HttpSession session;

	@Autowired
	public LikeService likeService;

	@ModelAttribute
	private LikeForm likeform() {
		return new LikeForm();
	}

	/**
	 * postにいいねをする
	 *
	 * @param LikeForm
	 * @return
	 */
	@PostMapping("/mark")
	public String markLike(LikeForm likeForm, BindingResult result, Model model) {

		// 入力値エラーの際は編集画面を表示する
		if (result.hasErrors()) {
			return "kakeibo/edit";
		}

		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// formをオブジェクトに詰める
		Like likePost = new Like();

		Post post = new Post();
		post.setId(likeForm.getPostId());
		likePost.setPost(post);
		
		// boardはいいねをしたときにそのboardにそのままとどまれるようにidを取得している
		Board board = new Board();
		Long nowBoardId = likeForm.getBoardId();
		board.setId(nowBoardId);
		likePost.setBoard(board);
		
		likePost.setUser(user);

		likeService.markLike(likePost);

		return "redirect:/board/" + nowBoardId;
	}
	
	/**
	 * postのいいねを取り消す
	 *
	 * @param LikeForm
	 * @return
	 */
	@PostMapping("/remove")
	public String removeLike(LikeForm likeForm, BindingResult result, Model model) {

		// 入力値エラーの際は編集画面を表示する
		if (result.hasErrors()) {
			return "kakeibo/edit";
		}

		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// formをオブジェクトに詰める
		Like removeLikePost = new Like();

		Post post = new Post();
		post.setId(likeForm.getPostId());
		removeLikePost.setPost(post);
		
		// boardはいいねをしたときにそのboardにそのままとどまれるようにidを取得している
		Board board = new Board();
		Long nowBoardId = likeForm.getBoardId();
		board.setId(nowBoardId);
		removeLikePost.setBoard(board);
		
		removeLikePost.setUser(user);

		likeService.removeLike(removeLikePost);

		return "redirect:/board/" + nowBoardId;
	}

}
