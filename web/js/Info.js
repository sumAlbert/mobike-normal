$(document).ready(function(){
    var num=-1;
    var result="";
    var loop_lock=false;
    var index=469;
    var myGeo = new BMap.Geocoder();
    var start_lng;
    var stop_lng;
    var start_lat;
    var stop_lat;
    $("#start").click(function(){
        updateInfo();
    });
    function updateInfo(){
        $.ajax({
            type : "POST",
            url : "/Info",
            dataType : "text",
            data : {
                num : num ,
                result : result
            },
            success : function(data){
                var JSON_data=JSON.parse(data);
                start_lng=JSON_data["start_lng"];
                start_lat=JSON_data["start_lat"];
                stop_lng=JSON_data["stop_lng"];
                stop_lat=JSON_data["stop_lat"];
                console.log("start");
                document.getElementById("result").innerHTML = "[";
                geocodeSearch(start_lng,start_lat);
            },
            error : function () {
                alert("failure")
            }
        })
    }
    function geocodeSearch(handing_lng,handing_lat){
        loop_lock=true;
        if(handing_lat < stop_lat){
            startNextSearch(handing_lat);
        }
        var pt=new BMap.Point(handing_lng,handing_lat);
        myGeo.getLocation(pt,function(rs){
            if(rs!=null){
                var addComp = rs.addressComponents;
                var addInfo = "{index:\""+index+"\",lng:\""+handing_lng+"\",lat:\""+handing_lat.toFixed(3)+"\",province:\""+addComp.province+"\",city:\""+addComp.city+"\",district:\""+addComp.district+"\",street:\""+addComp.street+"\",streetNumber:\""+addComp.streetNumber+"\"}";
                console.log(addInfo);
                document.getElementById("result").innerHTML += addInfo;
                index++;
                if(handing_lat>=stop_lat){
                    document.getElementById("result").innerHTML += "]";
                    result = document.getElementById("result").innerHTML;
                    num = num+1;
                    setTimeout(function(){updateInfo();},100);
                }
                else{
                    document.getElementById("result").innerHTML += ",";
                }
            }else{
                result="[]";
                num = num+1;
                setTimeout(function(){updateInfo();},100);
                loop_lock=true;
            }

        });
    }
    function startNextSearch(handing_lat){
        console.log(handing_lat);
        if(handing_lat<=stop_lat){
            if(!loop_lock){
                console.log("loop");
                setTimeout(function(){startNextSearch(handing_lat);},200);
            }
            else{
                var next_lng=start_lng;
                var next_lat=handing_lat+0.002;
                setTimeout(function(){geocodeSearch(next_lng,next_lat);},200);
            }
        }
    }
});