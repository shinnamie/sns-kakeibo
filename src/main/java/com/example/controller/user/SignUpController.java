package com.example.controller.user;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.user.User;
import com.example.form.user.SignUpForm;
import com.example.service.user.SignUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public String getSignUp() {
		
		return "user/signUp";
	}

	/**
	 * 新規ユーザー登録処理
	 * 成功時：ログイン画面表示(user/login.html)
	 * 失敗時：新規ユーザー登録画面に戻る(エラーメッセージ表示)
	 *
	 * @param signUpForm
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/signUp")
	public String postSignUp(@Validated SignUpForm signUpForm , BindingResult result , Model model , RedirectAttributes redirectAttributes){
		// 入力値チェック
		if (result.hasErrors()) {
			log.info("入力値エラー: {}" , signUpForm);
			return "user/signUp";			
			//登録済みメールアドレスの重複チェック
		}else if(signUpService.findByEmail(signUpForm.getMailAddress()) != null) {
			log.info("登録済みのメールアドレス: mailAddress:{}" , signUpForm.getMailAddress());
			model.addAttribute("mailAddressErrorMessage", "入力したメールアドレスは既に登録されています");
			return "user/signUp";
			//パスワードの一致チェック
		}else if(!(signUpForm.getPassword().equals(signUpForm.getConfirmPassword()))){
			log.info("パスワード不一致: password:{} confirmPassword:{}" , signUpForm.getPassword() , signUpForm.getConfirmPassword());
			model.addAttribute("passwordErrorMessage", "確認用パスワードはパスワードと同じものを入力してください");
			return "user/signUp";	

		}			

		User user = new User();

		// フォームの値をドメインにコピー
		BeanUtils.copyProperties(signUpForm , user);



	// 新規ユーザー登録
		signUpService.signUp(user);
		redirectAttributes.addFlashAttribute("successMessage", "登録に成功しました");
		return "redirect:/user/login";
	}
}
