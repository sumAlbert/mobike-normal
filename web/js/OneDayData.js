$(document).ready(function(){
    var item2=document.getElementsByClassName("item2");
    var item4=document.getElementsByClassName("item4");
    var save_time=[
        "2017-08-28 23:31:29",
        "2017-08-29 00:01:30",
        "2017-08-29 00:31:30",
        "2017-08-29 01:01:30",
        "2017-08-29 01:31:31",
        "2017-08-29 02:01:32",
        "2017-08-29 02:31:32",
        "2017-08-29 03:01:33",
        "2017-08-29 03:31:34",
        "2017-08-29 04:01:35",
        "2017-08-29 04:31:35",
        "2017-08-29 05:01:36",
        "2017-08-29 05:31:36",
        "2017-08-29 06:01:36",
        "2017-08-29 06:31:37",
        "2017-08-29 07:01:37",
        "2017-08-29 07:31:38",
        "2017-08-29 08:01:39",
        "2017-08-29 08:31:39",
        "2017-08-29 09:01:40",
        "2017-08-29 09:31:40",
        "2017-08-29 10:01:41",
        "2017-08-29 10:31:41",
        "2017-08-29 11:01:42",
        "2017-08-29 11:31:42",
        "2017-08-29 12:01:43",
        "2017-08-29 12:31:44",
        "2017-08-29 13:01:44",
        "2017-08-29 13:31:45",
        "2017-08-29 14:01:45",
        "2017-08-29 14:31:45",
        "2017-08-29 15:01:45",
        "2017-08-29 15:31:46",
        "2017-08-29 16:01:46",
        "2017-08-29 16:31:47",
        "2017-08-29 17:01:47",
        "2017-08-29 17:31:47",
        "2017-08-29 18:01:48",
        "2017-08-29 18:31:48",
        "2017-08-29 19:01:48",
        "2017-08-29 19:31:49",
        "2017-08-29 20:01:49",
        "2017-08-29 20:31:50",
        "2017-08-29 21:01:51",
        "2017-08-29 21:31:51",
        "2017-08-29 22:01:51",
        "2017-08-29 22:31:51",
        "2017-08-29 23:01:52",
        "2017-08-29 23:33:48"
    ];
    var lock=false;
    var map = new AMap.Map("container", {
        resizeEnable: true
    });
    //为地图注册click事件获取鼠标点击出的经纬度坐标
    var clickEventListener = map.on('click', function(e) {
        document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
        if(lock){
            alert("等待数据刷新");
        }
        else{
            lock=true;
            getArround(e.lnglat.getLng(),e.lnglat.getLat());
        }
    });
    var auto = new AMap.Autocomplete({
        input: "tipinput"
    });
    AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
    function select(e) {
        if (e.poi && e.poi.location) {
            map.setZoom(15);
            map.setCenter(e.poi.location);
        }
    }
    function getArround(lng,lat){
        $.ajax({
            url:"OneDayData",
            type:"POST",
            data:{
                "lng":lng,
                "lat":lat
            },
            success:function(Data){
                lock=false;
                showResult(Data);
            },
            error:function () {
                lock=false;
                console.log("failure");
            }
        });
    }
    function showResult(Data){
        var JSON_Data=JSON.parse(Data);
        document.getElementById("old_time").innerHTML=JSON_Data[save_time[0]];
        num=1;
        console.log(Data);
        inputResult(Data,num);
    }
    function inputResult(Data,num){
        console.log(num);
        var JSON_Data=JSON.parse(Data);
        console.log(JSON_Data[save_time[num]]);
        if(num%2==0){
            var flag=parseInt((num-1)/2);
            item4[flag].innerHTML=JSON_Data[save_time[num]];
        }
        else{
            var flag=parseInt((num-1)/2);
            item2[flag].innerHTML=JSON_Data[save_time[num]];
        }
        num++;
        if(num<=48)
            setTimeout(inputResult(Data,num),100);
    }
});