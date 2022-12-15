package com.example.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面に遷移(user/login.html)
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		Object message = model.getAttribute("successMessage");
		if(message != null) {
			model.addAttribute("successMesssage", String.valueOf(message));
		}
		
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
	public String postLogin(@Validated LoginForm loginForm, BindingResult result, Model model) {

		// 入力値チェック
		if (result.hasErrors()) {
			return getLogin(model);
		}

		User user = service.login(loginForm.getMailAddress(), loginForm.getPassword());

		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
			return "user/login";
		} else {
			session.setAttribute("user", user);
			return "redirect:/kakeibo/list";
		}
	}

	@PostMapping("/test/login")
	public String testLogin() {
		User user = service.login("aaa@aaa", "test1");
		session.setAttribute("user", user);
		return "redirect:/kakeibo/list";
	}

}
