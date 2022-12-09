package com.example.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.post.Post;
import com.example.service.post.PostService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private PostService postService;

	@GetMapping("/{boardId}")
	public String getPostList(@PathVariable("boardId") Long boardId, Model model) {
		// 該当掲示板の投稿リストを取得
		List<Post> postList = postService.selectPostList(boardId);
		if (postList.size() == 0) {
			model.addAttribute("message", "まだ投稿がありません");
			return "board/topic";
		}

		model.addAttribute("postList", postList);
		return "board/topic";
	}
}
