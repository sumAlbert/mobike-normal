var num=-1;
var result="";
$(document).ready(function(){
    $("#start").click(function(){
       $.ajax({
           type : "POST",
           url : "/Info",
           dataType : "text",
           data : {
               num : num ,
               result : result
           },
           success : function(data){
               alert(data);
           },
           error : function () {
               alert("failure")
           }
       })
    });
});