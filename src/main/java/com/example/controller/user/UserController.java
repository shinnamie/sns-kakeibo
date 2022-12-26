package com.example.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.User;
import com.example.service.follow.FollowService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private FollowService followService;

	@GetMapping("/{userId}")
	public String index(@ModelAttribute @PathVariable("userId") Long id , Model model) {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
	User myPage = followService.selectMyPage(id);
	model.addAttribute("myPage" , myPage);
		return "user/myPage";
	}
		
	//フォロー一覧を取得
	@GetMapping("/{userId}/following")
	public String getFollowingList(@PathVariable("userId") Long id , Model model) {
		// ログインチェックを追加
				User user = (User) session.getAttribute("user");
				if (user == null) {
					return "redirect:/user/login";
				}
		// フォローリストを取得
		List<User> followingList = followService.selectFollowingList(id);
		if (followingList.size() == 0) {
			model.addAttribute("message", "誰もフォローしていません");
			return "follow/following";
		}

		model.addAttribute("followingList", followingList);
		return "follow/following";
	}
	
	//フォロワー一覧を取得
	@GetMapping("/{userId}/followed")
	public String getFollowedList(@PathVariable("userId") Long id , Model model) {
		// ログインチェックを追加
				User user = (User) session.getAttribute("user");
				if (user == null) {
					return "redirect:/user/login";
				}
		// フォローリストを取得
		List<User> followedList = followService.selectFollowedList(id);
		if (followedList.size() == 0) {
			model.addAttribute("message", "フォローされているユーザーはいません");
			return "follow/followed";
		}

		model.addAttribute("followedList", followedList);
		return "follow/followed";
	}
}
