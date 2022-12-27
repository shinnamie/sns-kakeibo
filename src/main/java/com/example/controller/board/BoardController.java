package com.example.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.service.board.BoardService;
import com.example.service.like.LikeService;
import com.example.service.post.PostService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private PostService postService;

	@Autowired
	private LikeService likeService;

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
	public String getPostList(@PathVariable("boardId") Long boardId, Model model) {
		// 該当掲示板の投稿リストを取得
		List<Post> postList = postService.selectPostList(boardId);

		if (postList.size() == 0) {
			model.addAttribute("message", "まだ投稿がありません");
			return "board/topic";
		}

		// いいね数をPostListに追加する
		List<Integer> likeList = likeService.totallingLike(postList);
		Integer roopNumber = 0;
		for (Post post : postList) {
			post.setLikes(likeList.get(roopNumber));
			roopNumber += 1;
		}

		model.addAttribute("postList", postList);

		return "board/topic";
	}
}
