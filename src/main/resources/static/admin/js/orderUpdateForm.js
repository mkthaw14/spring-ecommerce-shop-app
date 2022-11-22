$(document).ready(function() {
	//alert("Yess ")
	onOrderUpdateClick();
})

function submitOrderUpdate(order_status) {
	//alert("status " + order_status)
	$("#status-select").val(order_status);
	//alert("selected val " + $("#status-select").val());
	//alert("form val " + form);
	$("form.order-update-form").submit();
}

function onOrderUpdateClick() {
	$("a.order-delivered").on("click", function(event) {
		event.preventDefault();
		submitOrderUpdate($(this).data("status"));
	})

	$("a.order-received").on("click", function(event) {
		event.preventDefault();
		submitOrderUpdate($(this).data("status"));
	})
	
	$("a.order-cancel").on("click", function(event) {
		event.preventDefault();
		submitOrderUpdate($(this).data("status"));
	})
}
