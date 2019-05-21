var connectButton = document.getElementsByClassName("connectButton")[0];
var sendButtonArea = document.getElementsByClassName("send-button-area")[0];
var contentBody = document.getElementsByClassName("content-body")[0];
var onlineCountSpan = document.getElementById("onlineCount");
var loginButton = document.querySelector("#login-button");
var socket = null;
var refreshCode = 100;
var messageCode = 200;
var messageSendingSuccessCode = 1;
var cookieLoginNameKey = "nickName";
var registerSuccessCode = 100;
var nickName = "";
window.onload = function (ev) {
    console.log("event", "window onload");
    if (window.WebSocket) {
        if (socket == null) {
            socket = new WebSocket("wss://120.77.222.242:9990");
        }
    } else {
        alert("您的浏览器不支持socket");
        return;
    }
    socket.onopen = function (e) {
        // console.log("onopen", e);
        // console.log("open socket", socket);
    };

    socket.onmessage = function (e) {
        // console.log("message", JSON.parse(e.data));
        var receiveData = JSON.parse(e.data);
        if (receiveData["methodCode"] === refreshCode) {
            refreshOnlineCount(receiveData["onlineCount"]);
        }else if (receiveData["methodCode"] === messageCode) {
            receiveMessage(JSON.parse(receiveData["sendingMessage"])["nickName"],JSON.parse(receiveData["sendingMessage"])["message"]);
        }else if (receiveData["methodCode"] === messageSendingSuccessCode) {
            sendingMessageSuccess();
        }
    };

    socket.onerror = function (e) {
        console.log("error", e);
    };

    socket.onclose = function (e) {
        console.log("close", e);
        console.log("closed socket", socket);
        setTimeout(function () {
            if (socket != null && socket.readyState === 3) {
                socket = new WebSocket("wss://120.77.222.242:9990");
            }
        }, 300);
        // alert("连接断开");
    };

    // console.log("cookie", document.cookie.search(cookieLoginNameKey));
    if (document.cookie.search(cookieLoginNameKey) === -1) {
        document.querySelector(".signWindow").style.display = "block";
    }else {
        var params = document.cookie.split(";");
        for (var i in params) {
            if (params[i].search(cookieLoginNameKey) !== -1) {
                nickName = params[i].split("=")[1];
            }
        }
    }

};


window.addEventListener("keypress", function (ev) {
    // console.log("keypress", ev.key);
    if (ev.key === "Enter") {
        doSendingMessage();
    }
});


loginButton.addEventListener("click", function (e) {
    var nickName = document.querySelector("#name-input").value;
    if (nickName == null || nickName === "") {
        return;
    }
    nickName = inputFilter(nickName);
    doHttpGet("?nickName=" + nickName);
});

sendButtonArea.addEventListener("click", function (evt) {
    doSendingMessage()
});

function refreshOnlineCount(count) {
    onlineCountSpan.innerHTML = count;
}

function receiveMessage(nickName,message) {
    var newMessageItem = document.createElement("div");
    newMessageItem.innerHTML = "<div class=\"message-content-nickName you\">" + nickName + "</div>\n" +
        "                    <div class=\"message-content you\">\n" + message + "</div>";
    contentBody.appendChild(newMessageItem);
    newMessageItem.classList.add("message-content-father");
    contentBody.scrollTop = contentBody.scrollHeight;
}

function doSendingMessage() {
    var input = document.getElementsByClassName("input-field")[0];
    if (input.value.trim() === "") {
        return;
    }
    socket.send(createSendingMessage(input.value));
}

function sendingMessageSuccess() {
    var input = document.getElementsByClassName("input-field")[0];
    var newMessageItem = document.createElement("div");

    newMessageItem.innerHTML = "<div class=\"message-content mine\">\n" + input.value + "</div>";

    contentBody.appendChild(newMessageItem);
    newMessageItem.classList.add("message-content-father");
    // newMessageItem.innerHTML = input.value;
    input.value = "";
    contentBody.scrollTop = contentBody.scrollHeight;
}

function createSendingMessage(message) {
    //TODO
    var filterMessage = inputFilter(message);
    var sendingMessage = {
        nickName: nickName,
        message: filterMessage
    };
    // sendingMessage.nickname = nickName;
    // sendingMessage.message = message;
    console.log("sendingMessage", sendingMessage);
    return JSON.stringify(sendingMessage);

}

function doHttpGet(getParams) {
    console.log("params", getParams);
    $.ajax({
        url: "/chat/register" + getParams,
        dataType: "json",
        type: "GET",
        complete:function (res) {
            // console.log("res", res.responseText);
            // console.log("status", res.status);
            if (res.status === 200) {
                var receiveData = JSON.parse(res.responseText);
                if (receiveData["code"] === registerSuccessCode) {
                    nickName = receiveData["confirmNickName"];
                    // console.log("signWindow", document.querySelector(".signWindow"));
                    document.querySelector(".signWindow").style.display = "none";
                }else {
                    alert("错误代码: " + receiveData["code"]);
                }
            }

        }
    });
}

function inputFilter(message) {
    var pattern_1 = /<(.*?)>(.*?)<\/(.*?)>/igm;
    var pattern_2 = /<(.*?)>(.*?)\/?>/igm;
    var pattern_3 = /<(.*?)>/igm;
    // console.log("pattern_1", pattern_1.test(message));
    // console.log("pattern_2", pattern_2.test(message));
    // console.log("pattern_3", pattern_3.test(message));
    if (pattern_1.test(message)) {
        // console.log(message.replace(pattern_1, "$1 [*]"));
        return message.replace(pattern_1, "<$1>$2</[*]>");
    }else if (pattern_2.test(message)) {
        return message.replace(pattern_2, "<$1>[*]>");
    }else if (pattern_3.test(message)) {
        return message.replace(pattern_3, "<$1[*]");
    }else {
        return message;
    }
}




