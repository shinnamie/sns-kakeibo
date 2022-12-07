package com.example.service.kakeibo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.domain.kakeibo.Kakeibo;
import com.example.repository.kakeibo.KakeiboMapper;

@SpringBootTest
class KakeiboServiceSaveKakeiboTest {

	@InjectMocks
	KakeiboService service;

	@Mock
	KakeiboMapper mapper;

	Kakeibo kakeibo = new Kakeibo();

	// 家計簿登録 (正常系)

	@Test
	@DisplayName("登録が成功した際、戻り値としてTrueを返す")
	void whenValidInformation_expectToInsertKakeibo() {
		// 準備
		when(mapper.saveKakeibo(kakeibo)).thenReturn(true);
		
		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がtrueになることを期待する)
		assertTrue(actual);
	}

	/** 家計簿登録 (異常系) */

	@Test
	@DisplayName("例外が発生した際に、戻り値としてFalseを返す")
	void whenInvalidInformation_throwException() {
		// 準備
		when(mapper.saveKakeibo(kakeibo)).thenThrow(new DataIntegrityViolationException(null));

		// 実行
		boolean actual = service.saveKakeibo(kakeibo);

		// 検証 (actualの値がfalseになることを期待する)
		assertFalse(actual);
	}

}
