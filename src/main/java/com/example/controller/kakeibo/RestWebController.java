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
			
			// 年月をもとに集計結果を取得
			List<MonthlyBalanceCalculationResult> monthlyBalanceCalculationResultList = kakeiboService
					.monthlyBalanceCalculationResultList();
			
			// 支出用と収入用のリストにそれぞれ集める
			List<MonthlyBalanceCalculationResult> incomeAmountResultList = new ArrayList<>();
			List<MonthlyBalanceCalculationResult> expenseAmountResultList = new ArrayList<>();
			// 収入
			for (MonthlyBalanceCalculationResult result : monthlyBalanceCalculationResultList) {
				MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
				Integer totalIncome = result.getTotalIncomeAmount();
				if (totalIncome != 0) {
					monthlyBalanceCalculationResult.setTotalIncomeAmount(totalIncome);
					monthlyBalanceCalculationResult.setDate(result.getDate());
				}
				incomeAmountResultList.add(monthlyBalanceCalculationResult);
			}
			// 支出
			for (MonthlyBalanceCalculationResult result : monthlyBalanceCalculationResultList) {
				MonthlyBalanceCalculationResult monthlyBalanceCalculationResult = new MonthlyBalanceCalculationResult();
				Integer totalExpenditure = result.getTotalExpenditureAmount();
				if (totalExpenditure != 0) {
					monthlyBalanceCalculationResult.setTotalExpenditureAmount(totalExpenditure);
					monthlyBalanceCalculationResult.setDate(result.getDate());
				}
				expenseAmountResultList.add(monthlyBalanceCalculationResult);
			}
			
			// 支出と収入で場合分けをしてeventにセット
			// 収入
			for (MonthlyBalanceCalculationResult income : incomeAmountResultList) {
				CalendarEvent event = new CalendarEvent();
				String date = income.getDate();
				event.setStart(date);
				String title = "収入  " + income.getTotalIncomeAmount() + "円";
				event.setTitle(title);
				events.add(event);
			}
			// 支出
			for (MonthlyBalanceCalculationResult expenditure : expenseAmountResultList) {
				CalendarEvent event = new CalendarEvent();
				String date = expenditure.getDate();
				event.setStart(date);
				String title = "支出  " + expenditure.getTotalExpenditureAmount() + "円";
				event.setTitle(title);

				events.add(event);
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
