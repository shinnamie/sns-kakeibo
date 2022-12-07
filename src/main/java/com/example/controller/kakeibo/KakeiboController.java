package com.example.controller.kakeibo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
import com.example.form.kakeibo.AddKakeiboForm;
import com.example.form.kakeibo.EditKakeiboForm;
import com.example.form.kakeibo.SearchKakeiboForm;
import com.example.service.kakeibo.KakeiboService;
import com.example.service.user.LoginService;

@Controller
@RequestMapping("/kakeibo")
public class KakeiboController {

	@Autowired
	KakeiboService kakeiboService;

	@Autowired
	LoginService loginService;

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
		return "kakeibo/kakeiboList";
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

		return getBreakDown(date, model);
	}

	/**
	 * 収支内訳を算出する
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "{date}", method = RequestMethod.GET)
	public String getBreakDown(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ModelAttribute @PathVariable("date") LocalDate date,
			Model model) {

		// 月初日と月末日を取得する
		String firstDateAndLastDate = kakeiboService.getFirstDayAndLastDay(date);
		model.addAttribute("firstDateAndLastDate", firstDateAndLastDate);

		// 年と月をString型で取得
		String yearAndMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));

		// 選択されている年月の費目別の支出・収入を算出する
		List<Kakeibo> kakeiboList = kakeiboService.totalByIncomeAndExpenditureBreakdown(yearAndMonth);
		if (kakeiboList == null || kakeiboList.size() == 0) { // データが存在しない場合
			model.addAttribute("message", "該当月のデータが存在しません。");
			return "kakeibo/breakdown-income-balance";
		} else {

			// 家計簿ListからMapに変換
			Map<String, Integer> kakeiboMap = kakeiboService.findBreakdown(kakeiboList);

			// 総収入・総支出・収支のMapを取得
			Map<String, Integer> totalAmountMap = kakeiboService.totalAmountMap(kakeiboMap);

			// Map内の費目別の割合を計算 Map<費目名,割合>
			Map<String, Double> percentageMap = kakeiboService
					.calculatePercentage(kakeiboService.integerToDouble(kakeiboMap));

			// 費目の総支出を格納したMapを呼び出す Map<費目名,支出額>
			Map<String, Integer> itemExpenceMap = kakeiboService.itemExpenseMap(kakeiboMap);

			// スコープに格納
			model.addAttribute("totalAmountMap", totalAmountMap);
			model.addAttribute("itemExpenceMap", itemExpenceMap);
			model.addAttribute("rateItemMap", percentageMap);
		}

		return "kakeibo/breakdown-income-balance";
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
	public String postKakeiboByYearAndMonth(@Validated SearchKakeiboForm searchKakeiboForm, BindingResult result,
			Model model) {

		// 入力値エラーの際は集計ページを表示する
		if (result.hasErrors()) {
			return "kakeibo/kakeiboByYearAndMonth";
		}

		// リストが返ってきた時、kakeiboListに格納
		model.addAttribute("kakeiboList",
				kakeiboService.findKakeiboByYearAndMonth(searchKakeiboForm.getYear(), searchKakeiboForm.getMonth()));
		// リストがnullの時、messageに格納
		model.addAttribute("message", "ご入力頂いた年月のデータは存在しません（年の指定は必須です）");
		// 収支計算結果の取得
		model.addAttribute("result",
				kakeiboService.monthlyBalanceCalculate(searchKakeiboForm.getYear(), searchKakeiboForm.getMonth()));

		return "kakeibo/kakeiboByYearAndMonth";
	}
}
