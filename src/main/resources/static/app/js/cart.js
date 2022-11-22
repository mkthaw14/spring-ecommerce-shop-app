/**
 * 
 */

const storageKey = 'cart-item-data';


$(document).ready(function() {
	initializeLocalStorage();
	showCartCount();
	quantityBtn();
	addToCart();
	buyItem();
})

function initializeLocalStorage() {
	let cartItemList = localStorage.getItem(storageKey);

	if (!cartItemList) {
		cartItemList = [];
		localStorage.setItem(storageKey, JSON.stringify(cartItemList));
	}

	console.log("cartItemList : " + cartItemList);
	//{"id" : 1, "name" : product1},
	//{"id" : 2, "name" : product2}
	//productObj = {id: 1, name: product 2}
}

function showCartCount() {
	let cartItemList = JSON.parse(localStorage.getItem(storageKey));
	console.log("item count :" + cartItemList.length);

	$('span.cart-count').text(cartItemList.length);
}

function quantityBtn() {
	$("button.quantity-right-plus").on("click", function() {
		//alert("plus");
		let amount = Number($("#quantity").val());
		if (amount < 10) {
			amount += 1;
			$("#quantity").val(amount);
		}

	});

	$("button.quantity-left-minus").on("click", function() {
		//alert("minus");
		let amount = Number($("#quantity").val());
		if (amount > 1) {
			amount -= 1;
			$("#quantity").val(amount);
		}

	});
}

function buyItem()
{
		$('a.buy-now').on('click', function(e) {
		e.preventDefault();

		let itemQty = $("input.input-number").val();
		//alert(itemQty);
		if (itemQty == undefined) {
			//alert("undefined true");
			itemQty = 1;
			//alert("itemQty " + itemQty);
		}

		let item = {
			id: $(this).data('id'),
			name: $(this).data('name'),
			price: $(this).data('price'),
			qty: Number(itemQty)
		};

		//alert("Item : " + JSON.stringify(item));

		saveOrUpdateItem(item);
		
		window.location.href = "/cart/detail";
	})
}

function addToCart() {
	$('a.add-to-cart').on('click', function(e) {
		e.preventDefault();

		let itemQty = $("input.input-number").val();
		//alert(itemQty);
		if (itemQty == undefined) {
			//alert("undefined true");
			itemQty = 1;
			//alert("itemQty " + itemQty);
		}

		let item = {
			id: $(this).data('id'),
			name: $(this).data('name'),
			price: $(this).data('price'),
			qty: Number(itemQty)
		};

		//alert("Item : " + JSON.stringify(item));

		saveOrUpdateItem(item);
	})
}


function saveOrUpdateItem(item) {
	cartItemList = localStorage.getItem(storageKey);

	if (!cartItemList) {
		alert("No key for cart data")
		return;
	}

	//alert('cart exists')

	cartItemList = JSON.parse(cartItemList);
	let foundIndex = findItemById(cartItemList, item.id);

	//alert("item exists : " +  foundIndex)
	if (foundIndex == -1) {
		//alert('new item')
		cartItemList.push(item);
	}
	else {
		//alert('updating existing item')
		let existingItem = cartItemList[foundIndex];
		existingItem.qty += item.qty;
		cartItemList[foundIndex] = existingItem;
	}

	localStorage.setItem(storageKey, JSON.stringify(cartItemList));
	showCartCount();
}

function findItemById(cartItemList, id) {
	let foundIndex = -1;
	$.each(cartItemList, function(index, value) {
		if (value.id == id) {
			//alert("id found")
			foundIndex = index;
		}
	});

	return foundIndex;
}
