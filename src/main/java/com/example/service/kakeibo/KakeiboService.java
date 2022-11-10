package com.example.service.kakeibo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.kakeibo.DeletedKakeibo;
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
	public List<Kakeibo> kakeiboList() {
		return kakeiboMapper.findAll();
	}


	/**
	 * 家計簿idから家計簿情報を取得
	 * 
	 * @param id 家計簿id
	 * @return kakeibo 家計簿
	 */
	public Kakeibo findByKakeiboId(Integer id) {
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
	 * 論理削除フラグ
	 * 
	 * @param kakeibo 家計簿
	 */
	public void updateIsDelete(Kakeibo kakeibo) {
		kakeiboMapper.updateIsDelete(kakeibo);
	}

	/**
	 * 家計簿の論理削除
	 * 
	 * @param deletedKakeibo 家計簿論理削除データ
	 */
	public void delete(DeletedKakeibo deletedKakeibo) {
		kakeiboMapper.delete(deletedKakeibo);
	}

	/**
	 * 本月の費目別の支出・収入を算出する
	 * 
	 * @param year  年
	 * @param month 月
	 * @return Kakeibo 収支内訳(費目ID・費目別支出・費目別収入・費目名)
	 */
	public List<Kakeibo> totalByIncomeAndExpenditureBreakdown(String yearAndMonth) {
		System.out.println(kakeiboMapper.totalIncomeAndExpenditureBreakdown(yearAndMonth));
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
	
	/**
	 * 
	 * 
	 * @return 
	 * */

}
