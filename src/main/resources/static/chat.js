var stompClient = null;

/**
 * 建立一个WebSocket连接，在建立WebSocket连接时，用户必须先输入用户名，然后才能建立连接
 */
function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // 订阅的地址
        stompClient.subscribe('/user/queue/chat', function (chat) {
            showGreeting(JSON.parse(chat.body));
        })
    })
}

function sendMsg() {
    // 发送路径
    stompClient.send("/app/chat", {}, JSON.stringify({'content': $("#content").val(), 'to': $("#to").val()}));
}


function showGreeting(message) {
    $("#chatsContent").append("<div>" + message.from + ":" + message.content + "</div>");
}

$(function () {
    connect();
    $("#send").click(function () {
        sendMsg();
    })
});