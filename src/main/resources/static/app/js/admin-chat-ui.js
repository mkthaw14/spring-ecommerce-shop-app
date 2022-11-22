$(document).ready(function() {
	setUp();
	showChatBox();

});

function setUp()
{

	$("#chat").slideUp();
}

function showChatBox()
{
	$("div.customer-support-content a.link-sender").click(function(event) {

		//$(this).hide();
		event.preventDefault();
		//$(".chat-messages").html("");
		$("#chat").slideDown();
		console.log("click");
	});
	
	$("#btn-close-chat-message-area").click(function() {
		$("#btn-open-messages").show();
		//$("#map-area").attr("class", "col-12");
		$("#chat").slideUp();
	});
}

