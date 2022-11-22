$(document).ready(function() {

	$("a.logout").on("click", function() {
		$("#f-logout").submit();
		alert('You have been log out')
	})
})