var stompClient = null;

var connect = function(cb) {
	var socket = new SockJS('/chat/ws-gateway');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/app/chat/' + chatId, reciveMessages);
		stompClient.subscribe('/chat/' + chatId, reciveMessages);
	});
};

var reciveMessages = function(msg){
	JSON.parse(msg.body).forEach(m => {
		var authorId = m.author.id;
		addMessage({in:authorId == companionId, text: m.value});
	});
}

var sendMessage = function(msg) {
	var body = '{"value":"' + msg + '"}';
	$.ajax({
		url: "/chat/api/v1/chats/" + chatId + "/messages",
		type: "POST",
		data: body,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(d) {
			console.log(d);
		}
	})
};

var addMessage = function(msg) {
	var containerClass = "message-container-" + (msg.in ? "in" : "out");
	var contentClass = "message-content-" + (msg.in ? "in" : "out");
	var html = "<div class='" + containerClass + "'><div class='" + contentClass + "'>" + msg.text + "</div></div>";
	$("#messages").append(html);
};

var init = function() {
	window.companionId = document.location.href.substr(document.location.href.indexOf('private') + 8);
	window.chatId = $("#text-field").attr('data-chat-id');
	$("#send-msg").click(function() {
		var field = $("#text-field")[0];
		if (field) {
			sendMessage(field.value)
		}
	});
	connect();
};
document.addEventListener("DOMContentLoaded", init);