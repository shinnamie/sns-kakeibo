package com.example.domain.kakeibo;

public class ExpenseItem {

	/** 費目Id */
	private Integer id;
	/** 費目名 */
	private String expenseItemName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpenseItemName() {
		return expenseItemName;
	}

	public void setExpenseItemName(String expenseItemName) {
		this.expenseItemName = expenseItemName;
	}

	@Override
	public String toString() {
		return "ExpenseItem [id=" + id + ", expenseItemName=" + expenseItemName + "]";
	}

}
