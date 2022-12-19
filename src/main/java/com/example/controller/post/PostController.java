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

import com.example.domain.board.Board;
import com.example.domain.post.Post;
import com.example.domain.user.User;
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

	@PostMapping("/newPost")
	public String getNewPost(HttpServletRequest request,Long boardId,NewPostForm newPostForm) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		
		// 投稿情報をPostオブジェクトに詰める
		Post newPost = new Post();
		newPost.setUser(user);
		Board nowBoard = new Board();
		Long nowBoardId = Long.parseLong(request.getParameter("boardId"));
		nowBoard.setId(nowBoardId);
		newPost.setBoard(nowBoard);
		newPost.setContents(newPostForm.getContents());
		
		// 投稿する
		postServiceImpl.savePost(newPost);

		return "board/topic";
	}

}
