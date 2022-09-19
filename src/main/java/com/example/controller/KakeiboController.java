package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.Kakeibo;
import com.example.service.KakeiboService;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@Autowired
	KakeiboService kakeiboService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Kakeibo> kakeiboList = kakeiboService.kakeiboList();
		model.addAttribute("kakeiboList", kakeiboList);
		return "kakeibo/kakeiboList";
	}
}
