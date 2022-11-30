package com.example.controller.user;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import com.example.form.user.SignUpForm;
import com.example.service.user.SignUpService;

@Controller
@RequestMapping("/user")
public class SignUpController {

	@Autowired
	private SignUpService signUpService;
	
	@ModelAttribute
	private SignUpForm signUpform() {
		return new SignUpForm();
	}

	/**
	 * 新規ユーザー登録画面に遷移(user/signUp.html)
	 * 
	 * @return
	 */
	@GetMapping("/signup")
	public String toSignUp() {
			
		return "user/signup";
	}
	
	/**
	 * 新規ユーザー登録処理
	 * 成功時：ログイン画面表示(user/login.html)
	 * 失敗時：新規ユーザー登録画面に戻る(エラーメッセージ表示)
	 * 
	 * @param signUpForm
	 * @param model
	 * @return
	 */
	@PostMapping("/signUp")
	public String getSignUp(@ModelAttribute @Validated SignUpForm signUpForm , BindingResult result , Model model) {
		// 入力値チェック
		if (result.hasErrors()) {
			return "user/signUp";
		}
		
		User user = new User();
		
		// 生年月日を変換・セット(LocalDate型)
		LocalDate dateOfBirth = signUpForm.getDateOfBirth();
		user.setDateOfBirth(dateOfBirth);

		// フォームの値をドメインにコピー
		BeanUtils.copyProperties(signUpForm , user);
		
		// 性別を変換してセット
		Integer gender = Integer.parseInt(signUpForm.getGender());
		user.setGender(gender);
		
		
	// 新規ユーザー登録 (登録失敗した場合は登録画面に戻る)
		if (!signUpService.signUp(user)) {
			model.addAttribute("errorMessage", "登録に失敗しました");
			return "user/signUp";
				}
		
		return "user/login";
	}
}
