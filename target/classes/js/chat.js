
/*var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);*/
var notification = 0;
const badge = document.querySelector('.badge');
badge.innerText = notification;

/*
stompClient.connect({}, function () {
    stompClient.subscribe('/topic/group/compliance', function (message) {
        var chatMessage = JSON.parse(message.body);
        pushMail(chatMessage);
        console.log(' This particular one is from the server...'
            + chatMessage.sender + ": " + chatMessage.content);
    });
});
*/

// Send a message to group 123
/*function sendMessage(groupId, sender, content) {
    var message = { groupId: groupId, sender: sender, content: content };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
}*/
function pushMail(chatMessage) {
    let badge = document.getElementById("notificationBadge");
    let list = document.getElementById("notificationList");

    let currentCount = parseInt(badge.textContent, 10);
    let newCount = currentCount + 1;

    badge.textContent = newCount;

    if (newCount > 0) {
        badge.style.display = "inline-block";

    } else {
        badge.style.display = "none";
    }

    let newMessage = document.createElement("a");
    newMessage.href = "/start-form/businessRegistrationSchema";
    newMessage.className = "list-group-item list-group-item-action";
    newMessage.textContent = chatMessage.content;
    list.appendChild(newMessage);
}
document.addEventListener("DOMContentLoaded", function () {
    let badge = document.getElementById("notificationBadge");
    if (parseInt(badge.textContent, 10) === 0) {
        badge.style.display = "none";
    }
});