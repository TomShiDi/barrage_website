var deviceType = goPAGE();

function getCommentClick() {
    var avId = document.getElementById("avId").value;
    var form = document.getElementsByTagName("form")[0];
    if (avId === "") {
        console.log("avId is null");
        alert("请输入有效id号");
        return;
    }
    var i = document.getElementsByTagName("i")[0];
    i.classList.add("fa", "fa-spinner", "fa-spin");
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/bilibili/get-comment?avId=" + avId,
        complete: function (response) {
            if (response.status === 200 && response.responseJSON.code === 200) {
                var data = JSON.parse(response.responseText);
                var a = document.createElement("a");
                // a.rel = "nofollow";
                if (data["code"] === 500) {
                    alert(data["message"]);
                }
                if (data["code"] === 200) {
                    a.href = data["message"];
                    if (deviceType === "mobile") {
                        a.download = avId + ".txt";
                    } else {
                        a.download = "";
                    }

                    document.getElementsByTagName("body")[0].appendChild(a);
                    a.click();

                }
            } else {
                if (response.status === 200 && response.responseJSON.code === 8) {
                    window.location.href = window.location.protocol + "//" + window.location.host + "/login.html";
                }else {
                    alert("系统异常");
                }
            }
            i.classList.remove("fa", "fa-spinner", "fa-spin");
        }

    });
    alert("请耐心等十几秒");
}


/**
 *判断是否为移动端
 */
function goPAGE() {
    if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
        /*window.location.href="你的手机版地址";*/
        // deviceType = "mobile";
        return "mobile";
    }
    else {
        /*window.location.href="你的电脑版地址";    */
        // deviceType = "pc";
        return "pc";
    }
}