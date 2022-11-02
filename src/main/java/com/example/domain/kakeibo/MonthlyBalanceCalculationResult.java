package com.example.domain.kakeibo;

public class MonthlyBalanceCalculationResult {

	/** 収入合計 */
	private Integer totalIncomeAmount;
	/** 支出合計 */
	private Integer totalExpenditureAmount;
	/** 収支計算結果 */
	private Integer balanceCalculationResult;
	public Integer getTotalIncomeAmount() {
		return totalIncomeAmount;
	}
	public void setTotalIncomeAmount(Integer totalIncomeAmount) {
		this.totalIncomeAmount = totalIncomeAmount;
	}
	public Integer getTotalExpenditureAmount() {
		return totalExpenditureAmount;
	}
	public void setTotalExpenditureAmount(Integer totalExpenditureAmount) {
		this.totalExpenditureAmount = totalExpenditureAmount;
	}
	public Integer getBalanceCalculationResult() {
		return balanceCalculationResult;
	}
	public void setBalanceCalculationResult(Integer balanceCalculationResult) {
		this.balanceCalculationResult = balanceCalculationResult;
	}
	@Override
	public String toString() {
		return "MonthlyBalanceCalculationResult [totalIncomeAmount=" + totalIncomeAmount + ", totalExpenditureAmount="
				+ totalExpenditureAmount + ", balanceCalculationResult=" + balanceCalculationResult + "]";
	}
}
