package com.example.controller.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
import com.example.form.board.DeleteBoardForm;
import com.example.form.board.NewBoardForm;
import com.example.service.board.BoardService;
import com.example.service.post.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	HttpSession session;

	@Autowired
	private BoardService boardService;

	@Autowired
	private PostService postService;

	@ModelAttribute
	private NewBoardForm newBoardForm() {
		return new NewBoardForm();
	}

	/*
	 * 掲示板リストを取得 存在しない：messageを表示 存在する：board/list.htmlに遷移
	 * 
	 */
	@GetMapping("/")
	public String getBoardList(Model model) {

		List<Board> boardList = boardService.selectBoardList();

		if (boardList.size() == 0) {
			model.addAttribute("message", "まだ掲示板のリストが存在しません");
			return "board/list";
		}

		model.addAttribute("boardList", boardList);
		return "board/list";
	}

	@GetMapping("/{boardId}")
	public String getPostList(@ModelAttribute @PathVariable("boardId") Long boardId, Model model) {
		// 該当掲示板の投稿リストを取得
		List<Post> postList = postService.selectPostList(boardId);
		if (postList.size() == 0) {
			model.addAttribute("message", "まだ投稿がありません");
			return "board/topic";
		}

		model.addAttribute("postList", postList);
		return "board/topic";
	}

	/**
	 * board新規投稿
	 *
	 * @param NewBoardForm
	 * @return 作成した掲示板のurl
	 */
	@PostMapping("/newBoard")
	public String getNewBoard(@Validated NewBoardForm newBoardForm, Model model, BindingResult result,
			RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 入力値チェック
		if (result.hasErrors()) {
			log.info("入力値エラー: {}", newBoardForm);
			model.addAttribute("errorMessage", "掲示板名は必ず入力してください");
			return "board/topic";
		}

		// 作成される掲示板情報をBoardオブジェクトに詰める
		Board newBoard = new Board();
		newBoard.setName(newBoardForm.getBoardName());
		newBoard.setDescription(newBoardForm.getDescription());

		User boardUser = new User();
		boardUser.setId(newBoardForm.getUserId());
		newBoard.setUser(boardUser);

		// 作成する
		boardService.saveBoard(newBoard);

		// 投稿完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "掲示板の作成が正常に完了しました");

		return "redirect:/board/";
	}

	/**
	 * board削除
	 *
	 * @param deleteBoardForm
	 * @return 掲示板一覧
	 */
	@PostMapping("/deleteBoard")
	public String deleteBoard(@Validated DeleteBoardForm deleteBoardForm, Model model, BindingResult result,RedirectAttributes redirectAttributes) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// 入力値チェック
		if (result.hasErrors()) {
			log.info("入力値エラー: {}", deleteBoardForm);
			model.addAttribute("errorMessage", "正常に掲示板が削除されませんでした");
			return "board/topic";
		}

		// 削除される掲示板情報をBoardオブジェクトに詰める
		Board deleteBoard = new Board();

		deleteBoard.setId(deleteBoardForm.getBoardId());

		User boardUser = new User();
		boardUser.setId(deleteBoardForm.getUserId());
		deleteBoard.setUser(boardUser);

		// 削除する
		boardService.deleteBoard(deleteBoard);

		// 削除完了メッセージを表示する
		redirectAttributes.addFlashAttribute("successMessage", "選択した掲示板が削除されました");

		return "redirect:/board/";
	}

}
