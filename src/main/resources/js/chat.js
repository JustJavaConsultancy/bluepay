document.addEventListener("DOMContentLoaded", function () {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    let layoutDiv = document.getElementById("layoutDiv");
    const chatGroup = layoutDiv.getAttribute("chat-group");


    const badge = document.getElementById("notificationBadge");
    if (badge && parseInt(badge.textContent, 10) === 0) {
        badge.style.display = "none";
    }

    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/group/'+chatGroup, function (message) {
            var chatMessage = JSON.parse(message.body);
            alert(' A message arrives=='+chatMessage)
            pushMail(chatMessage);
            console.log(' This particular one is from the server...'
                + chatMessage.sender + ": " + chatMessage.content);
        });
    });

    function sendMessage(groupId, sender, content) {
        var message = { groupId: groupId, sender: sender, content: content };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));
    }

    function pushMail(chatMessage) {
        let badge = document.getElementById("notificationBadge");
        let list = document.getElementById("notificationList");

        if (!badge || !list) return;

        let currentCount = parseInt(badge.textContent || "0", 10);
        let newCount = currentCount + 1;

        badge.textContent = newCount;
        badge.style.display = newCount > 0 ? "inline-block" : "none";

        let newMessage = document.createElement("a");
        newMessage.href = "/start-form/businessRegistrationSchema";
        newMessage.className = "list-group-item list-group-item-action";
        newMessage.textContent = chatMessage.content;
        list.appendChild(newMessage);
    }
});
