"use strict";

window.onload = function () {
	// 棒グラフを適応する箇所の情報格納(canvasタグ)
	var ctx = document.getElementById('bar-chart');
	
	// SQLで集計したデータの存在する日付を配列で取得
	let dates = document.getElementsByClassName('date');
	// 月毎の合計支出を配列で取得
	let totalExpenditures = document.getElementsByClassName('totalExpenditureAmountList');
	// 月毎の合計収入を配列で取得
	let totalIncomes = document.getElementsByClassName('totalIncomeAmountList');
	
	// 年の取得
	let year = dates[0].innerText.substr(0, 4);
	// 年月の配列を準備
	let monthsOfYear = [];
	for (let i = 1; i <= 12; i++) {
		let month;
		if (i < 10) {
			month = "0" + i
		} else {
			month = i
		}
		monthsOfYear.push(year + "/" + month);
	}
	
	// SQLの集計から得られた年月データを新しい配列を作成し格納する
	let newDates = []
	for (let date of dates) {
		newDates.push(date.innerText);
	}
	
	// 月毎の合計支出を新しい配列を作成し(カンマ(,)と円を省いて)格納する
	let newTotalExpenditures = [];
	for (let expenditure of totalExpenditures) {
		expenditure = expenditure.innerText.replace(",", "").replace("円", "");
		newTotalExpenditures.push(expenditure);
	}
	
	// 月毎の合計収入を新しい配列を作成し(カンマ(,)と円を省いて)格納する
	let newTotalIncomes = [];
	for (let income of totalIncomes) {
		income = income.innerText.replace(",", "").replace("円", "");
		newTotalIncomes.push(income);
	}
	
	// 引数に渡された日付(年月'yyyy/MM')と集計で得られた年月データが一致するかを判定する関数(合計支出)
	var equalusDateOfTotalExpenditureFunction = function(month) {
		for (let i = 0; i < newDates.length; i++) {
			if (month == newDates[i]) {
				return newTotalExpenditures[i];
			}
		}
	}
	
	// 引数に渡された日付(年月'yyyy/MM')と集計で得られた年月データが一致するかを判定する関数(合計収入)
	var equalusDateOfTotalIncomeFunction = function(month) {
		for (let i = 0; i < newDates.length; i++) {
			if (month == newDates[i]) {
				return newTotalIncomes[i];
			}
		}
	}
	
	// 棒グラフに用いる新たな合計支出のデータ群
	let totalExpenditureDatum = [];
	// データ1年分を回す
	for (let month of monthsOfYear) {
		let data = equalusDateOfTotalExpenditureFunction(month);
		if (data == null) {
			totalExpenditureDatum.push(0);
		} else {
			totalExpenditureDatum.push(data);	
		}
	}
	console.log(totalExpenditureDatum);
	
	// 棒グラフに用いる新たな合計収入のデータ群
	let totalIntcomeDatum = [];
	// データ1年分を回す
	for (let month of monthsOfYear) {
		let data = equalusDateOfTotalIncomeFunction(month);
		if (data == null) {
			totalIntcomeDatum.push(0);
		} else {
			totalIntcomeDatum.push(data);	
		}
	}
	console.log(totalIntcomeDatum);

	
	
	// データ情報
	var data = {
		labels: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    	datasets: [{
        	label: '支出',
        	data: totalExpenditureDatum,
        	backgroundColor: 'rgba(255, 100, 100, 1)'
    	},
    	{
        	label: '収入',
        	data: totalIntcomeDatum,
        	backgroundColor: 'rgba(100, 100, 255, 1)'
    	}
    	]
	};
	
	// グラフのオプション設定
	var options = {
		scales: {
			xAxes: [{
            	scaleLabel: {
                	display: true,
                	labelString: '月'
            	}
        	}],
        	yAxes: [{
            	ticks: {
                	min: 0,
                	userCallback: function(tick) {
                    	return tick.toString() + '円';
                	}
            	},
            	scaleLabel: {
                	display: true,
                	labelString: '金額'
            	}
        	}]
    	},
    	title: {
        	display: true,
        	text: '支出と収入の推移'
    	}
	};
	
	// chart.jsのフォーマットに適応
	var ex_chart = new Chart(ctx, {
		type: 'bar',
    	data: data,
    	options: options
	});
}