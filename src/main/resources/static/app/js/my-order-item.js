$(document).ready(
	function() {
		$("div.my-order-content").on("click",
			"button.btn-collapse", function() {

				let id = $(this).data("id");
				let div_id = "#div-collapse" + id;
				let accordion = $("div.my-order-content").children("#accordion-" + $(this).data("id"));
				//console.log("click " + accordion.attr("id"));
				$(div_id).attr("data-parent", "#" + accordion.attr("id"));


				var data = [];
				if (!$(div_id).hasClass("show")) {
					//console.log("True");
					$.get("/shop/order/order-item/" + id, function(response) {
						data = response;
						//console.log("data " + data);
						console.log("res " + JSON.stringify(response[0]));

					}).done(function() {

						let table = "";
						//console.log("key " + data);
						$.each(data, function(key, value) {
							//console.log("d " + value.product.name);

							table += `
										<tr>
											<td >${value.product.id}</td>
											<td >${value.product.name}</td>
											<td >${value.quantity}</td>
										</tr>
					
							`;
							
							
						})
						

						$( "div.my-order-content #table-" + id + " tbody").html(table);



						console.log( $( "div.my-order-content #table-" + id + " tbody").get(0) );
					});


				}

				//console.log("div : " + $("div.my-order-content").children(".table-body").is(":visible"));
			});
	});
	
	
	$.fn.log = function() {
  		console.log.apply(console, this);
  		return this;
	};