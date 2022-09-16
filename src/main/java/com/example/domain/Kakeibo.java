package com.example.domain;

import java.time.LocalDate;

public class Kakeibo {

	/** 家計簿Id */
	private long id;
	/** ユーザーId */
	private Integer userId;
	/** 決済日付 */
	private LocalDate settlementDate;
	/** 費目Id */
	private Integer expenseItemId;
	/** 支出金額 */
	private Integer expenditureAmount;
	/** 収入金額 */
	private Integer incomeAmount;
	/** 決済Id */
	private Integer settlementId;
	/** 利用店舗 */
	private String usedStore;
	/** 備考 */
	private String remarks;
	/** 登録日 */
	private LocalDate insertDate;
	/** 更新日 */
	private LocalDate updateDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	public Integer getExpenseItemId() {
		return expenseItemId;
	}
	public void setExpenseItemId(Integer expenseItemId) {
		this.expenseItemId = expenseItemId;
	}

	public Integer getExpenditureAmount() {
		return expenditureAmount;
	}

	public void setExpenditureAmount(Integer expenditureAmount) {
		this.expenditureAmount = expenditureAmount;
	}
	public Integer getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(Integer incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public Integer getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
	}
	public String getUsedStore() {
		return usedStore;
	}
	public void setUsedStore(String usedStore) {
		this.usedStore = usedStore;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public LocalDate getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(LocalDate insertDate) {
		this.insertDate = insertDate;
	}
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "Kakeibo [id=" + id + ", userId=" + userId + ", settlementDate=" + settlementDate + ", expenseItemId="
				+ expenseItemId + ", expenditureAmount=" + expenditureAmount + ", incomeAmount=" + incomeAmount
				+ ", settlementId=" + settlementId + ", usedStore=" + usedStore + ", remarks=" + remarks
				+ ", insertDate=" + insertDate + ", updateDate=" + updateDate + "]";
	}


}
