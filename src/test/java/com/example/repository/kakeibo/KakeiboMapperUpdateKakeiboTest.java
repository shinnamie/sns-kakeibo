package com.example.repository.kakeibo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.domain.kakeibo.Kakeibo;

@SpringBootTest
class KakeiboMapperUpdateKakeiboTest {
	
	private Kakeibo kakeibo;
	
	@Autowired
	private KakeiboMapper kakeiboMapper;
	
	@BeforeEach
	void setUp() throws Exception {
		// IDが「1」の家計簿を取得
		kakeibo = kakeiboMapper.findByKakeiboId(1L);
	}
	
	/** 入力値チェックに問題がない場合、更新処理ができるかのテスト */
	
	// 異常系
	
	@Test
	@DisplayName("決済日付がnullの時、例外を返す")
	void testPaymentDateIsNull() throws Exception {
		
		// 更新後の値をセット
		kakeibo.setPaymentDate(null);
		
		// 検証(例外がスローされるか)
		assertThrows(DataIntegrityViolationException.class, () -> kakeiboMapper.update(kakeibo));
	}
	
	@Test
	@DisplayName("費目IDがnullの時、例外を返す")
	void testExpenseItemIdIsNull() throws Exception {
		
		// 更新後の値をセット
		kakeibo.setExpenseItemId(null);
		
		// 検証(例外がスローされるか)
		assertThrows(DataIntegrityViolationException.class, () -> kakeiboMapper.update(kakeibo));
	}
	
	@Test
	@DisplayName("支出金額がnullの時、例外を返す")
	void testExpenditureAmountIsNull() throws Exception {
		
		// 更新後の値をセット
		kakeibo.setExpenditureAmount(null);
		
		// 検証(例外がスローされるか)
		assertThrows(DataIntegrityViolationException.class, () -> kakeiboMapper.update(kakeibo));
	}
	
	@Test
	@DisplayName("収入金額がnullの時、例外を返す")
	void testIncomeAmountIsNull() throws Exception {
		
		// 更新後の値をセット
		kakeibo.setIncomeAmount(null);
		
		// 検証(例外がスローされるか)
		assertThrows(DataIntegrityViolationException.class, () -> kakeiboMapper.update(kakeibo));
	}
	
	// 正常系

	@Test
	@DisplayName("入力項目に問題がない時、更新に成功する")
	void testSuccess() throws Exception {
		
		// 更新後の値をセット
		kakeibo.setPaymentDate(LocalDate.parse("2022-11-11"));
		kakeibo.setExpenseItemId(8);
		kakeibo.setExpenditureAmount(3000);
		kakeibo.setIncomeAmount(0);
		
		// Mapperクラス実行
		kakeiboMapper.update(kakeibo);
		
		// IDが「1」の更新後の家計簿を取得
		Kakeibo updateKakeibo = kakeiboMapper.findByKakeiboId(1L);
		
		// 検証(更新後の値になっているか)
		assertEquals(LocalDate.parse("2022-11-11"), updateKakeibo.getPaymentDate());
		assertEquals(8, updateKakeibo.getExpenseItemId());
		assertEquals(3000, updateKakeibo.getExpenditureAmount());
		assertEquals(0, updateKakeibo.getIncomeAmount());
	}

}
