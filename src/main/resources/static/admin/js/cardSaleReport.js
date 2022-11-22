$(document).ready(function() {
	//alert("Card Sales");
	getLatestMonthSale();
	getNumberOfProduct();
	getNumberOfCategories();
	getNumberOfOrderPending();
});



function getLatestMonthSale() {
	$.ajax({
		url: "/api/order/latest-monthly-sales",
		type: "GET",
		success: function(data) {
			let sale = data;
			//alert(JSON.stringify(sale));
			let key = Object.keys(sale);
			//alert(sale[key]);

			$("#latest-month-sales").text(sale[key] + " Ks");
		}
	})
}

function getNumberOfProduct() {
	$.ajax({
		url: "/api/product/number-of-products",
		type: "GET",
		success: function(data) {
			//alert(JSON.stringify(sale));
			let key = Object.keys(data);
			//alert(data[key]);

			$("#number-of-products").text(data[key]);
		}
	})

}

function getNumberOfCategories() {
	$.ajax({
		url: "/api/product/number-of-categories",
		type: "GET",
		success: function(data) {
			//alert(JSON.stringify(sale));
			let key = Object.keys(data);
			//alert(data[key]);

			$("#number-of-categories").text(data[key]);
		}
	})
}

function getNumberOfOrderPending(){
	$.ajax({
		url : "api/order/pending-order-count",
		type : "GET",
		success : function(data) {
			//alert(JSON.stringify(sale));
			let key = Object.keys(data);
			//alert(data[key]);

			$("#pending-orders-count").text(data[key]);
		}
	})
}
