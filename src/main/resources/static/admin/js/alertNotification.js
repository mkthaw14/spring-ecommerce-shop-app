//notifyOperation();

$(document).ready(function() {
	notifyOperation();
});

var second = 0;
var interval = null;
function notifyOperation() {
	if($("div.operationNotifaction").is(":visible"))
	{
		//alert("interval");
		interval = setInterval(incrementSecond, 1000);
	}
}

function incrementSecond()
{
	//$("span.secondCounter").text(second);
    second += 1;
	
	console.log(interval);
	if(second >= 2)
	{	
		$("div.operationNotifaction").slideUp();
		clearInterval(interval);
		console.log(interval);
	}
}
