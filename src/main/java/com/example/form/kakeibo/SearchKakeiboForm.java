package com.example.form.kakeibo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class SearchKakeiboForm {

	/** 年 */
	@NotBlank
	@Range(min = 1950, max = 2050)
	private String year;
	
	/** 月 */
	private String month;
}
