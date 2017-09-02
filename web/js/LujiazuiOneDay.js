$(document).ready(function(){
    var item2=document.getElementsByClassName("item2");
    var item4=document.getElementsByClassName("item4");
    var save_time=[
        "2017-08-31 00:01:15",
        "2017-08-30 00:00:51",
        "2017-08-30 00:30:51",
        "2017-08-30 01:00:52",
        "2017-08-30 01:30:52",
        "2017-08-30 02:00:52",
        "2017-08-30 02:30:53",
        "2017-08-30 03:00:54",
        "2017-08-30 03:30:54",
        "2017-08-30 04:00:55",
        "2017-08-30 04:30:55",
        "2017-08-30 05:00:56",
        "2017-08-30 05:30:56",
        "2017-08-30 06:00:57",
        "2017-08-30 06:30:58",
        "2017-08-30 07:00:58",
        "2017-08-30 07:30:58",
        "2017-08-30 08:00:59",
        "2017-08-30 08:30:59",
        "2017-08-30 09:01:00",
        "2017-08-30 09:31:00",
        "2017-08-30 10:01:01",
        "2017-08-30 10:31:02",
        "2017-08-30 11:01:02",
        "2017-08-30 11:31:03",
        "2017-08-30 12:01:03",
        "2017-08-30 12:31:03",
        "2017-08-30 13:01:03",
        "2017-08-30 13:31:04",
        "2017-08-30 14:01:04",
        "2017-08-30 14:31:04",
        "2017-08-30 15:01:05",
        "2017-08-30 15:31:06",
        "2017-08-30 16:01:06",
        "2017-08-30 16:31:06",
        "2017-08-30 17:01:07",
        "2017-08-30 17:31:08",
        "2017-08-30 18:01:08",
        "2017-08-30 18:31:09",
        "2017-08-30 19:01:10",
        "2017-08-30 19:31:11",
        "2017-08-30 20:01:12",
        "2017-08-30 20:31:12",
        "2017-08-30 21:01:12",
        "2017-08-30 21:31:12",
        "2017-08-30 22:01:13",
        "2017-08-30 22:31:13",
        "2017-08-30 23:01:14",
        "2017-08-30 23:31:14"
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
            url:"LujiazuiOneDay",
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