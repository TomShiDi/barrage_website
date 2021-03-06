<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>

    <meta http-equiv="X-UA-Compatible" content=="IE=edge"/>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>-------------</title>
    <link type="text/css" href="/script/jquery-easyui-1.7.5/themes/icon.css" rel="stylesheet">
    <link type="text/css" href="/script/jquery-easyui-1.7.5/themes/default/easyui.css" rel="stylesheet">
    <script type="text/javascript" src="/script/jquery.min.js"></script>
    <script type="text/javascript" src="/script/jquery-easyui-1.7.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/script/jquery-easyui-1.7.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="module" src="script/index-2.js?t=1.1.8">
        import {addBarrageData} from '../static/script/index-2'
    </script>
    <!--<script src="https://use.fontawesome.com/06dae4fa6e.js"></script>-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="/css/style.css?t=1.2.8">
</head>
<body>
<!--<script src="/script/three.min.js"></script>-->
<!--<script src="/script/canvas-index.js"></script>-->
<!--<canvas width="1920" height="1007" style="position: absolute; top: 0;"></canvas>-->
<nav class="side-menu">
    <div class="settings">
        <a href="/login.html">
            <img class="head-icon" src="pic/no_login.png"/>
        </a>
    </div>
    <div class="menu-ul">
        <ul>
            <li>
                <a href="#">
                    <i class="fa">
                        <svg class="bi bi-house-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M8 3.293l6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z" clip-rule="evenodd"></path>
                            <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z" clip-rule="evenodd"></path>
                        </svg>
                    </i>
                    <span class="nav-text">Home</span>
                </a>
            </li>

            <li>
                <a href="panSearch.html">
                    <i class="fa">
                        <svg class="bi bi-cloud-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M3.5 13a3.5 3.5 0 1 1 .59-6.95 5.002 5.002 0 1 1 9.804 1.98A2.5 2.5 0 0 1 13.5 13h-10z" clip-rule="evenodd"></path>
                        </svg>
                    </i>
                    <span class="nav-text">网盘资源搜索</span>
                </a>
            </li>

            <li>
                <a href="BComment.html">
                    <i class="fa">
                        <svg class="bi bi-bootstrap-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M4.002 0a4 4 0 0 0-4 4v8a4 4 0 0 0 4 4h8a4 4 0 0 0 4-4V4a4 4 0 0 0-4-4h-8zm1.06 12h3.475c1.804 0 2.888-.908 2.888-2.396 0-1.102-.761-1.916-1.904-2.034v-.1c.832-.14 1.482-.93 1.482-1.816 0-1.3-.955-2.11-2.542-2.11H5.062V12zm1.313-4.875V4.658h1.78c.973 0 1.542.457 1.542 1.237 0 .802-.604 1.23-1.764 1.23H6.375zm0 3.762h1.898c1.184 0 1.81-.48 1.81-1.377 0-.885-.65-1.348-1.886-1.348H6.375v2.725z" clip-rule="evenodd"></path>
                        </svg>
                    </i>
                    <span class="nav-text">B站评论提取</span>
                </a>
            </li>

            <li>
                <a href="socket.html">
                    <i class="fa">
                        <svg class="bi bi-chat-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path d="M8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6-.097 1.016-.417 2.13-.771 2.966-.079.186.074.394.273.362 2.256-.37 3.597-.938 4.18-1.234A9.06 9.06 0 0 0 8 15z"></path>
                        </svg>
                    </i>
                    <span class="nav-text">聊天室</span>
                </a>
            </li>

            <!--<li>-->
            <!--<a href="#">-->
            <!--<i class="fa fa-rocket fa-lg"></i>-->
            <!--<span class="nav-text">Rocket</span>-->
            <!--</a>-->
            <!--</li>-->

            <!--<li>-->
            <!--<a href="#">-->
            <!--<i class="fa fa-code fa-lg"></i>-->
            <!--<span class="nav-text">Code</span>-->
            <!--</a>-->
            <!--</li>-->

            <!--<li>-->
            <!--<a href="#">-->
            <!--<i class="fa fa-coffee fa-lg"></i>-->
            <!--<span class="nav-text">Coffee</span>-->
            <!--</a>-->
            <!--</li>-->

            <!--<li>-->
            <!--<a href="#">-->
            <!--<i class="fa fa-compass fa-lg"></i>-->
            <!--<span class="nav-text">Compass</span>-->
            <!--</a>-->
            <!--</li>-->

            <!--<li>-->
            <!--<a href="#">-->
            <!--<i class="fa fa-download fa-lg"></i>-->
            <!--<span class="nav-text">Download</span>-->
            <!--</a>-->
            <!--</li>-->
        </ul>
    </div>
</nav>
<div class="barrage-box">
    <!--<div style="z-index: -100;width: 100%;height: 100%;color: #3d3d3d;font-size: 20px">-->
    <!--测试文字-->
    <!--</div>-->
    <div class="barrage-content" style="position: relative;top: 0;">
        <div class="barrage-road">
                <span class="barrage">
                    <img src="pic/huaji.jpg" rel="icon" class="barrage-head-img" alt=""/>
                    <div class="barrage-info-area">
                        测试弹幕.....
                        <img src="pic/star-active.png" class="star-img" alt="">
                        <span class="star-num-text">0</span>
                    </div>
                </span>
        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div class="barrage-road">

        </div>
        <div style="z-index: -100;position: absolute;top: 0;width: 100%;height: 100%;color: #c841cb;font-size: 20px;display: flex;justify-content: center;align-items: center;">
            <div style="width: 50%;
            height: 50%;
            word-break: break-all;
            white-space: normal;
            display: flex;
            justify-content: center;
            align-items: center;
            box-shadow: 2px 0 2px rgba(139,255,134,0.58),0 2px 2px rgba(167,254,106,0.58),-2px 0 2px rgba(167,254,106,0.58),0 -2px 2px rgba(167,254,106,0.58);
            border-radius: 8px;
            overflow: hidden;
            padding: 2%;
        " quotes-flag>
                <pre style="width: 100%;
            height: 100%;
            word-break: break-all;
            white-space: normal;
            display: flex;
            justify-content: center;
            align-items: center;
            /*box-shadow: 2px 0 2px rgba(139,255,134,0.58),0 2px 2px rgba(167,254,106,0.58),-2px 0 2px rgba(167,254,106,0.58),0 -2px 2px rgba(167,254,106,0.58);*/
            border-radius: 8px;
            overflow: hidden;
            padding: 2%;
            color: #c841cb;
            font-size: 20px;
            ">

                </pre>
                <!--测试文字<br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda-->
                <!--测试文字<br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda-->
                <!--测试文字<br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda <br /> sadasda-->
            </div>

        </div>
    </div>

    <div class="barrage-settings">
        <!--<input class="text-size" type="range" max="100" min="0">-->
        <input class="text-preview" type="button" value="Hello World">
        <div class="range-slider">
            <div class="range-slider-fill"></div>
            <div class="range-slider-handle"></div>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="black">
            <span class="colorSelectorText" style="color: black">黑</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="red">
            <span class="colorSelectorText" style="color: red">红</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="green">
            <span class="colorSelectorText" style="color: green">绿</span>
        </div>
        <div style="margin-left: 5px">
            <input type="radio" name="colorSelected" value="white" checked="checked">
            <span class="colorSelectorText" style="color: white">白</span>
        </div>
        <div style="margin-left: 10px">
            <select class="speed-selector">
                <option value="12" name="speedNum">请选择车速</option>
                <option value="12" name="speedNum">8</option>
                <option value="11" name="speedNum">7</option>
                <option value="10" name="speedNum">6</option>
                <option value="9" name="speedNum">5</option>
                <option value="8" name="speedNum">4</option>
                <option value="7" name="speedNum">3</option>
                <option value="6" name="speedNum">2</option>
                <option value="5" name="speedNum">1</option>
            </select>
        </div>

    </div>

    <div class="comment-box">
        <input class="comment-input" type="text" placeholder="请输入待发送内容">
    </div>
    <div class="button-area">
        <button class="send-button">发送弹幕</button>
    </div>

</div>
<div class="tipImgArea">
    <img src="pic/phone-reverse.gif">
    <span>请解除手机屏幕锁定，并将手机横屏放置</span>
</div>

<ul class="right-menu-ul" style="display: none">
    <li class="menu-item">举报当前弹幕</li>
</ul>
</body>
</html>