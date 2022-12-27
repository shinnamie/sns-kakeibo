package com.example.domain.kakeibo;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CsvKakeibo {

	/** 決済日付 */
	private LocalDate paymentDate;
	/** 費目名 */
	private String expenseItemName;
	/** 支出金額 */
	private Integer expenditureAmount;
	/** 収入金額 */
	private Integer incomeAmount;
	/** 決済方法 */
	private String settlementName;
	/** 利用店舗 */
	private String usedStore;
	/** 備考 */
	private String remarks;
	/** 登録日 */
	private Timestamp insertAt;

}
