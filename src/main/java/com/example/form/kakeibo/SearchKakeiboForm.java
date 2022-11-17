package com.example.form.kakeibo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SearchKakeiboForm {

	/** 年 */
	@NotBlank(message = "年の指定は必須です")
	private String year;
	
	/** 月 */
	private String month;
}
