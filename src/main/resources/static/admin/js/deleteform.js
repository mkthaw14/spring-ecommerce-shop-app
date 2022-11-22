$(document).ready(function() {

	deleteRow();
})

function deleteRow() {
	$("a.delete-btn").on("click", function(event) {
		event.preventDefault();
		let id = $(this).data("id");
		//alert("delete btn " + id);
		
		let form = $("form.delete-form input[name=id]").attr("value", id);
		//alert(id + " form val " + form);
	});
	
	$("a.confirm-delete-btn").on("click", function(event){
		event.preventDefault();
		$("form.delete-form").submit();
	});
}


function deleteRow2() {
	$("a.delete-btn").on(
		"click",
		function(event) {
			event.preventDefault();
			let id = $(this).data("id");

			let form = $("form.delete-form input[name=id]").attr("value", id)
			alert(id + " form val " + form);
			$("form.delete-form").submit();
		})
}