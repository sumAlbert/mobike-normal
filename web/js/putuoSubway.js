$(document).ready(function(){
    $.ajax({
        url: "PutuoSubway",
        type: "POST",
        success: function(){
            alert("success");
        },
        error:function(){
            alert("error");
        }
    })
});