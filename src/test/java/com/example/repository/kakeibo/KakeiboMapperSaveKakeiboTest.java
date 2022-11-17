package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.kakeibo.Kakeibo;

@SpringBootTest
@Transactional
class KakeiboMapperSaveKakeiboTest {

	@Autowired
	KakeiboMapper mapper;

	Kakeibo kakeibo = new Kakeibo();

	@BeforeEach
	void setUp() throws Exception {
		kakeibo.setUserId(1);
		LocalDate date = LocalDate.now();
		kakeibo.setPaymentDate(date);
		kakeibo.setExpenseItemId(1);
		kakeibo.setExpenditureAmount(10000);
		kakeibo.setIncomeAmount(0);
		kakeibo.setUsedStore("コンビニ");
		kakeibo.setRemarks("お醤油");
	}

	/** 家計簿登録 (正常系) */

	@Test
	@DisplayName("正しい値を渡すと家計簿の登録が成功する")
	void whenValidInformation_expectToInsertKakeibo() {
		// 実行
		boolean actual = mapper.saveKakeibo(kakeibo);

		// 検証 (actualの値がtrueになることを期待する)
		assertTrue(actual);
	}

	/** 家計簿登録 (異常系) */

	@Test
	@DisplayName("ユーザIDがnullのとき、例外をスローする")
	void whenUserIdDoesNotExist_throwException() {
		// 準備 (ユーザIDにnullをセット)
		kakeibo.setUserId(null);

		// 実行 & 検証
		assertThrows(DataIntegrityViolationException.class, () -> mapper.saveKakeibo(kakeibo));
	}

	@Test
	@DisplayName("支払い日がnullのとき、例外をスローする")
	void whenPaymentDateDoesNotExist_throwException() {
		// 準備 (支払日にnullをセット)
		kakeibo.setPaymentDate(null);

		// 実行 & 検証
		assertThrows(DataIntegrityViolationException.class, () -> mapper.saveKakeibo(kakeibo));
	}

	@Test
	@DisplayName("費目IDがnullのとき、例外をスローする")
	void whenExpenseItemIdDoesNotExist_throwException() {
		// 準備 (費目IDにnullをセット)
		kakeibo.setExpenseItemId(null);

		// 実行 & 検証
		assertThrows(DataIntegrityViolationException.class, () -> mapper.saveKakeibo(kakeibo));
	}

	@Test
	@DisplayName("支出金額がnullのとき、例外をスローする")
	void whenExpenditureAmountDoesNotExist_throwException() {
		// 準備 (支払い金額にnullをセット)
		kakeibo.setExpenditureAmount(null);

		// 実行 & 検証
		assertThrows(DataIntegrityViolationException.class, () -> mapper.saveKakeibo(kakeibo));
	}

	@Test
	@DisplayName("収入金額がnullのとき、例外をスローする")
	void whenIncomeAmountDoesNotExist_throwException() {
		// 準備 (収入金額にnullをセット)
		kakeibo.setIncomeAmount(null);

		// 実行 & 検証
		assertThrows(DataIntegrityViolationException.class, () -> mapper.saveKakeibo(kakeibo));
	}

}
