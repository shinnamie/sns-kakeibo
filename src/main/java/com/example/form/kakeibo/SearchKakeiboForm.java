package com.example.form.kakeibo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SearchKakeiboForm {

	/** 年 */
	@NotBlank
	@Max(value = 2050)
	@Min(value = 1950)
	private String year;
	
	/** 月 */
	private String month;
}
