package com.example.form.kakeibo;

import javax.validation.constraints.Pattern;

public class AggregatedYearOrMonthForm {

	/** 集計年 */
	@Pattern(regexp = "^[0-9]{4}$", message = "集計年を半角数字(4桁)で指定してください")
	private String year;
	/** 集計月 */
	private String month;
	/** 費目Id */
	private Integer expenseItemId;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getExpenseItemId() {
		return expenseItemId;
	}

	public void setExpenseItemId(Integer expenseItemId) {
		this.expenseItemId = expenseItemId;
	}
	@Override
	public String toString() {
		return "AggregatedYearOrMonthForm [year=" + year + ", month=" + month + ", expenseItemId=" + expenseItemId
				+ "]";
	}


}
