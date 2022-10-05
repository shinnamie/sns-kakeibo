"use strict";

document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth', // カレンダーの種類
		events: { // カレンダーに表示するイベント
      		url : '/api/event/all' //ここにRestControllerを呼び出すurlを記載
    	},
    	dateClick: (e)=>{// 日付マスのクリックイベント
			console.log("dateClick:", e);
		},
		eventClick: (e)=>{// イベントのクリックイベント
			console.log("eventClick:", e.event.title);
		},
		eventDidMount: (info)=>{// カレンダーに配置された時のイベント
			//var col = info.el.attributes.col;
          	//console.log(col)//確認
          	
          	//event　で定義している　extendedPropsの中のstarttimesから　始まる時間の配列を受け取る
          	//let start = info.event.extendedProps.start;
          	//console.log('startTimes：' + start);
          	
          	console.log(info)

		}
	})
	calendar.render();	
});


