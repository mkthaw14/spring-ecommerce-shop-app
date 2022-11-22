/**
 * 
 */

$(document).ready(function() {
	getOrderSales();
})


function getOrderSales() {

	$.ajax({

		url: "/api/order/daily-sales",
		type: 'GET',
		success: function(data) {
			let dataForChart = data;
			var dataset = myLineChart.data.datasets[0];
			var chart_data = Object.keys(dataset)[12];
			let sales = [];
			let dates = [];

			for (var key in dataForChart) {
				dates.push(key);
				sales.push(dataForChart[key]);
			}

			myLineChart.data.datasets[0][chart_data] = sales;
			myLineChart.data.labels = dates;
			myLineChart.update();

		}
	})
}