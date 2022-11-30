package com.example.form.user;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SignUpForm {

	private String mailAddress;
	private String password;
	private String confirmPassword;
	private String name;
	private LocalDate dateOfBirth;
	private String gender;
	private String remarks;

}
