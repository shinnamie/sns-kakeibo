package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.kakeibo.Kakeibo;

@SpringBootTest
@Transactional
class KakeiboServiceSaveKakeiboTest {

	@Autowired
	KakeiboService service;

	// 家計簿登録 (正常系)

	@Test
	@DisplayName("正しい値を渡すと家計簿の登録が成功する")
	void whenValidInformation_expectToInsertKakeibo() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(0);
		kakeibo.setUsedStore("コンビニ");
		kakeibo.setRemarks("お醤油");

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がtrueになることを期待する)
		assertTrue(actual);
	}

	/** 家計簿登録 (異常系) */

	@Test
	@DisplayName("ユーザIDがnullのとき、戻り値としてnullを返す")
	void whenUserIdDoesNotExist_throwException() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(null);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(0);

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

	@Test
	@DisplayName("支払い日がnullのとき、戻り値としてnullを返す")
	void whenPaymentDateDoesNotExist_throwException() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		kakeibo.setPaymentDate(null);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(0);

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

	@Test
	@DisplayName("費目IDがnullのとき、戻り値としてnullを返す")
	void whenExpenseItemIdDoesNotExist_throwException() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(null);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(0);

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

	@Test
	@DisplayName("支出金額がnullのとき、戻り値としてnullを返す")
	void whenExpenditureAmountDoesNotExist_throwException() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(null);
		kakeibo.setIncomeAmount(0);

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

	@Test
	@DisplayName("収入金額がnullのとき、戻り値としてnullを返す")
	void whenIncomeAmountDoesNotExist_throwException() {
		// 準備 (Kakeiboインスタンスに値をセット)
		Kakeibo kakeibo = new Kakeibo();
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(null);

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

}
