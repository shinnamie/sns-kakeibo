"use strict";

window.onload = function () {
	var ctx = document.getElementById('bar-chart');
	
	var data = {
		labels: ["平成26年", "平成27年", "平成28年", "平成29年", "平成30年"],
    	datasets: [{
        label: '得点',
        data: [880, 740, 900, 520, 930],
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