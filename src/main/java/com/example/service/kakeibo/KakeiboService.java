package com.example.service.kakeibo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.HashMap;
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
	 * 選択された月の費目別の支出・収入を算出する
	 * 
	 * @param year  年
	 * @param month 月
	 * @return Kakeibo 収支内訳(費目ID・費目別支出・費目別収入・費目名)
	 */
	@Transactional(readOnly = true)
	public List<Kakeibo> totalByIncomeAndExpenditureBreakdown(String yearAndMonth) {
		List<Kakeibo> kakeiboItemList = kakeiboMapper.totalIncomeAndExpenditureBreakdown(yearAndMonth);
		
		if(kakeiboMapper.totalIncomeAndExpenditureBreakdown(yearAndMonth) == null || kakeiboMapper.totalIncomeAndExpenditureBreakdown(yearAndMonth).size() == 0) {
			return null;
		}
		return kakeiboItemList;
	}

	/**
	 * 月別の費目別の収入・支出を計算
	 * 
	 * @param totalByIncomeAndExpenditureBreakdown(String yearAndMonth)
	 * @return Map<String, Integer>
	 */
	public Map<String, Integer> findBreakdown(List<Kakeibo> kakeiboItemList) {
		
		if(kakeiboItemList == null || kakeiboItemList.size() == 0) {
			return null;
		}

		// Mapを生成
		Map<String, Integer> kakeiboItemMap = new HashMap<>();

		// 総収入・総支出・収支を初期設定
		Integer totalExpenditureAmount = 0;
		Integer totalIncomeAmount = 0;
		Integer incomeAndExpenditure = 0;

		// ListからMapへ移し替え
		for (Kakeibo kakeiboItem : kakeiboItemList) {
			// 費目が支出か収入かで条件分岐
			if (kakeiboItem.getExpenditureAmount() != null) {
				kakeiboItemMap.put(kakeiboItem.getExpenseItem().getExpenseItemName(),
						kakeiboItem.getExpenditureAmount());
				totalExpenditureAmount += kakeiboItem.getExpenditureAmount();
			} else {
				kakeiboItemMap.put(kakeiboItem.getExpenseItem().getExpenseItemName(), kakeiboItem.getIncomeAmount());
				totalIncomeAmount += kakeiboItem.getIncomeAmount();
			}
		}

		// 総収入・総支出・収支をMapに格納
		kakeiboItemMap.put("総支出", totalExpenditureAmount);
		kakeiboItemMap.put("総収入", totalIncomeAmount);
		incomeAndExpenditure = totalIncomeAmount - totalExpenditureAmount;
		kakeiboItemMap.put("収支", incomeAndExpenditure);

		return kakeiboItemMap;
	}

	/**
	 * 総収入・総支出・収支だけ取り出す
	 * 
	 * 
	 */
	public Map<String, Integer> totalAmountMap(Map<String, Integer> kakeiboItemMap) {
		if(kakeiboItemMap.isEmpty()) {
			return null;
		}
		// Mapを生成
		Map<String, Integer> totalAmountMap = new HashMap<>();
		totalAmountMap.put("総収入", kakeiboItemMap.get("総収入"));
		totalAmountMap.put("総支出", kakeiboItemMap.get("総支出"));
		totalAmountMap.put("収支", kakeiboItemMap.get("収支"));
		return totalAmountMap;
	}

	/**
	 * 費目だけ取り出す
	 * 
	 * 
	 */
	public Map<String, Integer> itemExpenseMap(Map<String, Integer> kakeiboItemMap) {
		if(kakeiboItemMap.isEmpty()) {
			return null;
		}
		kakeiboItemMap.remove("総収入");
		kakeiboItemMap.remove("総支出");
		kakeiboItemMap.remove("収支");
		return kakeiboItemMap;
	}

	/**
	 * Map<String,Integer> → Map<String,Double>に変換する
	 * 
	 * 
	 */
	public Map<String, Double> integerToDouble(Map<String, Integer> kakeiboItemMap) {
		if(kakeiboItemMap.isEmpty()) {
			return null;
		}
		return kakeiboItemMap.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> Double.valueOf(e.getValue())));
	}

	/**
	 * Map内の費目別の割合を計算し、Map<費目名,割合>を返す
	 * 
	 * 
	 */
	public Map<String, Double> culcRate(Map<String, Double> doubleMap) {
		if(doubleMap == null) {
			return null;
		}

		// 総支出を変数に格納
		Double totalExpenditureAmount = doubleMap.get("総支出");
		System.out.println(totalExpenditureAmount);

		// 費目別の割合を格納するMapを生成
		Map<String, Double> rateMap = new HashMap<>();

		// 費目別の割合をrateMapに格納していく
		// 給料
		if (doubleMap.containsKey("給料")) {
			Double salary = doubleMap.get("給料");
			Double expenditureRateSalary = salary / totalExpenditureAmount * 100;
			Double roundExpenditureRateSalary = Math.round(expenditureRateSalary * 100.0) / 100.0;
			rateMap.put("給料", roundExpenditureRateSalary);
		}

		// 雑収入
		if (doubleMap.containsKey("雑収入")) {
			Double income = doubleMap.get("雑収入");
			Double expenditureRateIncome = income / totalExpenditureAmount * 100;
			Double roundExpenditureRateIncome = Math.round(expenditureRateIncome * 100.0) / 100.0;
			rateMap.put("雑収入", roundExpenditureRateIncome);
		}

		// 振替・振込
		if (doubleMap.containsKey("振替・振込")) {
			Double transfer = doubleMap.get("振替・振込");
			Double expenditureRateTransfer = transfer / totalExpenditureAmount * 100;
			Double roundExpenditureRateTransfer = Math.round(expenditureRateTransfer * 100.0) / 100.0;
			rateMap.put("振替・振込", roundExpenditureRateTransfer);
		}

		// 食費
		if (doubleMap.containsKey("食費")) {
			Double food = doubleMap.get("食費");
			Double expenditureRateFood = food / totalExpenditureAmount * 100;
			Double roundExpenditureRateFood = Math.round(expenditureRateFood * 100.0) / 100.0;
			rateMap.put("食費", roundExpenditureRateFood);
		}

		// 日用品
		if (doubleMap.containsKey("日用品")) {
			Double daily = doubleMap.get("日用品");
			Double expenditureRateDaily = daily / totalExpenditureAmount * 100;
			Double roundExpenditureRateDaily = Math.round(expenditureRateDaily * 100.0) / 100.0;
			rateMap.put("日用品", roundExpenditureRateDaily);
		}

		// 趣味・娯楽
		if (doubleMap.containsKey("趣味・娯楽")) {
			Double hobby = doubleMap.get("趣味・娯楽");
			Double expenditureRateHobby = hobby / totalExpenditureAmount * 100;
			Double roundExpenditureRateHobby = Math.round(expenditureRateHobby * 100.0) / 100.0;
			rateMap.put("趣味・娯楽", roundExpenditureRateHobby);
		}

		// 交際費
		if (doubleMap.containsKey("交際費")) {
			Double entertainment = doubleMap.get("交際費");
			Double expenditureRateEntertainment = entertainment / totalExpenditureAmount * 100;
			Double roundExpenditureRateEntertainment = Math.round(expenditureRateEntertainment * 100.0) / 100.0;
			rateMap.put("交際費", roundExpenditureRateEntertainment);
		}

		// 交通費
		if (doubleMap.containsKey("交通費")) {
			Double transportation = doubleMap.get("交通費");
			Double expenditureRateTransportation = transportation / totalExpenditureAmount * 100;
			Double roundExpenditureRateTransportation = Math.round(expenditureRateTransportation * 100.0) / 100.0;
			rateMap.put("交通費", roundExpenditureRateTransportation);
		}

		// 衣服
		if (doubleMap.containsKey("衣服")) {
			Double clothes = doubleMap.get("衣服");
			Double expenditureRateClothes = clothes / totalExpenditureAmount * 100;
			Double roundExpenditureRateClothes = Math.round(expenditureRateClothes * 100.0) / 100.0;
			rateMap.put("衣服", roundExpenditureRateClothes);
		}

		// 美容
		if (doubleMap.containsKey("美容")) {
			Double beauty = doubleMap.get("美容");
			Double expenditureRateBeauty = beauty / totalExpenditureAmount * 100;
			Double roundExpenditureRateBeauty = Math.round(expenditureRateBeauty * 100.0) / 100.0;
			rateMap.put("美容", roundExpenditureRateBeauty);
		}

		// 健康・医療
		if (doubleMap.containsKey("健康・医療")) {
			Double health = doubleMap.get("健康・医療");
			Double expenditureRateHealth = health / totalExpenditureAmount * 100;
			Double roundExpenditureRateHealth = Math.round(expenditureRateHealth * 100.0) / 100.0;
			rateMap.put("健康・医療", roundExpenditureRateHealth);
		}

		// 自動車
		if (doubleMap.containsKey("自動車")) {
			Double car = doubleMap.get("自動車");
			Double expenditureRateCar = car / totalExpenditureAmount * 100;
			Double roundExpenditureRateCar = Math.round(expenditureRateCar * 100.0) / 100.0;
			rateMap.put("自動車", roundExpenditureRateCar);
		}

		// 教養・教育
		if (doubleMap.containsKey("教養・教育")) {
			Double education = doubleMap.get("教養・教育");
			Double expenditureRateEducation = education / totalExpenditureAmount * 100;
			Double roundExpenditureRateEducation = Math.round(expenditureRateEducation * 100.0) / 100.0;
			rateMap.put("教養・教育", roundExpenditureRateEducation);
		}

		// 水道・光熱費
		if (doubleMap.containsKey("水道・光熱費")) {
			Double water = doubleMap.get("水道・光熱費");
			Double expenditureRateWater = water / totalExpenditureAmount * 100;
			Double roundExpenditureRateWater = Math.round(expenditureRateWater * 100.0) / 100.0;
			rateMap.put("水道・光熱費", roundExpenditureRateWater);
		}

		// 通信費
		if (doubleMap.containsKey("通信費")) {
			Double phoneBill = doubleMap.get("通信費	");
			Double expenditureRatePhoneBill = phoneBill / totalExpenditureAmount * 100;
			Double roundExpenditureRatePhoneBill = Math.round(expenditureRatePhoneBill * 100.0) / 100.0;
			rateMap.put("通信費", roundExpenditureRatePhoneBill);
		}

		// 住宅
		if (doubleMap.containsKey("住宅")) {
			Double housing = doubleMap.get("住宅");
			Double expenditureRateHousing = housing / totalExpenditureAmount * 100;
			Double roundExpenditureRateHousing = Math.round(expenditureRateHousing * 100.0) / 100.0;
			rateMap.put("住宅", roundExpenditureRateHousing);
		}

		// 税・社会保障
		if (doubleMap.containsKey("税・社会保障")) {
			Double tax = doubleMap.get("税・社会保障");
			Double expenditureRateTax = tax / totalExpenditureAmount * 100;
			Double roundExpenditureRateTax = Math.round(expenditureRateTax * 100.0) / 100.0;
			rateMap.put("税・社会保障", roundExpenditureRateTax);
		}

		// 保険
		if (doubleMap.containsKey("保険")) {
			Double insurance = doubleMap.get("保険");
			Double expenditureRateInsurance = insurance / totalExpenditureAmount * 100;
			Double roundExpenditureRateInsurance = Math.round(expenditureRateInsurance * 100.0) / 100.0;
			rateMap.put("保険", roundExpenditureRateInsurance);
		}

		// その他
		if (doubleMap.containsKey("その他")) {
			Double others = doubleMap.get("その他");
			Double expenditureRateOthers = others / totalExpenditureAmount * 100;
			Double roundExpenditureRateOthers = Math.round(expenditureRateOthers * 100.0) / 100.0;
			rateMap.put("その他", roundExpenditureRateOthers);
		}

		return rateMap;
	}


	/**
	 * 月別で集計した費目毎の割合を計算
	 * 
	 * @return Map<String,Integer>
	 */
	public Map<String, Double> calcExpeditureRate(Map<String, Integer> kakeiboItemMap) {
		Map<String, Double> itemExpeditureRateMap = new HashMap<>();

		kakeiboItemMap.entrySet().stream().filter(e -> "自動車".equals(e.getKey())).forEach(System.out::println);

		return itemExpeditureRateMap;
	}

}
