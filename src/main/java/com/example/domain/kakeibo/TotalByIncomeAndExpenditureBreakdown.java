package com.example.domain.kakeibo;

public class TotalByIncomeAndExpenditureBreakdown {
	
	/** 費目Id */
	private Integer expenseItemId;
	/** 費目ごとの合計金額 */
	private Integer totalByExpenseItem;
	/** 費目ごとの支出額割合 */
	private double incomeAndExpenditureBreakedownRate;
	/** 家計簿 */
	private Kakeibo kakeibo;
	/** 費目 */
	private ExpenseItem expenseItem;

	public Integer getExpenseItemId() {
		return expenseItemId;
	}

	public void setExpenseItemId(Integer expenseItemId) {
		this.expenseItemId = expenseItemId;
	}

	public Integer getTotalByExpenseItem() {
		return totalByExpenseItem;
	}

	public void setTotalByExpenseItem(Integer totalByExpenseItem) {
		this.totalByExpenseItem = totalByExpenseItem;
	}

	public double getIncomeAndExpenditureBreakedownRate() {
		return incomeAndExpenditureBreakedownRate;
	}

	public void setIncomeAndExpenditureBreakedownRate(double incomeAndExpenditureBreakedownRate) {
		this.incomeAndExpenditureBreakedownRate = incomeAndExpenditureBreakedownRate;
	}

	public Kakeibo getKakeibo() {
		return kakeibo;
	}

	public void setKakeibo(Kakeibo kakeibo) {
		this.kakeibo = kakeibo;
	}

	public ExpenseItem getExpenseItem() {
		return expenseItem;
	}

	public void setExpenseItem(ExpenseItem expenseItem) {
		this.expenseItem = expenseItem;
	}

	@Override
	public String toString() {
		return "TotalByIncomeAndExpenditureBreakdown [expenseItemId=" + expenseItemId + ", totalByExpenseItem="
				+ totalByExpenseItem + ", incomeAndExpenditureBreakedownRate=" + incomeAndExpenditureBreakedownRate
				+ ", kakeibo=" + kakeibo + ", expenseItem=" + expenseItem + "]";
	}
}
