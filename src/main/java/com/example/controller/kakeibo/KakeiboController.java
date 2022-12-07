package com.example.controller.kakeibo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.kakeibo.DeletedKakeibo;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.domain.kakeibo.TotalByIncomeAndExpenditureBreakdown;
import com.example.form.kakeibo.AddKakeiboForm;
import com.example.form.kakeibo.EditKakeiboForm;
import com.example.service.kakeibo.KakeiboService;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@Autowired
	KakeiboService kakeiboService;

	@ModelAttribute
	private EditKakeiboForm editKakeiboForm() {
		return new EditKakeiboForm();
	}

	/**
	 * 家計簿一覧画面を表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(Model model) {
		List<Kakeibo> kakeiboList = kakeiboService.kakeiboList();
		model.addAttribute("kakeiboList", kakeiboList);
		return "kakeibo/kakeiboList";
	}

	@GetMapping(value = "/update")
	public String update(Integer id, EditKakeiboForm editKakeiboForm) {
		// idから家計簿情報を1件取得する
		Kakeibo kakeibo = kakeiboService.findByKakeiboId(id);

		// 値をコピー(kakeibo→editKakeiboForm)
		BeanUtils.copyProperties(kakeibo, editKakeiboForm);

		// 収入金額と支出金額に関してはdomainと型が異なるため手動でセット
		String expenditureAmount = kakeibo.getExpenditureAmount().toString();
		String incomeAmount = kakeibo.getIncomeAmount().toString();
		editKakeiboForm.setExpenditureAmount(expenditureAmount);
		editKakeiboForm.setIncomeAmount(incomeAmount);

		return "kakeibo/edit";
	}

	/**
	 * 収支内訳画面を表示する
	 * 
	 * @return
	 */
	@GetMapping(value = "/breakdown-income-balance")
	public String breakdownIncomeBalance() {
		return "kakeibo/breakdown-income-balance";
	}

	/**
	 * 年別・月別集計画面を表示する
	 * 
	 * @return
	 */
	@GetMapping(value = "/yearly-or-monthly-aggregation")
	public String monthlyAggregation() {
		return "kakeibo/year-and-month";
	}

	/** 家計簿を追加する画面(register.html)を表示 */
	@GetMapping("/registerKakeibo")
	public String getRegisterKakeibo(@ModelAttribute AddKakeiboForm form) {
		return "kakeibo/register";
	}

	/** 家計簿の追加処理 */
	@PostMapping("/saveKakeibo")
	public String saveKakeibo(@ModelAttribute @Validated AddKakeiboForm form, BindingResult result, Model model) {
		// 入力値チェック
		if (result.hasErrors()) {
			return getRegisterKakeibo(form);
		}

		// フォームの値をドメインにコピー
		Kakeibo kakeibo = new Kakeibo();
		BeanUtils.copyProperties(form, kakeibo);

		// 新規登録処理 (登録失敗した場合は登録画面に戻る)
		if (!kakeiboService.saveKakeibo(kakeibo)) {
			model.addAttribute("errorMessage", "登録が失敗しました");
			return getRegisterKakeibo(form);
		}

		return "redirect:/kakeibo/list";
	}

	/**
	 * 家計簿の更新処理
	 * 
	 * @param editKakeiboForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Validated EditKakeiboForm editKakeiboForm, BindingResult result) {
		if (result.hasErrors()) {
			return "kakeibo/edit";
		}

		Kakeibo kakeibo = new Kakeibo();

		// 値をdomainにコピー
		BeanUtils.copyProperties(editKakeiboForm, kakeibo);

		// 支出金額と収入金額を変換してセット
		Integer expenditureAmount = Integer.parseInt(editKakeiboForm.getExpenditureAmount());
		Integer incomeAmount = Integer.parseInt(editKakeiboForm.getIncomeAmount());
		kakeibo.setExpenditureAmount(expenditureAmount);
		kakeibo.setIncomeAmount(incomeAmount);

		// 更新処理の実装
		kakeiboService.update(kakeibo);

		return "redirect:/kakeibo/list";
	}

	/**
	 * 家計簿を論理削除する
	 * 
	 * @param id 家計簿id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Integer id) {
		DeletedKakeibo deletedKakeibo = new DeletedKakeibo();
		Kakeibo kakeibo = new Kakeibo();

		// 値をセット
		deletedKakeibo.setKakeiboId(id);

		// 削除フラグをtrueにする
		kakeibo.setId(id);
		kakeibo.setDeleted(true);

		// 論理削除の実行
		kakeiboService.delete(deletedKakeibo);
		kakeiboService.updateIsDelete(kakeibo);

		return "redirect:/kakeibo/list";
	}

	/**
	 * 収支内訳を算出する
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "{date}", method = RequestMethod.GET)
	public String incomeAndExpenditureBalance(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
		// 月初日と月末日を取得する
		String firstDateAndLastDate = kakeiboService.getFirstDayAndLastDay(date);
		model.addAttribute("firstDateAndLastDate", firstDateAndLastDate);

		// 年と月をString型で取得
		String yearAndMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));

		// 収支計算結果の取得
		MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = kakeiboService
				.monthlyBalanceCalculateOfBreakdown(yearAndMonth);
		if (monthlyBalanceCalculationResult == null) { // データが存在しない場合
			model.addAttribute("message", "該当月のデータが存在しません。");
			return "kakeibo/breakdown-income-balance";
		}

		// 年月の値を送り収支内訳結果を取得する
		List<TotalByIncomeAndExpenditureBreakdown> totalByIncomeAndExpenditureBreakdownList = kakeiboService
				.totalByIncomeAndExpenditureBreakdown(yearAndMonth);

		// それぞれをスコープに格納
		model.addAttribute("totalByIncomeAndExpenditureBreakdownList", totalByIncomeAndExpenditureBreakdownList);
		model.addAttribute("monthlyBalanceCalculationResult", monthlyBalanceCalculationResult);

		return breakdownIncomeBalance();
	}

	/**
	 * 日付を取得するためのメソッド
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/breakdown", method = RequestMethod.GET)
	public String receiveDate(Model model) {
		// 本日の年月を取得
		LocalDate date = LocalDate.now();
		model.addAttribute("date", date);

		return incomeAndExpenditureBalance(date, model);
	}

	/**
	 * 月別集計結果を取得する
	 * 
	 * @param year
	 * @param month
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/aggregated-year-or-month", method = RequestMethod.POST)
	public String aggregatedByMonth(String year, String month, Model model) {

		// 年月のパラメーターを元に検索、結果を取得
		List<Kakeibo> kakeiboMonthList = kakeiboService.findByYearAndMonth(year, month);

		// 収支計算結果の取得
		MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = kakeiboService
				.monthlyBalanceCalculate(year, month);

		// それぞれの結果をスコープに格納
		if (kakeiboMonthList == null || kakeiboMonthList.size() == 0) {
			model.addAttribute("message", "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
		} else {
			model.addAttribute("kakeiboMonthList", kakeiboMonthList);
			model.addAttribute("monthlyBalanceCalculationResult", monthlyBalanceCalculationResult);
		}

		return "kakeibo/year-and-month";
	}
}
