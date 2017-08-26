$(document).ready(function(){
    /*下面的ajax是获取片区的共享单车数目*/
   // $.ajax({
   //     type:"post",
   //     url:"/PutuoBlockAddress",
   //     dataType : "text",
   //     data : [],
   //     success : function(data){
   //         var json=JSON.parse(data);
   //         for(var key in json){
   //             document.getElementById(key).innerHTML=json[key];
   //         }
   //     },
   //     error: function () {
   //
   //     }
   // });
   /*下面的ajax是获取片区的平均户占有量*/
    $.ajax({
        type: "post",
        url: "/PutuoBlockPerRoom",
        dataType: "text",
        data: [],
        success: function (data) {
            var json = JSON.parse(data);
            for (var key in json) {
                document.getElementById(key).innerHTML = json[key];
            }
        },
        error: function () {

        }
    })
});