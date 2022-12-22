package com.example.controller.follow;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.User;
import com.example.service.follow.FollowService;

@Controller
@RequestMapping("/follow")	
public class FollowController {

	@Autowired
	HttpSession session;
	
	@Autowired
	private FollowService followService;
	
	
	@GetMapping("/following")
	public String getFollowingList(Model model) {
		// ログインチェックを追加
				User user = (User) session.getAttribute("user");
				if (user == null) {
					return "redirect:/user/login";
				}
		// フォローリストを取得
		List<User> followingList = followService.selectFollowingList(user.getId());
		if (followingList.size() == 0) {
			model.addAttribute("message", "誰もフォローしていません");
			return "follow/following";
		}

		model.addAttribute("followingList", followingList);
		return "follow/following";
	}
	
	@GetMapping("/followed")
	public String getFollowedList(Model model) {
		// ログインチェックを追加
				User user = (User) session.getAttribute("user");
				if (user == null) {
					return "redirect:/user/login";
				}
		// フォロワーリストを取得
		List<User> followedList = followService.selectFollowedList(user.getId());
		if (followedList.size() == 0) {
			model.addAttribute("message", "誰にもフォローされていません");
			return "follow/followed";
		}

		model.addAttribute("followedList", followedList);
		return "follow/followed";
	}
	
}
