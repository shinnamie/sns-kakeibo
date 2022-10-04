"use strict";

document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');
	var calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth', // カレンダーの種類
		events: { // カレンダーに表示するイベント
      		url : '/api/event/all' //ここにRestControllerを呼び出すurlを記載
    	}
	});
	calendar.render();
	
});


