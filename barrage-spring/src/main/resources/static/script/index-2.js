"use strict";

var barrageContent = document.getElementsByClassName("barrage-content")[0];
// var rect = barrageContent.getBoundingClientRect();

/**
 * 弹道
 * @type {HTMLCollectionOf<Element>}
 */
var barrageRoad = document.getElementsByClassName("barrage-road");


var rangeValue = 20;//字体大小 预设值

var sliderHandle = document.getElementsByClassName("range-slider-handle")[0];//字体调节滑块
var slider = document.getElementsByClassName("range-slider")[0];
var sliderRect = slider.getBoundingClientRect();
var sliderFill = document.getElementsByClassName("range-slider-fill")[0];//滑过区域
var textPreview = document.getElementsByClassName("text-preview")[0];//预览字体区

var content = document.getElementsByClassName("comment-input")[0];//弹幕输入框
var speedNum = document.getElementsByClassName("speed-selector")[0];//弹幕速度
var color = document.getElementsByName("colorSelected");//弹幕颜色选择器
var colorSelected = "white";//被选择颜色 预设值

var transitionEvent = whichTransitionEvent();//判断浏览器内核类型

var index = 1;//数据库弹幕表分页页码

var I = 0;
sliderHandle.style.left = "0px";


// class BarrageData_2 extends Array{
    // constructor() {
    //     super();
    // }
    //
    //
    // push(...args){
    //     super.push(...args);
    //     console.log("push: ", args);
    //     return this.length;
    // }
// }


/**
 * 测试用数据 数据格式
 * @type {*[]}
 */
var barrageData_2 = [
    {
        "content": "第二条弹幕.............",
        "color": "red",
        "speed": 16,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第一条弹幕.............",
        "color": "green",
        "speed": 10,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第三条弹幕.............",
        "color": "green",
        "speed": 12,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第四条弹幕.............",
        "color": "green",
        "speed": 10,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第五条弹幕.............",
        "color": "white",
        "speed": 8,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第六条弹幕.............",
        "color": "red",
        "speed": 8,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第七条弹幕.............",
        "color": "green",
        "speed": 6,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第八条弹幕.............",
        "color": "black",
        "speed": 10,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "第九条弹幕.............",
        "color": "black",
        "speed": 14,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "随机弹幕.............",
        "color": "black",
        "speed": 8,
        "textSize": 20,
        "road": 0,
        "starNum":0
    },
    {
        "content": "随机弹幕.............",
        "color": "red",
        "speed": 5,
        "textSize": 20,
        "road": 0,
        "starNum":0
    }
];

// var barrageData_2 = new BarrageData_2();

barrageData_2 = barrageData_2.concat();
/**
 * 未测试方法
 */
// var barrageDataProxy = new Proxy(barrageData_2,{
//     get:function (target,key,receiver) {
//         return target[key];
//     },
//     set: function (target, key, value, receiver) {
//         target[key] = value;
//         console.log("数组监听", value);
//         return true;
//     }
// })


/**
 * 初始化操作自执行函数
 */
(function () {
    for (var i = 0; i < barrageData_2.length; i++) {
        // barrageData_2[i]["road"] = Math.floor(Math.random() * barrageRoad.length);
        barrageData_2[i]["road"] = i % barrageRoad.length;
    }
    // console.log(barrageData_2);
    var that = this;
    var timer = setInterval(function (args) {
        if (barrageData_2.length <= 0) {
            // that.clearInterval(timer);
            console.log("barrageData:", barrageData_2);
            regularGetBarrage();
            return;
        }
        sendBarrage(0, barrageData_2[0]["content"], barrageData_2[0]["color"], barrageData_2[0]["speed"], barrageData_2[0]["textSize"], barrageData_2[0]["road"]);
        // console.log("barrageData: ", barrageData_2);
        barrageData_2.splice(0, 1);
    }, 1000);


})();

/**
 * 鼠标指定弹幕事件函数
 * @param event
 */
function onMouseIn(event) {
    // console.log(event);
    var barrage = event.valueOf();
    var computeStyle = window.getComputedStyle(barrage), left = computeStyle.getPropertyValue("left");
    barrage.style.left = left;
    barrage.classList.remove("barrage-active");
}

/**
 * 鼠标离开弹幕事件函数
 * @param event
 */
function onMouseLeave(event) {
    // console.log(event);
    var barrage = event.valueOf();
    barrage.classList.add("barrage-active");
}

function mouseClicked(event) {
    var barrage = event.valueOf();
    barrage.classList.add("barrage-clicked");
    barrage.childNodes[1].setAttribute("src", "/pic/star-active.png");
    barrage.childNodes[3].innerHTML = parseInt(barrage.childNodes[3].innerHTML) + 1;
    // for (var i =0;i<barrage.childNodes.length;i++){
    //     console.log(i, barrage.childNodes[i]);
    // }

}


/**
 * 弹幕发送处理函数
 * @param index
 * @param content 弹幕内容
 * @param colorSelected 选择的颜色
 * @param speedNum 弹幕速度
 * @param rangeValue 字体大小
 * @param roadNum 弹道
 */
function sendBarrage(index, content, colorSelected, speedNum, rangeValue, roadNum) {
    // var randomRoadNum = Math.floor(Math.random() * barrageRoad.length);

    var newBarrage = document.createElement("span");

    if (content === "") {
        return;
    }
    // for (var i = 0;i<color.length;i++){
    //     if (color[i].checked) {
    //         colorSelected = color[i].value;
    //     }
    // }
    newBarrage.classList.add("barrage");
    // newBarrage.styleSheet.removeRule(11);
    newBarrage.style.setProperty("color", colorSelected);
    // newBarrage.style.transition = "transition left " + speedNum + "s linear .2s";
    newBarrage.style.transitionDuration = speedNum + "s";
    newBarrage.style.webkitTransitionDuration = speedNum + "s";

    // newBarrage.style.webkitTransition = "-webkit-transition left " + speedNum + "s linear .2s";
    // setProperty("transition", "left " + speedNum + "s linear .2s;");
    newBarrage.style.setProperty("font-size", rangeValue + "px");
    newBarrage.innerHTML = content + "<img src='pic/star.png' class='star-img' alt=''>" +
        "                    <span class='star-num-text'>"+barrageData_2[0]["starNum"]+"</span>";
    // console.log("num:", barrageData_2[0]["starNum"]);
    newBarrage.onmouseenter = function (ev) {
        onMouseIn(this);
    };
    newBarrage.onmouseleave = function (ev) {
        onMouseLeave(this);
    };
    newBarrage.onclick = function (ev) {
        var ul = document.getElementsByClassName("right-menu-ul")[0];

        ul.style.display = "none";
        mouseClicked(this);
    };
    newBarrage.oncontextmenu = function (ev){
        var ul = document.getElementsByClassName("right-menu-ul")[0];

        event.preventDefault();

        ul.style.display = "block";
        ul.style.left = event.clientX +"px";
        ul.style.top = event.clientY + "px";
    }

    //TODO 备注，dom初始化的时机
    barrageRoad[roadNum].appendChild(newBarrage);

    setTimeout(function () {
        newBarrage.classList.add("barrage-active");
    },50);


    newBarrage.addEventListener(transitionEvent, function (evt) {
        // console.log(this);
        this.parentNode.removeChild(this);
        // barrageData_2.splice(0, 1);
        console.log("----------------------transitionEnd事件触发一次---------------");
    });

}

/**
 * 滑块鼠标按下事件函数
 * @param event
 */
sliderHandle.onmousedown = function (event) {
    var that = this;
    var oldX = event.clientX;
    var left = parseInt(that.style.left);


    // console.log("mousedown  ", sliderHandle);
    document.onmousemove = function (ev) {
        var x = ev.clientX - oldX;

        that.style.left = left + x + "px";

        rangeValue = Math.ceil((parseInt(that.style.left) / sliderRect.width) * 40) + 1;


        if (parseInt(that.style.left) < 0) {
            that.style.left = "0";
            rangeValue = 10;
        }
        if (parseInt(that.style.left) > sliderRect.width) {
            that.style.left = sliderRect.width - 10 + "px";
            rangeValue = 50;
        }
        sliderFill.style.width = that.style.left;
        textPreview.style.fontSize = rangeValue + "px";
        console.log("rangeValue: ", rangeValue);
    };


    document.onmouseup = function (ev) {
        document.onmouseup = null;
        document.onmousemove = null;
    }
};

/**
 * 发送弹幕按钮点击事件响应函数
 */
function addBarrageData() {
    var item = {
        "content": "",
        "color": "red",
        "speed": 16,
        "textSize": 20,
        "road": 0,
        "starNum": 0
    };
    var content_1 = content.value;
    item["speed"] = speedNum.value;
    item["textSize"] = rangeValue;
    item["road"] = Math.floor(Math.random() * barrageRoad.length);

    content_1 = content_1.replace(/<.*?>.*?<\/.*?>/g, '[非法字段]');
    content_1 = content_1.replace(/<img/g, '[非法字段]');
    content_1 = content_1.replace(/<script/g, '[非法字段]');
    item["content"] = content_1;
    console.log("content: ", content_1);

    for (var i = 0;i<color.length;i++) {
        if (color[i].checked) {
            item["color"] = color[i].value;
            break;
        }
    }
    console.log("添加事件触发");
    barrageData_2.push(item);
    console.log("barrageData :", barrageData_2.length);
    // content.value = "";
    saveBarrage();
}

/**
 * 右键菜单处理函数
 */

// document.oncontextmenu = function (event) {
//
//     var ul = document.getElementsByClassName("right-menu-ul")[0];
//
//     event.preventDefault();
//
//     ul.style.display = "block";
//     ul.style.left = event.clientX +"px";
//     ul.style.top = event.clientY + "px";
//
//     // return false;
// }

document.onclick = function (e) {
    var ul = document.getElementsByClassName("right-menu-ul")[0];

    ul.style.display = "none";
};

/**
 * 举报弹幕响应函数
 * @param id
 */
function report(id) {

    $.messager.show({
        title: "提示消息",
        msg: "举报成功",
        timeout: 5000
    });
}

function saveBarrage() {
    $.ajax({
        url: "/barrage/save",
        type: "post",
        dataType: "json",
        data: {
            "barrageSenderId": 1,
            "content": content.value,
            "speed":speedNum.value,
            "color":function () {
                for (var i = 0;i<color.length;i++){
                    if (color[i].checked){
                        return color[i].value;
                    }
                }
            },
            "textSize": rangeValue,
            "road": Math.floor(Math.random() * barrageRoad.length)
        },
        complete:function (ev) {
            // console.log(ev);
            content.value = "";
            if (ev.status === 200){
                console.log(ev.responseText);
            }else{
                console.log("error", ev.responseText);
            }
        }
    });
}

function regularGetBarrage() {
    $.ajax({
        url: "/barrage/getData?index=" + index,
        type: "get",
        dataType: "json",
        complete: function (ev) {
            if (ev.status == 200) {
                var data = JSON.parse(ev.responseText);
                barrageData_2 = barrageData_2.concat(data["resultData"]["barrageInfoPage"]["content"]);
                console.log("regular", barrageData_2);
                index++;
                // console.log(data["resultData"]["barrageInfoPage"]["content"]);
            } else {
                console.log("error", ev.responseText);
            }
        }
    });
}
/**
 * 浏览器内核判断函数
 * @returns {*}
 */
function whichTransitionEvent() {
    var t;
    var el = document.createElement('fakeelement');
    var transitions = {
        'transition': 'transitionend',
        'OTransition': 'oTransitionEnd',
        'MozTransition': 'transitionend',
        'WebkitTransition': 'webkitTransitionEnd'
    }

    for (t in transitions) {
        if (el.style[t] !== undefined) {
            return transitions[t];
        }
    }
}
