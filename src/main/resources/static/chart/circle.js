"use strict";

window.onload = function () {
	// 費目の配列データ
	let expenseItemNames = document.getElementsByClassName('expenseItemNameList');
	// 費目ごとの合計金額の配列データ
	let totalByExpenseItems = document.getElementsByClassName('totalByExpenseItemList');
	// 費目の割合の配列データ
	let rates = document.getElementsByClassName('rateList');
	
	// 本月で集計された「費目名」を新しい配列を作成し格納する
	let newNames = []; // 新しい配列
	for (let expenseItemName of expenseItemNames) {
		newNames.push(expenseItemName.innerText);
	}
	
	// 本月で集計された費目名ごとの「割合」を新しい配列を作成し格納する
	let newRates = []; // 新しい配列
	for (let rate of rates) {
		// 取り出した値のテキストから%を排除
		rate = rate.innerText.replace("%", "");
		newRates.push(rate);
	}
	
	// グラフの色を判定し新しい配列に格納する関数
	let newColor = [];
	for (let expenseItemName of newNames) {
		switch(expenseItemName) {
			case "食費":
				newColor.push("#fa8072");
				break;
			case "日用品":
				newColor.push("#00ff7f");
				break;
			case "趣味・娯楽":
				newColor.push("#ff1493");
				break;
			case "交際費":
				newColor.push("#1e90ff");
				break;
			case "交通費":
				newColor.push("#9400d3");
				break;
			case "衣服":
				newColor.push("#FFFF00");
				break;
			case "美容":
				newColor.push("#ffd700");
				break;
			case "健康・医療":
				newColor.push("#ffa07a");
				break;
			case "自動車":
				newColor.push("#c0c0c0");
				break;
			case "教養・教育":
				newColor.push("#6495ed");
				break;
			case "水道・光熱費":
				newColor.push("#00bfff");
				break;
			case "通信費":
				newColor.push("#9932cc");
				break;
			case "住宅":
				newColor.push("#32cd32");
				break;
			case "税・社会保障":
				newColor.push("#ffebcd");
				break;
			case "保険":
				newColor.push("#ff69b4");
				break;
			case "その他":
				newColor.push("#fffacd");
				break;	
		}
		//if (expenseItemName == "食費") newColor.push("#fa8072");
		//if (expenseItemName == "日用品") newColor.push("#00ff7f");
		//if (expenseItemName == "趣味・娯楽") newColor.push("#ff1493");
		//if (expenseItemName == "交際費") newColor.push("#1e90ff");
		//if (expenseItemName == "交通費") newColor.push("#9400d3");
		//if (expenseItemName == "衣服") newColor.push("#FFFF00");
		//if (expenseItemName == "美容") newColor.push("#ffd700");
		//if (expenseItemName == "健康・医療") newColor.push("#ffa07a");
		//if (expenseItemName == "自動車") newColor.push("#c0c0c0");
		//if (expenseItemName == "教養・教育") newColor.push("#6495ed");
		//if (expenseItemName == "水道・光熱費") newColor.push("#00bfff");
		//if (expenseItemName == "通信費") newColor.push("#9932cc");
		//if (expenseItemName == "住宅") newColor.push("#32cd32");
		//if (expenseItemName == "税・社会保障") newColor.push("#ffebcd");
		//if (expenseItemName == "保険") newColor.push("#ff69b4");
		//if (expenseItemName == "その他") newColor.push("#fffacd");
	}
	
	// 円グラフの情報取得
	let context = document.querySelector("#balance_circle").getContext('2d')
    new Chart(context, {
	  // データの種類
      type: 'pie',
      // データの設定
      data: {
        labels: newNames,
        datasets: [{
         	backgroundColor: newColor,
          	data: newRates
        }]
      },
      // 円グラフのオプション設定
      options: {
		// 円グラフのタイトル
		title: {
        	display: true,
        	text: '収支内訳の割合'
      	},
        responsive: false,
        	legend: {
				// 凡例(ラベル)の非表示
　　				display: false,
　			},
		plugins: {
			// 円グラフ内のラベルの表示設定
        	labels: {
				// パーセンテージ表示
          		render: 'percentage',
          		// 文字の色
          		fontColor: 'black',
          		// 文字の大きさ
          		fontSize: 20
        	}
      	}
      }
    });
}