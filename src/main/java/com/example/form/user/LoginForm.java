package com.example.form.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

	@NotBlank
	@Email
	private String mailAdress;
	@NotBlank
	private String password;

}
