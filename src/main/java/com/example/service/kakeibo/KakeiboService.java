package com.example.service.kakeibo;

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

}
