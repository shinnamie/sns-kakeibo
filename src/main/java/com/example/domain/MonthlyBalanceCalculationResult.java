package com.example.domain;

public class MonthlyBalanceCalculationResult {

	/** 収入合計 */
	private Integer TotalIncomeAmount;
	/** 支出合計 */
	private Integer TotalExpenditureAmount;
	/** 収支計算結果 */
	private Integer BalanceCalculationResult;

	public Integer getTotalIncomeAmount() {
		return TotalIncomeAmount;
	}

	public void setTotalIncomeAmount(Integer totalIncomeAmount) {
		TotalIncomeAmount = totalIncomeAmount;
	}

	public Integer getTotalExpenditureAmount() {
		return TotalExpenditureAmount;
	}

	public void setTotalExpenditureAmount(Integer totalExpenditureAmount) {
		TotalExpenditureAmount = totalExpenditureAmount;
	}

	public Integer getBalanceCalculationResult() {
		return BalanceCalculationResult;
	}

	public void setBalanceCalculationResult(Integer balanceCalculationResult) {
		BalanceCalculationResult = balanceCalculationResult;
	}

	@Override
	public String toString() {
		return "BalanceCalculationResult [TotalIncomeAmount=" + TotalIncomeAmount + ", TotalExpenditureAmount="
				+ TotalExpenditureAmount + ", BalanceCalculationResult=" + BalanceCalculationResult + "]";
	}

}
