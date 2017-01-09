$(document).ready(function() {
        $( "#webItemDate" ).datepicker({ dateFormat: 'dd-mm-yy' });
        $(".imageToSelect").click(imageSelected);
       
    });
function imageSelected(){
	$("#image\\.sourceURL").val($(this).attr("src"));
}