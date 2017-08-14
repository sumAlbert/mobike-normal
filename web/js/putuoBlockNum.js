$(document).ready(function(){
   $.ajax({
       type:"post",
       url:"/PutuoBlockAddress",
       dataType : "text",
       data : [],
       success : function(data){
           var json=JSON.parse(data);
           for(var key in json){
               document.getElementById(key).innerHTML=json[key];
           }
       },
       error: function () {
           
       }
   });
});