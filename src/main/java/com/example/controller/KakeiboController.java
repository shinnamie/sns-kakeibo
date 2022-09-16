package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "kakeiboList";
	}
}
