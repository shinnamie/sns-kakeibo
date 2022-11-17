package com.example.form.kakeibo;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.domain.kakeibo.ExpenseItem;
import com.example.domain.kakeibo.Settlement;

import lombok.Data;

@Data
public class EditKakeiboForm {

	/** 家計簿Id */
	private long id;
	/** ユーザーId */
	private Integer userId;
	/** 決済日付 */
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate paymentDate;
	/** 費目Id */
	@NotNull
	private Integer expenseItemId;
	/** 支出金額 */
	@Max(value = 99999999)
	@Min(value = 0)
	@NotNull
	private Integer expenditureAmount;
	/** 収入金額 */
	@Max(value = 99999999)
	@Min(value = 0)
	@NotNull
	private Integer incomeAmount;
	/** 決済Id */
	private Integer settlementId;
	/** 利用店舗 */
	private String usedStore;
	/** 備考 */
	private String remarks;
	/** 更新日 */
	private Timestamp updateAt;
	/** 費目 */
	private ExpenseItem expenseItem;
	/** 決済 */
	private Settlement settlement;

}
