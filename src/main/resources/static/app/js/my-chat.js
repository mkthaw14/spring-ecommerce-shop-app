var current_chat_id = "";
var stompClient = null;
var chatRoomIDs = [];
var userName = "";

$(document).ready(function() {

	ajaxBeforeSendEventListener();
	getUserName();
	collectChatRoomIDs();
	connectAllChatRoom();

	
	$("a.link-sender").click(function() {
		
		//clean up all the messages in chatroom
		$(".chat-messages").text("");
		
		//assign a new chatId 
		let chatId = $(this).data("chat_id");
		current_chat_id = chatId;
		//alert(current_chat_id);
		
		//clear chatRoom notification
		let noti_Id = $("#chat-noti-" + current_chat_id);
		noti_Id.addClass("d-none ");
		noti_Id.removeClass("badge bg-success");
		noti_Id.text("0");
					
		//change the receiver name
		let receiverName = $(this).data("chat_name");
		$("#chatroom-receiver-name").text(receiverName);
		$("#chatroom-receiver-name-first-letter").text(receiverName[0].toUpperCase());
		
		loadOfflineMessage();

	});
	
	$("#btn-send-msg").click(function() { 
		sendMessage();	
	});
	
});

function loadOfflineMessage()
{

	let post_obj = {
		"chatId" : current_chat_id
	}
	
	console.log(current_chat_id);
	
	$.ajax({
		type : "POST",
		url : "/chat/load-offline-msg",
		async : false,
		data : post_obj,
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

function collectChatRoomIDs()
{
	let chatRooms = $("div.customer-support-content a");
	
	for(let i = 0; i < chatRooms.length; i++)
	{
		chatRoomIDs.push($(chatRooms[i]).attr("data-chat_id"));
		//console.log(chatRoomIDs[i]);
	}

}

function connectAllChatRoom()
{
	let socket = new SockJS("/customer-support-websocket");
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, function(frame) {
		for(let i = 0; i < chatRoomIDs.length; i++)
		{
			let url = "/user/" + chatRoomIDs[i] + "/customer-private-chat-room";
			//console.log(url);
			stompClient.subscribe(url, function(message) {
				if(current_chat_id == chatRoomIDs[i])
				{
					//alert(message);
					showMessage(message.body);
					updateRealTimeMessages(message.body);
				}
				else
				{
					let noti_Id = $("#chat-noti-" + chatRoomIDs[i]);
					let numOfNoti = Number(noti_Id.text());
					noti_Id.text(++numOfNoti);
					noti_Id.addClass("badge bg-success");
					noti_Id.removeClass("d-none");
					alert("A new message has been sent to you");
				}

			});
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

function sendMessage()
{
	if($("#msg-input").val() == "")
		return;
		
	let chatRoom = {
		"id" : current_chat_id
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

function findChatId(chatId)
{
	for(let i = 0; i < chatRoomIDs.length; i++)
	{
		if(chatId == chatRoomIDs[i])
		{
			alert("ID found " + chatRoomIDs[i]);
			return chatRoomIDs[i];
		}
	}
}

function ajaxBeforeSendEventListener() {
	//alert('Befor send');
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
}