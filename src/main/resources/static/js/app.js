var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
			console.log(greeting.body);
            //showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/user/queue/chat', function (greeting) {
			console.log(' from chat : ' + greeting.body);
            //showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
//    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/hello", {}, 'lol');
}

function sendToChat(msg) {
//    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/chat", {}, msg);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});