package com.example.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.Kakeibo;
import com.example.form.AddKakeiboForm;
import com.example.service.KakeiboService;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@Autowired
	KakeiboService kakeiboService;

	@ModelAttribute
	private AddKakeiboForm addKakeiboForm() {
		return new AddKakeiboForm();
	}

	/**
	 * 家計簿一覧画面を表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(Model model) {
		List<Kakeibo> kakeiboList = kakeiboService.kakeiboList();
		model.addAttribute("kakeiboList", kakeiboList);
		return "kakeibo/kakeiboList";
	}
	
	/**
	 * 家計簿登録画面を表示
	 * 
	 * @return
	 */
	@GetMapping(value = "/addKakeibo")
	public String addKakeibo() {
		return "kakeibo/add";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(AddKakeiboForm addKakeiboForm) {
		Kakeibo kakeibo = new Kakeibo();
		// 現在の登録日時の取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// 決済日時を変換・セット
		LocalDate settlementDate = addKakeiboForm.getSettlementDate().toLocalDate();
		kakeibo.setSettlementDate(settlementDate);

		// フォームの値をドメインにコピー
		BeanUtils.copyProperties(addKakeiboForm, kakeibo);

		// 登録日時と(最終)更新日時を手動でセット
		kakeibo.setInsertDate(timestamp);
		kakeibo.setUpdateDate(timestamp);

		// 新規登録処理
		kakeiboService.save(kakeibo);

		return "redirect:/kakeibo/list";
	}
}
