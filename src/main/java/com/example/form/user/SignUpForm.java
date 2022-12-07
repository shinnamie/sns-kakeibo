package com.example.form.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
	
	@NotNull
	private Integer gender;
	
	private String remarks;

}
