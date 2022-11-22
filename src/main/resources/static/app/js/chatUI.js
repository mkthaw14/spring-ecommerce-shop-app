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
	$("#btn-open-messages").click(function() {
		$("#chat").slideDown();
		$(this).hide();
	});
	
	$("#btn-close-chat-message-area").click(function() {
		$("#btn-open-messages").show();
		//$("#map-area").attr("class", "col-12");
		$("#chat").slideUp();
	});
}

