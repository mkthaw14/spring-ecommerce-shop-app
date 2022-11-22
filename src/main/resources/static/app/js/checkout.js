/**
 * 
 */
$(document).ready(function() {
	showCartCount(); // from cart.js
	calculateCartTotals(); // from cartdetail.js
	ajaxBeforeSendEventListener();
	checkout();
});

function checkout() {
	$('div.cart-detail').on('click', 'a.place-order-btn', function(event) {

		event.preventDefault();

		let receiverInfo = {
			name: $('#r-name').val(),
			email: $('#r-email').val(),
			phone: $('#r-phone').val(),
			address: $('#r-address').val()
		}

		let cartItem = localStorage.getItem(storageKey);
		if (cartItem != null) {
			//alert('cartItem : ' + cartItem)
			cartItem = JSON.parse(cartItem);

			let orderInfo = {
				receiver: receiverInfo,
				itemDataList: cartItem
			}

			//Remember controller action method must return json result in order to make this ajax function works
			$.ajax({
				type: 'POST',
				contentType: 'application/json',
				dataType: 'json',
				data: JSON.stringify(orderInfo),
				url: '/cart/place-order',

				success: function(result) {
					if (result == "")
						alert('Success. But something is wrong ' + JSON.stringify(result))
					else {
						localStorage.clear();
						alert('save operation success')
						window.location.href = "/";
					}
				},
				error: function(result) {
					alert("Something is wrong" + JSON.stringify(result))
				}
			})
		}
	});
}

function ajaxBeforeSendEventListener() {
	//alert('Befor send');
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
}