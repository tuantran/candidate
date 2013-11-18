function displayContent(objectId, name){
    if($(objectId).is(':hidden')){
		$(objectId).slideDown('400');
                document.getElementById(name).innerHTML="Hide[-]";
    }else{
		$(objectId).slideUp('400');
                document.getElementById(name).innerHTML="More[+]";
	}
}

$(function() {  

// show/hide menu
$(".menudrop").hover(
    function () {
        $(".submenu").show();
    },
    function () {
        $(".submenu").hide();
    }
 );         
$(".submenu").hover(
    function () {
        $(".submenu").show();
    },
    function () {
        $(".submenu").hide();
    }
 );     

// select/deselect all
var selectAllToggleState = false;
$('.selectall').bind('click', function(){
    if (selectAllToggleState) {
       $('.ads_checkbox').each(function() {
           $(this).removeAttr('checked');
       });      
    } else {    
       $('.ads_checkbox').each(function() {
           $(this).attr('checked', 'checked');                        
       });
    }    
    selectAllToggleState = ! selectAllToggleState;
});

});



