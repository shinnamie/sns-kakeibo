package com.example.repository.kakeibo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.kakeibo.DeletedKakeibo;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.domain.kakeibo.TotalByIncomeAndExpenditureBreakdown;

@Mapper
public interface KakeiboMapper {
	
	/**
	 * 家計簿情報の全件取得
	 * 
	 * @return 検索結果
	 */
	List<Kakeibo> findAll();

	/**
	 * 家計簿idから家計簿情報を取得
	 * 
	 * @return 検索結果(1件)
	 */
	Kakeibo findByKakeiboId(Integer id);

	/**
	 * 家計簿の新規登録
	 * 
	 * @param kakeibo
	 */
	void save(Kakeibo kakeibo);

	/**
	 * 家計簿の編集
	 * 
	 * @param kakeibo
	 */
	void update(Kakeibo kakeibo);

	/**
	 * 論理削除フラグ
	 * 
	 * @param kakeibo 家計簿
	 */
	void updateIsDelete(Kakeibo kakeibo);

	/**
	 * 家計簿の論理削除
	 * 
	 * @param deletedKakeibo 家計簿論理削除データ
	 */
	void delete(DeletedKakeibo deletedKakeibo);

	/**
	 * 本月の収支内訳を算出する
	 * 
	 * @param year  年
	 * @param month 月
	 * @return 収支内訳
	 */
	List<TotalByIncomeAndExpenditureBreakdown> totalIncomeAndExpenditureBreakdown(String yearAndMonth);

	/**
	 * 月別集計
	 * 
	 * @param year  集計年
	 * @param month 集計月
	 * @return 集計結果
	 */
	List<Kakeibo> findKakeiboByYearAndMonth(String year, String month);

	/**
	 * 月別の収支計算結果
	 * 
	 * @return
	 */
	MonthlyBalanceCalculationResult monthlyBalanceCalculate(String year, String month);

	/**
	 * 月別の収支計算結果(収支内訳画面用)
	 * 
	 * @return
	 */
	MonthlyBalanceCalculationResult monthlyBalanceCalculateOfBreakdown(String yearAndMonth);

}
