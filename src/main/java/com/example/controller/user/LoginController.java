package com.example.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.service.user.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService service;

}
