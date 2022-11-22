/**
 * 
 */

 
 $(document).ready(function() {
	 initializeLocalStorage();
	 showCartCount();
	 showCartDetail();
	 updateSubTotal();
	 removeItemBtn();
	 handleCheckOutBtn();
	 //alert("second js file")
 });
 
 function showCartDetail()
 {

	 let cartListItem = localStorage.getItem(storageKey);
	 
	 if(cartListItem != null)
	 {
		 cartListItem = JSON.parse(cartListItem);
		 //alert('parsed cart list : ' + cartListItem);
		 drawProductTable(cartListItem);
	 }
	 
	 calculateCartTotals();

	 //ActivateOrDisableCheckoutButton(cartListItem);
	 //alert('cart list : ' + cartListItem);
 }
 
 function drawProductTable(cartListItem)
 {
	 let rows = "";
	 $.each(cartListItem, function(index, value) {
		 let subtotal = value.price * value.qty;
		 rows += `
			 <tr class="text-center ">
					<td class="product-remove">
						<a href="" class="remove-item-btn" data-id="${value.id}">
						<span
							class="ion-ios-close ">
							</span></a></td>


					<td class="product-name">
						<h3>${value.name}</h3>

					</td>

					<td class="price"> ${'Ks. ' + value.price}</td>

					<td class="quantity">
						<div class="input-group mb-3">
							<input type="number" name="quantity"
								class="quantity form-control 
								input-number update-quantity" 
								value="${value.qty}" min="1"
								max="100" data-id = ${value.id} >
						</div>
					</td>

					<td class="text-dark sub-total" data-id="${value.id}">${'Ks. ' + subtotal}</td>
				</tr>
			 `;
	 })

	 $('tbody.product-row').html(rows);
 }
 
 function updateSubTotal()
 {
	 $('tbody.product-row').on('change', 'input.update-quantity', function () {
		 let id = $(this).data('id');
		 let qty = $(this).val();
		 
		 let cartItemList = localStorage.getItem(storageKey);
		 if(cartItemList != null)
		 {
			 cartItemList = JSON.parse(cartItemList);
			 let foundIndex = findItemById(cartItemList, id);
			 let itemToUpdate = cartItemList[foundIndex];
			 itemToUpdate.qty = qty;
			 cartItemList[foundIndex] = itemToUpdate;
			 localStorage.setItem(storageKey, JSON.stringify(cartItemList));
			 showCartDetail();
		 }
		 
	 });
	 
 }
 
 function showProductTotal(productTotal)
 {
	$('span.total-product').text('Ks. ' + productTotal);
 }
 
 function calculateProductTotal()
 {
	 let cartItem = localStorage.getItem(storageKey);
	 
	 if(cartItem != null)
	 {
		 cartItem = JSON.parse(cartItem);
		 let total = 0;
		 $.each(cartItem, function(index, value) {
			 total += value.price * value.qty;
			  
		 });
		 return total;
	 }	
	 
	 return 0;
 }
 
 
 function calculateCartTotals()
 {
	 let productTotal =  calculateProductTotal();
	 let discount = 0; 
	 let delivery = 0;
	 let grandTotal = calculateGrandTotal(productTotal, discount, delivery);
	 showProductTotal(productTotal);
	 showGrandTotal(grandTotal);
 }
 
 function calculateGrandTotal(productTotal, discount, delivery)
 {
	 let grandTotal = productTotal;
	 grandTotal -= (discount + delivery);
	 return grandTotal;
 }
 
 function showGrandTotal(grandTotal)
 {
	 $('span.grand-total').text('Ks. ' + grandTotal);
 }
 
 function ActivateOrDisableCheckoutButton(cartItemList)
 {
	 if(cartItemList == null || cartItemList.length == 0)
	 {
		 $('a.checkout-btn').removeAttr('href');
	 }
	 
 }
 
 function handleCheckOutBtn()
 {
	 $('p.checkout-btn').on('click', function(event) {
		 let cartItemList = localStorage.getItem(storageKey);
		 if (cartItemList == null) 
		 {
			event.preventDefault();
			return;
		 }
		 
		 //alert('cart : not null ' + cartItemList);
		 cartItemList = JSON.parse(cartItemList);	 
		 if(cartItemList.length == 0)
		 {
			alert('length : ' + cartItemList.length);
			event.preventDefault();
			return;
		 }
		 
		 //alert('cart :  ' + cartItemList.length);
		 
		 const url = '/cart/checkout';
		 window.location.href = url;

	 });

 }
 
 function removeItemBtn()
 {
	 $('tbody.product-row').on('click', 'a.remove-item-btn', function(event) {
		event.preventDefault();
		let id = $(this).data('id');
		//alert('item to remove : ' + id);
		
		let cartItemList = localStorage.getItem(storageKey);
		if(cartItemList != null)
		{
			let cart_obj = JSON.parse(cartItemList);
			let foundIndex = findItemById(cart_obj, id);
			cart_obj.splice(foundIndex, 1);
			localStorage.setItem(storageKey, JSON.stringify(cart_obj));
			//alert('updated length : ' + JSON.stringify(cart_obj))
			
		    showCartCount();
	 		showCartDetail();
		} 
	 });
 }

 
 
 