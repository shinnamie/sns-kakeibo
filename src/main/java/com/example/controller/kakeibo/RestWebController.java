package com.example.controller.kakeibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.kakeibo.CalendarEvent;
import com.example.domain.kakeibo.MonthlyBalanceCalculationResult;
import com.example.service.kakeibo.KakeiboService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/event")
public class RestWebController {

	@Autowired
	KakeiboService kakeiboService;

	@GetMapping(value = "/all")
	public String getDailySummaries() {

		String jsonMsg = null;

		try {
			// イベント格納用のListを準備
			List<CalendarEvent> events = new ArrayList<>();
			
			// 通年の集計結果を取得
			List<MonthlyBalanceCalculationResult> monthlyBalanceCalculationResultList = kakeiboService
					.monthlyBalanceCalculationResultList();
			
			// 支出用と収入用のリストにそれぞれ集約
			List<MonthlyBalanceCalculationResult> incomeAmountResultList = new ArrayList<>(); // 収入用リスト
			List<MonthlyBalanceCalculationResult> expenseAmountResultList = new ArrayList<>(); // 支出用リスト
			// 収入
			for (MonthlyBalanceCalculationResult result : monthlyBalanceCalculationResultList) {
				MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
				Integer totalIncome = result.getTotalIncomeAmount();
				if (totalIncome != 0) {// 収入金額が0以外のものをListに集約する(0円のものは排除)
					monthlyBalanceCalculationResult.setTotalIncomeAmount(totalIncome);
					monthlyBalanceCalculationResult.setDate(result.getDate());
				}
				incomeAmountResultList.add(monthlyBalanceCalculationResult);
			}
			// 支出
			for (MonthlyBalanceCalculationResult result : monthlyBalanceCalculationResultList) {
				MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
				Integer totalExpenditure = result.getTotalExpenditureAmount();
				if (totalExpenditure != 0) {// 支出金額が0以外のものをListに集約する(0円のものは排除)
					monthlyBalanceCalculationResult.setTotalExpenditureAmount(totalExpenditure);
					monthlyBalanceCalculationResult.setDate(result.getDate());
				}
				expenseAmountResultList.add(monthlyBalanceCalculationResult);
			}
			
			// 支出と収入で場合分けをしてeventにセット
			for (MonthlyBalanceCalculationResult income : incomeAmountResultList) {// 収入
				CalendarEvent event = new CalendarEvent();
				String date = income.getDate();
				event.setStart(date); // カレンダー日付にセット
				String title = "収入  " + income.getTotalIncomeAmount() + "円"; // 「支出 + 合計収入金額」がタイトル
				event.setTitle(title); // タイトルをセット
				events.add(event); // リストに追加(eventsにadd)
			}

			for (MonthlyBalanceCalculationResult expenditure : expenseAmountResultList) {// 支出
				CalendarEvent event = new CalendarEvent();
				String date = expenditure.getDate();
				event.setStart(date); // カレンダー日付にセット
				String title = "支出  " + expenditure.getTotalExpenditureAmount() + "円"; // 「支出 + 合計支出金額」がタイトル
				event.setTitle(title); // タイトルをセット
				events.add(event); // リストに追加(eventsにadd)
			}

			// FullCalendarにエンコード済み文字列を渡す
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);

		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}

		return jsonMsg;
	}

}
