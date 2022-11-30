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
import com.example.form.kakeibo.SearchKakeiboForm;
import com.example.service.kakeibo.KakeiboService;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@Autowired
	KakeiboService kakeiboService;

	/**
	 * 支出金額及び収入金額はnullを許容しないため、あらかじめ初期値として0をセット
	 * 
	 * @return 家計簿新規登録フォーム
	 */
	@ModelAttribute
	private AddKakeiboForm addKakeiboForm() {
		return new AddKakeiboForm("0", "0");
	}

	@ModelAttribute
	private EditKakeiboForm editKakeiboForm() {
		return new EditKakeiboForm();
	}
	
	@ModelAttribute
	private SearchKakeiboForm searchKakeiboForm() {
		return new SearchKakeiboForm();
	}

	/**
	 * 家計簿一覧画面を表示
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String getList(Model model) {
		model.addAttribute("kakeiboList", kakeiboService.findKakeiboList());
		return "kakeibo/list";
	}
	
	/**
	 * 家計簿登録画面を表示(元のデータをデフォルト表示)
	 * 
	 * @return
	 */
	@GetMapping(value = "/addKakeibo")
	public String addKakeibo(AddKakeiboForm addKakeiboForm) {
		return "kakeibo/add";
	}

	/**
	 * 編集画面表示(初期値に登録済みデータ表示)
	 * 
	 * @param id
	 * @param editKakeiboForm
	 * @return
	 */
	@GetMapping("/edit")
	public String editKakeibo(Long id, EditKakeiboForm editKakeiboForm) {
		// idから家計簿情報を1件取得する
		Kakeibo kakeibo = kakeiboService.findByKakeiboId(id);

		// 値をコピー(kakeibo→editKakeiboForm)
		BeanUtils.copyProperties(kakeibo, editKakeiboForm);

		return "kakeibo/edit";
	}
	
	/**
	 * 家計簿の更新処理
	 * 
	 * @param editKakeiboForm
	 * @param result
	 * @return
	 */
	@PostMapping("/update")
	public String updateKakeibo(@Validated EditKakeiboForm editKakeiboForm, BindingResult result, Model model) {
		
		// 入力値エラーの際は編集画面を表示する
		if (result.hasErrors()) {
			return "kakeibo/edit";
		}

		Kakeibo kakeibo = new Kakeibo();

		// 値をdomainにコピー
		BeanUtils.copyProperties(editKakeiboForm, kakeibo);

		// 更新処理の実行
		if (!kakeiboService.updateKakeibo(kakeibo)) {
			model.addAttribute("errorMessage", "更新が失敗しました");
			return "kakeibo/edit";
		}

		return "redirect:/kakeibo/list";
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
	 * 家計簿の新規登録処理
	 * 
	 * @param addKakeiboForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Validated AddKakeiboForm addKakeiboForm, BindingResult result) {
		if (result.hasErrors()) {
			return "kakeibo/add";
		}

		Kakeibo kakeibo = new Kakeibo();

		// 決済日時を変換・セット(LocalDate型)
		LocalDate settlementDate = addKakeiboForm.getSettlementDate();
		kakeibo.setPaymentDate(settlementDate);

		// フォームの値をドメインにコピー
		BeanUtils.copyProperties(addKakeiboForm, kakeibo);

		// 支出金額と収入金額を変換してセット
		Integer expenditureAmount = Integer.parseInt(addKakeiboForm.getExpenditureAmount());
		Integer incomeAmount = Integer.parseInt(addKakeiboForm.getIncomeAmount());
		kakeibo.setExpenditureAmount(expenditureAmount);
		kakeibo.setIncomeAmount(incomeAmount);

		// 新規登録処理
		kakeiboService.save(kakeibo);

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
	 * 年別・月別集計画面を表示する
	 * 
	 * @return
	 */
	@GetMapping("/kakeiboByYearAndMonth")
	public String getKakeiboByYearAndMonth() {
		return "kakeibo/kakeiboByYearAndMonth";
	}

	/**
	 * 年別・月別集計結果を取得する
	 * @param searchKakeiboForm
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/kakeiboByYearAndMonth")
	public String postKakeiboByYearAndMonth(@Validated SearchKakeiboForm searchKakeiboForm, BindingResult result, Model model) {
		
		// 入力値エラーの際は集計ページを表示する
		if (result.hasErrors()) {
			return "kakeibo/kakeiboByYearAndMonth";
		}

		// リストが返ってきた時、kakeiboListに格納
		model.addAttribute("kakeiboList", kakeiboService.findKakeiboByYearAndMonth(searchKakeiboForm.getYear(), searchKakeiboForm.getMonth()));
		// リストがnullの時、messageに格納
		model.addAttribute("message", "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
		// 収支計算結果の取得
		model.addAttribute("result", kakeiboService.monthlyBalanceCalculate(searchKakeiboForm.getYear(), searchKakeiboForm.getMonth()));


		return "kakeibo/kakeiboByYearAndMonth";
	}
}
