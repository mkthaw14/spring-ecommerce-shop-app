var chatId = "";
var userName = "";
var stompClient = null;

$(document).ready(function() {	
	$("#btn-send-msg").click(function() { 
		//alert("Click");
		sendMessage();	
	});
		
	$("div.customer-support-content a.link-sender").click(function() {
		connectOrSwitchChatRoom(this);
	});
	
	ajaxBeforeSendEventListener();
});

function connectOrSwitchChatRoom(link)
{
	//disconnect from current chat room
    disconnect();
    
	//clear current chat messages
	$(".chat-messages").html("");
    
	//get a new chatId
	chatId = $(link).data("chat_id");
	//alert($(link).data("chat_id"));
	
	//change the receiver name
	let receiverName = $(link).data("chat_name");
	$("#chatroom-receiver-name").text(receiverName);
	$("#chatroom-receiver-name-first-letter").text(receiverName[0].toUpperCase());
	//connect to chat room
	connect();
}


function showMessage(message)
{
	console.log(JSON.parse(message));
	message = JSON.parse(message);
	
	//alert(message.senderName + " , " + message.chatRoom.id);
	//alert("userName : " + userName);
	//alert(userName == message.senderName);
	//alert("message.senderName : " + message.senderName);
	let msgDirection = userName == message.senderName ? "right" : "left";
	let bgColor = userName == message.senderName ? "bg-primary" : "bg-dark";
	let msgSenderName = userName == message.senderName ? "You" : message.senderName;
	let name = message.senderName[0];
	
	let newMsg = `
		<div class="chat-message-${msgDirection} pb-4">
			<div>
				<div class="rounded-circle mr-1 ${bgColor}
				d-flex justify-content-center align-items-center text-white" 
				style="width: 40px; height: 40px;">${name.toUpperCase()}</div>
				<div class="text-muted small text-nowrap mt-2">2:33 am</div>
			</div>
			<div class="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
				<div class="font-weight-bold mb-1">${msgSenderName}</div>
				${message.content}
			</div>
		</div>
	`; 
	
	$(".chat-messages").append(newMsg);
	$("#msg-input").val('');
}

function connect()
{
	getUserName();
	loadOfflineMessage();	
}

function getUserName()
{
	$.ajax({
		type : "POST",
		url : "/chat/get-customer_support_operator_name",
		async : false,
		success : function(data) {
			console.log(data);
			userName = data;
		},
		error : function(err) {
			console.log(err);
		}
	});
}

function loadOfflineMessage()
{

	let post_obj = {
		"chatId" : chatId
	}
	
	console.log(chatId);
	
	$.ajax({
		type : "POST",
		url : "/chat/load-offline-msg",
		async : false,
		data : post_obj,
		success : function(messages) {
			if(messages.length > 0)
			{
				messages.forEach(function(msg) {
					
					showMessage(JSON.stringify(msg));
				})
			}
				
			console.log("offine-msg : " + JSON.stringify(messages));
			connectToChatRoom();
		},
		error : function(err) {
			console.log(JSON.stringify(err));
		}
	});
}


function connectToChatRoom()
{
	var socket = new SockJS("/customer-support-websocket");
	stompClient = Stomp.over(socket);
	
	stompClient.connect({} , function(frame) {
		console.log(frame);
		let url = "/user/" + chatId + "/customer-private-chat-room";
		stompClient.subscribe(url, function(message) {
			console.log("msg : " + message);
			showMessage(message.body);
		})
	});
}

function disconnect()
{
	if(stompClient != null)
	{
		stompClient.disconnect();
	}
}

function sendMessage()
{
	if($("#msg-input").val() == "")
		return;
		
	let chatRoom = {
		"id" : chatId
	}
	
	let post_obj = {
		"senderName" : null,
		"content" :  $("#msg-input").val(),
		"chatRoom" : chatRoom
	}
	// chatRoomUrl "/app/private-msg-to-site-admin"
	stompClient.send("/app/private-msg-to-customer", {}, JSON.stringify(post_obj));
	console.log(JSON.stringify(post_obj));
}

function ajaxBeforeSendEventListener() {
	//alert('Befor send');
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
}