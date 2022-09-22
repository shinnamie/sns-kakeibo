package com.example.controller.kakeibo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.kakeibo.DeletedKakeibo;
import com.example.domain.kakeibo.Kakeibo;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.form.kakeibo.AddKakeiboForm;
import com.example.form.kakeibo.EditKakeiboForm;
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
	
	/**
	 * 家計簿登録画面を表示(元のデータをデフォルト表示)
	 * 
	 * @return
	 */
	@GetMapping(value = "/addKakeibo")
	public String addKakeibo(AddKakeiboForm addKakeiboForm) {
		return "kakeibo/add";
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

	@GetMapping(value = "/monthly-aggregation")
	public String monthlyAggregation() {
		return "kakeibo/month";
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
		kakeibo.setSettlementDate(settlementDate);

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

		// 現在の日時を取得(削除日時)
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// 値をセット
		deletedKakeibo.setKakeiboId(id);
		deletedKakeibo.setDeleteAt(timestamp);

		// 削除フラグをtrueにする
		kakeibo.setId(id);
		kakeibo.setDeleted(true);
		kakeiboService.updateIsDelete(kakeibo);

		// 論理削除
		kakeiboService.delete(deletedKakeibo);

		return "redirect:/kakeibo/list";
	}

	/**
	 * 月別集計結果を取得する
	 * 
	 * @param year
	 * @param month
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/aggregated-month", method = RequestMethod.POST)
	public String aggregatedByMonth(String year, String month, Model model) {
		// 年月のパラメーターを元に検索、結果を取得
		List<Kakeibo> kakeiboMonthList = kakeiboService.findByYearAndMonth(year, month);

		// 収支計算
		MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = kakeiboService
				.monthlyBalanceCalculate(year, month);

		// それぞれの結果をスコープに格納
		if (kakeiboMonthList == null || kakeiboMonthList.size() == 0) {
			model.addAttribute("message", "ご入力頂いた年月のデータは存在しません。");
		} else {
			model.addAttribute("kakeiboMonthList", kakeiboMonthList);
			model.addAttribute("monthlyBalanceCalculationResult", monthlyBalanceCalculationResult);
		}

		return "kakeibo/month";
	}
}
