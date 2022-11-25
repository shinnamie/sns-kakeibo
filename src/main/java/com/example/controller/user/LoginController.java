package com.example.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.User;
import com.example.form.user.LoginForm;
import com.example.service.user.LoginService;

@Controller
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private LoginService service;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ログイン画面に遷移(user/login.html)
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String getLogin() {
		
		return "user/login";
	}
	
	/**
	 * ログイン処理
	 * 成功時：家計簿一覧表示(kakeibo/list.html)
	 * 失敗時：ログイン画面に戻る(エラーメッセージ表示)
	 * 
	 * @param loginForm
	 * @param model
	 * @return
	 */
	@PostMapping("/login")
	public String postLogin(LoginForm loginForm, Model model) {
		
		User user = service.login(loginForm.getMailAdress(), loginForm.getPassword());
		
		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
			return "user/login";
		} else {
			session.setAttribute("user", user);
			return "redirect:/kakeibo/list";
		}
	}

}
