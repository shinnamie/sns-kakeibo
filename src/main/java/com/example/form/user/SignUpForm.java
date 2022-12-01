package com.example.form.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpForm {

	@NotBlank
	@Email
	private String mailAddress;
	
	@NotBlank
	@Size(min=8, max=16)
	private String password;
	
	@NotBlank
	private String confirmPassword;
	private String name;
	private LocalDate dateOfBirth;
	private Integer gender;
	private String remarks;

}
