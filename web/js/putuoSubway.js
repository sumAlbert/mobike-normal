$(document).ready(function(){
    var num=-1;
    var subway_count=0;
    var subwayX=[];
    var subwayY=[];
    var subwayName=[];
    var subwayId=[];
    var JSON_Data=[];
    var distances=[];
    var message="";
    $("#start").click(function(){
        updateInfo();
    });
    function updateInfo(){
        if(num==-1){
            $.ajax({
                url: "PutuoSubway",
                type: "POST",
                data:{
                    "num":num
                },
                success: function(Data){
                    JSON_Data=JSON.parse(Data);
                    for(var i in JSON_Data){
                        subwayX.push(JSON_Data[i].distX);
                        subwayY.push(JSON_Data[i].distY);
                        subwayName.push(JSON_Data[i].name);
                        subwayId.push("sub"+i);
                    }
                    setTimeout(function () {
                        num++;
                        updateInfo();
                    },1000)
                },
                error:function(){
                    alert("error");
                }
            });
        }
        else if(num<66476){
            $.ajax({
                url: "PutuoSubway",
                type: "POST",
                data:{
                    "num":num,
                    "message":"normal"
                },
                success: function(Data){
                    var JSON_Data=JSON.parse(Data);
                    subway_count=0;
                    distances=[];
                    message="normal";
                    console.log("Handing: "+JSON_Data.id+","+JSON_Data.distX+","+JSON_Data.distY);
                    saveDistance(JSON_Data.id,JSON_Data.distX,JSON_Data.distY);
                },
                error:function(){
                    alert("error");
                }
            });
        }
    }
    function saveDistance(id,distX,distY){
        var lnglat = new AMap.LngLat(distX, distY);
        if(subway_count<20){
            var distance=lnglat.distance([subwayX[subway_count],subwayY[subway_count]]);
            distances[subway_count]=distance;
            setTimeout(function () {
                subway_count++;
                saveDistance(id,distX,distY);
            },1)
        }
        else{
            var String_distances=distances.join(",");
            $.ajax({
                url: "PutuoSubway",
                type: "POST",
                data:{
                    "num":num,
                    "message":"save",
                    "distances":String_distances,
                    "id":id
                },
                success: function(){
                    console.log("finish "+num);
                    setTimeout(function () {
                        num++;
                        updateInfo();
                    },1)
                },
                error:function(){
                    alert("error");
                }
            });
        }
        // console.log(lnglat.distance([subwayX[0],subwayY[0]]));
        // console.log(lnglat.distance([subwayX[1],subwayY[1]]));
    }
});