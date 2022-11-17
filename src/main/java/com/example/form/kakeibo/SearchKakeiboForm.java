package com.example.form.kakeibo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SearchKakeiboForm {

	/** 年 */
	@NotBlank
	private String year;
	
	/** 月 */
	private String month;
}
