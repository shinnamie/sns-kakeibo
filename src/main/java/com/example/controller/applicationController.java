package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.domain.user.User;

@Controller
public class applicationController {

	@Autowired
	HttpSession session;

	/** Webアプリを開いた際にログイン画面を表示するよう */
	@GetMapping("/")
	public String index() {
		// ログインチェックを追加
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		return "redirect:/kakeibo/list";
	}

}
