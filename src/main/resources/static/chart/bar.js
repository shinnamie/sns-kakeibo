"use strict";

window.onload = function () {
	var ctx = document.getElementById('bar-chart');
	
	var data = {
		labels: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    	datasets: [{
        	label: '支出',
        	data: [880, 740, 900, 520, 930, 880, 740, 900, 520, 930, 880, 740],
        	backgroundColor: 'rgba(255, 100, 100, 1)'
    	},
    	{
        	label: '収入',
        	data: [880, 740, 900, 520, 930, 880, 740, 900, 520, 930, 880, 740],
        	backgroundColor: 'rgba(100, 100, 255, 1)'
    	}
    	]
	};
	
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
	
	var ex_chart = new Chart(ctx, {
		type: 'bar',
    	data: data,
    	options: options
	});
}