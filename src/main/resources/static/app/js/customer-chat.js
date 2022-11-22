var chatId = "";
var userName = "";
var stompClient = "";
var openMessages = false;

$(document).ready(function() {	
	$("#btn-send-msg").click(function() { 
		//alert("Click");
		sendMessage();	
	});
		
	$("#btn-open-messages").click(function() {
		openMessages = true;
		connect();
	});
	
	$("#btn-close-chat-message-area").click(function() {
		disconnect();
	});
	
	ajaxBeforeSendEventListener();
	getChatIDAndConnectToChatRoom();

});

function showMessage(message)
{
	console.log(JSON.parse(message));
	message = JSON.parse(message);
	
	//alert("userName : " + userName);
	//alert("message.senderName : " + message.senderName);
	let msgDirection = userName == message.senderName ? "right" : "left";
	let bgColor = userName == message.senderName ? "bg-dark" : "bg-primary";
	let msgSenderName = userName == message.senderName ? "You" : message.senderName;
	let name = message.senderName[0];
	let time = message.createdTime;
	let date = message.createdDate;
	
	let newMsg = `
		<div class="chat-message-${msgDirection} pb-4">
			<div>
				<div class="rounded-circle mr-1 ${bgColor}
				d-flex justify-content-center align-items-center text-white" 
				style="width: 40px; height: 40px;">${name.toUpperCase()}</div>
				<div class="text-muted small text-nowrap mt-2">${time}</div>
				<div class="text-muted small text-nowrap mt-2">${date}</div>
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
	//getChatID();
	loadOfflineMessage();	
}

function getChatIDAndConnectToChatRoom()
{
		jQuery.ajax({
		type : "POST",
		async : false,
		url : "/chat/get-chatId",
		success : function(data) {
			console.log(JSON.stringify(data));
			//alert(JSON.stringify(data));
		    chatId = data.chatId;
		    userName = data.userName;
		    connectToChatRoom();
		},
		error : function(err) {
			console.log(JSON.stringify(err));
		}
	});
}

function getChatID()
{
		jQuery.ajax({
		type : "POST",
		async : false,
		url : "/chat/get-chatId",
		success : function(data) {
			console.log(JSON.stringify(data));
			//alert(JSON.stringify(data));
		    chatId = data.chatId;
		    userName = data.userName;
		},
		error : function(err) {
			console.log(JSON.stringify(err));
		}
	});
}

function loadOfflineMessage()
{
	let post_obj = {
		"chatId" : chatId
	};
	
	jQuery.ajax({
		type : "POST",
		url : "/chat/load-offline-msg",
		data : post_obj,
		async : false,
		success : function(messages) {
			if(messages.length > 0)
			{
				let unread_msg = [];
				messages.forEach(function(msg) {
					
					if(msg.messageStatus == "unread" && msg.senderName != userName)
					{
						let unread_msg_obj = {
							"id" : msg.id,
							"chatId" : msg.chatRoom.id,
							"status" : "read"
						}
						unread_msg.push(unread_msg_obj);
						//alert(msg.chatRoom.id);
					}
					
					showMessage(JSON.stringify(msg));
				})
				
				updateMessageStatus(unread_msg);
			}
				
			console.log("offine-msg : " + JSON.stringify(messages));
			//connectToChatRoom();
		},
		error : function(err) {
			console.log(JSON.stringify(err));
		}
	});
}

function updateMessageStatus(unread_msg)
{
	if(unread_msg.length <= 0)
		return;

	let messages = unread_msg;
	messages = JSON.stringify({ "messages" : messages })
	
	//post_obj = JSON.stringify(post_obj);
	console.log(JSON.stringify(messages));
	$.ajax({
		type : "POST",
		url : "/update-msg",
		data : messages,
		contentType: 'application/json',
		success : function(data) {
			//alert("update : " + data);
			console.log(data);
		},
		error : function(err) {
			//alert("update failure");
			console.log(err);
		}
	});
}

function updateRealTimeMessages(message)
{

	message = JSON.parse(message);
	
	if(message.senderName == userName)
		return;
		
	console.log(message);
	let msg_obj = {
		"id" : message.id,
		"chatId" : message.chatRoom.id,
		"status" : message.messageStatus 
	}
	
	let messages = [];
	messages.push(msg_obj);
	updateMessageStatus(messages);
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
			
			if(! openMessages)
			{
				//alert(message);
			
				let noti_Id = $("#chat-noti");
				let numOfNoti = Number(noti_Id.text());
				noti_Id.text(++numOfNoti);
				alert("A new message has been sent to you");
			}
			else
			{
				showMessage(message.body);
				updateRealTimeMessages(message.body);
			}
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
	stompClient.send("/app/private-msg", {}, JSON.stringify(post_obj));
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