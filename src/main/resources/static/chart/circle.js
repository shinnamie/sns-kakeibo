"use strict";

window.onload = function () {
	let context = document.querySelector("#balance_circle").getContext('2d')
    new Chart(context, {
      type: 'pie',
      data: {
        labels: ["食費", "日用品", "趣味・娯楽", "交際費", "交通費", "衣服", "美容", "健康・医療", "自動車", "教養・教育", "水道・光熱費", "通信費", "住宅", "税・社会保障", "保険", "その他"],
        datasets: [{
          backgroundColor: ["#fa8072", "#00ff7f", "#ff1493", "#1e90ff", "#9400d3", "#FFFF00", "#ffd700", "#ffa07a", "#c0c0c0", "#6495ed", "#00bfff", "#9932cc", "#32cd32", "#ffebcd", "#ff69b4", "#fffacd"],
          data: [6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6]
        }]
      },
      options: {
        responsive: false,
        plugins: {
　			legend: {
　　				display: false,
　			}
		}
      }
    });
}