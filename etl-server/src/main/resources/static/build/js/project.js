/**
 * 
 */
$(document).ready(function() {
	ShowProject();
	$.ajaxSetup({
        header: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

	$("button").click(function(event){
	    event.preventDefault();
	    var action=0;
	    var postData = new FormData($("#mainform")[0]);
	    var id= $("#identifier").val();
	    postData.append('id', id);
	    
		switch(this.id){
			case "register":
				action = 1;
				break;
			case "change":
				action = 2;
				break;
			case "delete":
				action = 3;
				break;
			case "cancel":
				ClearForm();
				return;
		}
		
		postData.append('action', action);
		$.ajax({
			        type: 'post',
			        url:  'project',
			        contentType: false, // The content type used when sending data to the server.
			        data: postData,
			        cache: false,
			        processData: false,
			        success: function (val) {
			        	alert(val);
			        	ShowProject();
					},
					error: function(val){
						var errMessage="The problem occured\n";
	
	//		            var error = jQuery.parseJSON(val.responseText);
	//		            $.each(error.message,function(key,value){
	//	                    errMessage+=value;
	//		            });
			            alert(errMessage);
	
					}
		});

	});
	
});




function ShowProject() {

	var mytable = $('#showlist').DataTable();
    mytable.clear().draw();

    $.ajax({
        type: 'get',
        url:  'projectshow',
        success: function (val) {
            var obj = JSON.parse(val);
            $.each(obj, function (id, value) {
//                var sbu= $("#sbu_id option[value='"+value.sbu_id+"']").text();
                var buttons= '<img src="'+imagePath+'/edit.png" onclick="changeFormInfo('+value.id+',2)">&nbsp;&nbsp;&nbsp;' +
                             '<img src="'+imagePath+'/remove.png" onclick="changeFormInfo('+value.id+',3)">';
                mytable.row.add( [
                    value.name,
                    value.customer ,
                    value.description,
                    buttons
                ] ).node().id="tr_" + value.id;
            });
            mytable.draw( false );
        },
        error: function (val){
        	alert("Problem happened");
        }
    	
    });
}
