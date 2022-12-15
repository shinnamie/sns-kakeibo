package com.example.domain.kakeibo;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.example.domain.user.User;

import lombok.Data;

@Data
public class Kakeibo {

	/** 家計簿Id */
	private Long id;
	/** ユーザーId */
	private Long userId;
	/** 決済日付 */
	private LocalDate paymentDate;
	/** 費目Id */
	private Long expenseItemId;
	/** 支出金額 */
	private Integer expenditureAmount;
	/** 収入金額 */
	private Integer incomeAmount;
	/** 決済Id */
	private Long settlementId;
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
	/** ユーザー */
	private User user;

}
