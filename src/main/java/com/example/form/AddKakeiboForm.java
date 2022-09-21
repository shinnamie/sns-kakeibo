package com.example.form;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.domain.ExpenseItem;
import com.example.domain.Settlement;

public class AddKakeiboForm {

	/** 家計簿Id */
	private long id;
	/** ユーザーId */
	private Integer userId;
	/** 決済日付 */
	@NotNull(message = "日付を指定してください")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate settlementDate;
	/** 費目Id */
	@NotNull(message = "費目を選択してください")
	private Integer expenseItemId;
	/** 支出金額 */
	@Range(min = 0, max = 99999999, message = "数値は整数で8桁(0~99999999)で入力してください")
	@Digits(integer = 8, fraction = 0, message = "小数点以下は入力できません")
	private String expenditureAmount;
	/** 収入金額 */
	@Range(min = 0, max = 99999999, message = "数値は整数で8桁(0~99999999)で入力してください")
	@Digits(integer = 8, fraction = 0, message = "小数点以下は入力できません")
	private String incomeAmount;
	/** 決済Id */
	private Integer settlementId;
	/** 利用店舗 */
	private String usedStore;
	/** 備考 */
	private String remarks;
	/** 登録日 */
	private Timestamp insertAt;
	/** 更新日 */
	private Timestamp updateAt;
	/** 費目 */
	private ExpenseItem expenseItem;
	/** 決済 */
	private Settlement settlement;

	public AddKakeiboForm() {
	}

	/**
	 * デフォルトで支出金額と収入金額をセットするコンストラクタを用意
	 * 
	 * @param expenditureAmount
	 * @param incomeAmount
	 */
	public AddKakeiboForm(String expenditureAmount, String incomeAmount) {
		this.expenditureAmount = expenditureAmount;
		this.incomeAmount = incomeAmount;
	}

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

	public String getExpenditureAmount() {
		return expenditureAmount;
	}

	public void setExpenditureAmount(String expenditureAmount) {
		this.expenditureAmount = expenditureAmount;
	}

	public String getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(String incomeAmount) {
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

	public Timestamp getInsertAt() {
		return insertAt;
	}

	public void setInsertAt(Timestamp insertAt) {
		this.insertAt = insertAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public ExpenseItem getExpenseItem() {
		return expenseItem;
	}

	public void setExpenseItem(ExpenseItem expenseItem) {
		this.expenseItem = expenseItem;
	}

	public Settlement getSettlement() {
		return settlement;
	}

	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	@Override
	public String toString() {
		return "AddKakeiboForm [id=" + id + ", userId=" + userId + ", settlementDate=" + settlementDate
				+ ", expenseItemId=" + expenseItemId + ", expenditureAmount=" + expenditureAmount + ", incomeAmount="
				+ incomeAmount + ", settlementId=" + settlementId + ", usedStore=" + usedStore + ", remarks=" + remarks
				+ ", insertAt=" + insertAt + ", updateAt=" + updateAt + ", expenseItem=" + expenseItem + ", settlement="
				+ settlement + "]";
	}



}
