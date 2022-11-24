package com.example.form.user;

import lombok.Data;

@Data
public class SignUpForm {

	private String mailAdress;
	private String password;
	private String name;
	private Integer age;
	private Integer gender;
	private String remarks;

}
