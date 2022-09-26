package com.example.domain.kakeibo;

public class IncomeAndExpenditureBreakdown {

	/** 費目Id */
	private Integer expenseItemId;
	/** 費目名 */
	private String expenseItemName;
	/** 食費合計 */
	private Integer foodExpenses;
	/** 食費の割合 */
	private double foodExpenseRate;
	/** 日用品費合計 */
	private Integer dailyNecessitiesExpenses;
	/** 日用品費の割合 */
	private double dailyNecessitiesExpenseRate;
	/** 趣味・娯楽費合計 */
	private Integer hobbyExpenses;
	/** 趣味・娯楽費の割合 */
	private double hobbyExpenseRate;
	/** 交際費合計 */
	private Integer entertainmentExpenses;
	/** 交際費の割合 */
	private double entertainmentExpensesRate;
	/** 交通費合計 */
	private Integer transportationExpenses;
	/** 交通費の割合 */
	private double transportationExpensesRate;
	/** 衣服費合計 */
	private Integer clothingExpenses;
	/** 衣服費の割合 */
	private double clothingExpensesRate;
	/** 美容費合計 */
	private Integer beautyExpenses;
	/** 美容費の割合 */
	private double beautyExpensesRate;
	/** 健康・医療費合計 */
	private Integer healthAndMedicalCareExpenses;
	/** 健康・医療費の割合 */
	private double healthAndMedicalCareExpensesRate;
	/** 自動車費合計 */
	private Integer carExpenses;
	/** 自動車費の割合 */
	private double carExpensesRate;
	/** 教養・教育費合計 */
	private Integer educationalExpenses;
	/** 教養・教育費の割合 */
	private double educationalExpensesRate;
	/** 水道・光熱費合計 */
	private Integer utilityExpenses;
	/** 水道・光熱費の割合 */
	private double utilityExpensesRate;
	/** 通信費合計 */
	private Integer communicationExpenses;
	/** 通信費の割合 */
	private double communicationExpensesRate;
	/** 住宅費合計 */
	private Integer housingExpenses;
	/** 住宅費の割合 */
	private double housingExpensesRate;
	/** 税・社会保障費合計 */
	private Integer taxAndSocialSecurityExpenses;
	/** 税・社会保障費の割合 */
	private double taxAndSocialSecurityExpensesRate;
	/** 保険費用合計 */
	private Integer insuranceExpenses;
	/** 保険費用の割合 */
	private double insuranceExpensesRate;
	/** その他合計 */
	private Integer othersExpenses;
	/** その他の費用の割合 */
	private double othersExpensesRate;
	/** 支出合計 */
	private Integer totalExpenditureAmount;

	public Integer getExpenseItemId() {
		return expenseItemId;
	}

	public void setExpenseItemId(Integer expenseItemId) {
		this.expenseItemId = expenseItemId;
	}

	public String getExpenseItemName() {
		return expenseItemName;
	}

	public void setExpenseItemName(String expenseItemName) {
		this.expenseItemName = expenseItemName;
	}
	public Integer getFoodExpenses() {
		return foodExpenses;
	}
	public void setFoodExpenses(Integer foodExpenses) {
		this.foodExpenses = foodExpenses;
	}
	public double getFoodExpenseRate() {
		return foodExpenseRate;
	}
	public void setFoodExpenseRate(double foodExpenseRate) {
		this.foodExpenseRate = foodExpenseRate;
	}
	public Integer getDailyNecessitiesExpenses() {
		return dailyNecessitiesExpenses;
	}
	public void setDailyNecessitiesExpenses(Integer dailyNecessitiesExpenses) {
		this.dailyNecessitiesExpenses = dailyNecessitiesExpenses;
	}
	public double getDailyNecessitiesExpenseRate() {
		return dailyNecessitiesExpenseRate;
	}
	public void setDailyNecessitiesExpenseRate(double dailyNecessitiesExpenseRate) {
		this.dailyNecessitiesExpenseRate = dailyNecessitiesExpenseRate;
	}
	public Integer getHobbyExpenses() {
		return hobbyExpenses;
	}
	public void setHobbyExpenses(Integer hobbyExpenses) {
		this.hobbyExpenses = hobbyExpenses;
	}
	public double getHobbyExpenseRate() {
		return hobbyExpenseRate;
	}
	public void setHobbyExpenseRate(double hobbyExpenseRate) {
		this.hobbyExpenseRate = hobbyExpenseRate;
	}
	public Integer getEntertainmentExpenses() {
		return entertainmentExpenses;
	}
	public void setEntertainmentExpenses(Integer entertainmentExpenses) {
		this.entertainmentExpenses = entertainmentExpenses;
	}
	public double getEntertainmentExpensesRate() {
		return entertainmentExpensesRate;
	}
	public void setEntertainmentExpensesRate(double entertainmentExpensesRate) {
		this.entertainmentExpensesRate = entertainmentExpensesRate;
	}
	public Integer getTransportationExpenses() {
		return transportationExpenses;
	}
	public void setTransportationExpenses(Integer transportationExpenses) {
		this.transportationExpenses = transportationExpenses;
	}
	public double getTransportationExpensesRate() {
		return transportationExpensesRate;
	}
	public void setTransportationExpensesRate(double transportationExpensesRate) {
		this.transportationExpensesRate = transportationExpensesRate;
	}
	public Integer getClothingExpenses() {
		return clothingExpenses;
	}
	public void setClothingExpenses(Integer clothingExpenses) {
		this.clothingExpenses = clothingExpenses;
	}
	public double getClothingExpensesRate() {
		return clothingExpensesRate;
	}
	public void setClothingExpensesRate(double clothingExpensesRate) {
		this.clothingExpensesRate = clothingExpensesRate;
	}
	public Integer getBeautyExpenses() {
		return beautyExpenses;
	}
	public void setBeautyExpenses(Integer beautyExpenses) {
		this.beautyExpenses = beautyExpenses;
	}
	public double getBeautyExpensesRate() {
		return beautyExpensesRate;
	}
	public void setBeautyExpensesRate(double beautyExpensesRate) {
		this.beautyExpensesRate = beautyExpensesRate;
	}
	public Integer getHealthAndMedicalCareExpenses() {
		return healthAndMedicalCareExpenses;
	}
	public void setHealthAndMedicalCareExpenses(Integer healthAndMedicalCareExpenses) {
		this.healthAndMedicalCareExpenses = healthAndMedicalCareExpenses;
	}
	public double getHealthAndMedicalCareExpensesRate() {
		return healthAndMedicalCareExpensesRate;
	}
	public void setHealthAndMedicalCareExpensesRate(double healthAndMedicalCareExpensesRate) {
		this.healthAndMedicalCareExpensesRate = healthAndMedicalCareExpensesRate;
	}
	public Integer getCarExpenses() {
		return carExpenses;
	}
	public void setCarExpenses(Integer carExpenses) {
		this.carExpenses = carExpenses;
	}
	public double getCarExpensesRate() {
		return carExpensesRate;
	}
	public void setCarExpensesRate(double carExpensesRate) {
		this.carExpensesRate = carExpensesRate;
	}
	public Integer getEducationalExpenses() {
		return educationalExpenses;
	}
	public void setEducationalExpenses(Integer educationalExpenses) {
		this.educationalExpenses = educationalExpenses;
	}
	public double getEducationalExpensesRate() {
		return educationalExpensesRate;
	}
	public void setEducationalExpensesRate(double educationalExpensesRate) {
		this.educationalExpensesRate = educationalExpensesRate;
	}
	public Integer getUtilityExpenses() {
		return utilityExpenses;
	}
	public void setUtilityExpenses(Integer utilityExpenses) {
		this.utilityExpenses = utilityExpenses;
	}
	public double getUtilityExpensesRate() {
		return utilityExpensesRate;
	}
	public void setUtilityExpensesRate(double utilityExpensesRate) {
		this.utilityExpensesRate = utilityExpensesRate;
	}
	public Integer getCommunicationExpenses() {
		return communicationExpenses;
	}
	public void setCommunicationExpenses(Integer communicationExpenses) {
		this.communicationExpenses = communicationExpenses;
	}
	public double getCommunicationExpensesRate() {
		return communicationExpensesRate;
	}
	public void setCommunicationExpensesRate(double communicationExpensesRate) {
		this.communicationExpensesRate = communicationExpensesRate;
	}
	public Integer getHousingExpenses() {
		return housingExpenses;
	}
	public void setHousingExpenses(Integer housingExpenses) {
		this.housingExpenses = housingExpenses;
	}
	public double getHousingExpensesRate() {
		return housingExpensesRate;
	}
	public void setHousingExpensesRate(double housingExpensesRate) {
		this.housingExpensesRate = housingExpensesRate;
	}
	public Integer getTaxAndSocialSecurityExpenses() {
		return taxAndSocialSecurityExpenses;
	}
	public void setTaxAndSocialSecurityExpenses(Integer taxAndSocialSecurityExpenses) {
		this.taxAndSocialSecurityExpenses = taxAndSocialSecurityExpenses;
	}
	public double getTaxAndSocialSecurityExpensesRate() {
		return taxAndSocialSecurityExpensesRate;
	}
	public void setTaxAndSocialSecurityExpensesRate(double taxAndSocialSecurityExpensesRate) {
		this.taxAndSocialSecurityExpensesRate = taxAndSocialSecurityExpensesRate;
	}
	public Integer getInsuranceExpenses() {
		return insuranceExpenses;
	}
	public void setInsuranceExpenses(Integer insuranceExpenses) {
		this.insuranceExpenses = insuranceExpenses;
	}
	public double getInsuranceExpensesRate() {
		return insuranceExpensesRate;
	}
	public void setInsuranceExpensesRate(double insuranceExpensesRate) {
		this.insuranceExpensesRate = insuranceExpensesRate;
	}
	public Integer getOthersExpenses() {
		return othersExpenses;
	}
	public void setOthersExpenses(Integer othersExpenses) {
		this.othersExpenses = othersExpenses;
	}
	public double getOthersExpensesRate() {
		return othersExpensesRate;
	}
	public void setOthersExpensesRate(double othersExpensesRate) {
		this.othersExpensesRate = othersExpensesRate;
	}
	public Integer getTotalExpenditureAmount() {
		return totalExpenditureAmount;
	}
	public void setTotalExpenditureAmount(Integer totalExpenditureAmount) {
		this.totalExpenditureAmount = totalExpenditureAmount;
	}
	@Override
	public String toString() {
		return "IncomeAndExpenditureBreakdown [expenseItemId=" + expenseItemId + ", expenseItemName=" + expenseItemName
				+ ", foodExpenses=" + foodExpenses + ", foodExpenseRate=" + foodExpenseRate
				+ ", dailyNecessitiesExpenses=" + dailyNecessitiesExpenses + ", dailyNecessitiesExpenseRate="
				+ dailyNecessitiesExpenseRate + ", hobbyExpenses=" + hobbyExpenses + ", hobbyExpenseRate="
				+ hobbyExpenseRate + ", entertainmentExpenses=" + entertainmentExpenses + ", entertainmentExpensesRate="
				+ entertainmentExpensesRate + ", transportationExpenses=" + transportationExpenses
				+ ", transportationExpensesRate=" + transportationExpensesRate + ", clothingExpenses="
				+ clothingExpenses + ", clothingExpensesRate=" + clothingExpensesRate + ", beautyExpenses="
				+ beautyExpenses + ", beautyExpensesRate=" + beautyExpensesRate + ", healthAndMedicalCareExpenses="
				+ healthAndMedicalCareExpenses + ", healthAndMedicalCareExpensesRate="
				+ healthAndMedicalCareExpensesRate + ", carExpenses=" + carExpenses + ", carExpensesRate="
				+ carExpensesRate + ", educationalExpenses=" + educationalExpenses + ", educationalExpensesRate="
				+ educationalExpensesRate + ", utilityExpenses=" + utilityExpenses + ", utilityExpensesRate="
				+ utilityExpensesRate + ", communicationExpenses=" + communicationExpenses
				+ ", communicationExpensesRate=" + communicationExpensesRate + ", housingExpenses=" + housingExpenses
				+ ", housingExpensesRate=" + housingExpensesRate + ", taxAndSocialSecurityExpenses="
				+ taxAndSocialSecurityExpenses + ", taxAndSocialSecurityExpensesRate="
				+ taxAndSocialSecurityExpensesRate + ", insuranceExpenses=" + insuranceExpenses
				+ ", insuranceExpensesRate=" + insuranceExpensesRate + ", othersExpenses=" + othersExpenses
				+ ", othersExpensesRate=" + othersExpensesRate + ", totalExpenditureAmount=" + totalExpenditureAmount
				+ "]";
	}


}
