"use strict";

window.onload = function () {
	var ctx = document.getElementById('bar-chart');
	
	var data = {
		labels: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    	datasets: [{
        label: '得点',
        data: [880, 740, 900, 520, 930, 880, 740, 900, 520, 930, 880, 740],
        backgroundColor: 'rgba(255, 100, 100, 1)'
    	}]
	};
	
	var options = {
		scales: {
			yAxes: [{
				ticks: {
                min: 300
            	}
        	}]
    	}
	};
	
	var ex_chart = new Chart(ctx, {
		type: 'bar',
    	data: data,
    	options: options
	});
}