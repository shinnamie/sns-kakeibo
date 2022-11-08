package com.example.form.kakeibo;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Settlement;

import lombok.Data;

@Data
public class AddKakeiboForm {

	/** 家計簿Id */
	private long id;
	/** ユーザーId */
	private Integer userId;
	/** 決済日付 */
	@NotNull(message = "日付を指定してください")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paymentDate;
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

}
