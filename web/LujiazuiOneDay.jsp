<%--
  Created by IntelliJ IDEA.
  User: 98267
  Date: 2017/8/30
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>鼠标拾取地图坐标</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.3&key=97a6dbb9d094503ff86c6a7076c3ba69&plugin=AMap.Autocomplete"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <style type="text/css">
        #info_table{
            width: 100%;
        }
        #info_table>tbody>tr>td{
            width: 24%;
            line-height: 20px;
            vertical-align: middle;
            color: #666;
            text-align: center;
        }
    </style>
</head>
<body>
<div id="container" style="width:70%"></div>
<div id="info" style="background: white;position: absolute;right: 0px;left: 71%;top: 0px;bottom: 0px;">
    <table id="info_table">
        <tr>
            <td>时间</td>
            <td>数量</td>
            <td>时间</td>
            <td>数量</td>
        </tr>
        <tr>
            <td class="item1">0:00</td>
            <td class="item2">0</td>
            <td class="item3">0:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">1:00</td>
            <td class="item2">0</td>
            <td class="item3">1:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">2:00</td>
            <td class="item2">0</td>
            <td class="item3">2:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">3:00</td>
            <td class="item2">0</td>
            <td class="item3">3:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">4:00</td>
            <td class="item2">0</td>
            <td class="item3">4:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">5:00</td>
            <td class="item2">0</td>
            <td class="item3">5:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">6:00</td>
            <td class="item2">0</td>
            <td class="item3">6:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">7:00</td>
            <td class="item2">0</td>
            <td class="item3">7:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">8:00</td>
            <td class="item2">0</td>
            <td class="item3">8:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">9:00</td>
            <td class="item2">0</td>
            <td class="item3">9:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">10:00</td>
            <td class="item2">0</td>
            <td class="item3">10:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">11:00</td>
            <td class="item2">0</td>
            <td class="item3">11:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">12:00</td>
            <td class="item2">0</td>
            <td class="item3">12:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">13:00</td>
            <td class="item2">0</td>
            <td class="item3">13:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">14:00</td>
            <td class="item2">0</td>
            <td class="item3">14:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">15:00</td>
            <td class="item2">0</td>
            <td class="item3">15:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">16:00</td>
            <td class="item2">0</td>
            <td class="item3">16:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">17:00</td>
            <td class="item2">0</td>
            <td class="item3">17:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">18:00</td>
            <td class="item2">0</td>
            <td class="item3">18:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">19:00</td>
            <td class="item2">0</td>
            <td class="item3">19:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">20:00</td>
            <td class="item2">0</td>
            <td class="item3">20:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">21:00</td>
            <td class="item2">0</td>
            <td class="item3">21:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">22:00</td>
            <td class="item2">0</td>
            <td class="item3">22:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1">23:00</td>
            <td class="item2">0</td>
            <td class="item3">23:30</td>
            <td class="item4">0</td>
        </tr>
        <tr>
            <td class="item1"></td>
            <td class="item2"></td>
            <td class="item3">下一日0:00</td>
            <td class="item4" id="old_time">0</td>
        </tr>
    </table>
</div>
<div id="myPageTop" style="left: 41%;top: 10px;right: 33%;">
    <table>
        <tr>
            <td>
                <label>按关键字搜索：</label>
            </td>
            <td class="column2">
                <label>左击获取经纬度：</label>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" placeholder="请输入关键字进行搜索" id="tipinput">
            </td>
            <td class="column2">
                <input type="text" readonly="true" id="lnglat">
            </td>
        </tr>
    </table>
</div>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/LujiazuiOneDay.js"></script>
</body>
</html>