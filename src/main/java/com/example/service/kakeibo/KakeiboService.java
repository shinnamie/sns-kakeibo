package com.example.service.kakeibo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.domain.kakeibo.TotalByIncomeAndExpenditureBreakdown;
import com.example.repository.kakeibo.KakeiboMapper;

@Service
public class KakeiboService {

	@Autowired
	KakeiboMapper kakeiboMapper;

	/**
	 * 家計簿情報の全件取得
	 * 
	 * @return 検索結果
	 */
	public List<Kakeibo> findKakeiboList() {
		return kakeiboMapper.findAll();
	}


	/**
	 * 家計簿idから家計簿情報を取得
	 * 
	 * @param id 家計簿id
	 * @return kakeibo 家計簿
	 */
	public Kakeibo findByKakeiboId(long id) {
		return kakeiboMapper.findByKakeiboId(id);
	}

	/**
	 * 家計簿の新規登録
	 * 
	 * @param kakeibo
	 */
	public void save(Kakeibo kakeibo) {
		kakeiboMapper.save(kakeibo);
	}

	/**
	 * 家計簿の編集
	 * 
	 * @param kakeibo
	 */
	public void update(Kakeibo kakeibo) {
		kakeiboMapper.update(kakeibo);
	}


	/**
	 * 家計簿の削除
	 * 
	 * @param id 
	 */
	public void deleteKakeibo(long id) {
		kakeiboMapper.delete(id);
	}


	/**
	 * 本月の収支内訳を算出する
	 * 
	 * @param year  年
	 * @param month 月
	 * @return 収支内訳
	 */
	public List<TotalByIncomeAndExpenditureBreakdown> totalByIncomeAndExpenditureBreakdown(String yearAndMonth) {
		return kakeiboMapper.totalIncomeAndExpenditureBreakdown(yearAndMonth);
	}

	/**
	 * 月別集計
	 * 
	 * @param year  集計年
	 * @param month 集計月
	 * @return 集計結果
	 */
	public List<Kakeibo> findByYearAndMonth(String year, String month) {
		return kakeiboMapper.findByYearAndMonth(year, month);
	}

	/**
	 * 月別の収支計算結果
	 * 
	 * @return
	 */
	public MonthlyBalanceCalculationResult monthlyBalanceCalculate(String year, String month) {
		return kakeiboMapper.monthlyBalanceCalculate(year, month);
	}

	/**
	 * 月別の収支計算結果(収支内訳画面用)
	 * 
	 * @return
	 */
	public MonthlyBalanceCalculationResult monthlyBalanceCalculateOfBreakdown(String yearAndMonth) {
		return kakeiboMapper.monthlyBalanceCalculateOfBreakdown(yearAndMonth);
	}

	/**
	 * 月初日と月末日を取得する
	 * 
	 * @return
	 */
	public String getFirstDayAndLastDay(LocalDate now) {
		// 月初日と月末日を取得
		LocalDate ldFirstDate = now.withDayOfMonth(1);// 月初
		String firstDate = ldFirstDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		int length = now.lengthOfMonth();
		LocalDate ldLastDate = now.withDayOfMonth(length);// 月末
		String lastDate = ldLastDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		return firstDate + " - " + lastDate;
	}

}
