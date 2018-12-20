/**

 * 
 */

var mytable;
var editor;
var unkhownRows="";
var xmlPath="download";




$(document).ready(function() {
   
   mytable = $('#showlist').DataTable({
	   "paging": false,
	   "scrollX": true,
   });	   

	$('select[name="project_id"]').on('change', function() {
		Report();
		pid=$("#project_id").val();
		$('#dbid').empty().append('<option value="">Select Databases</option>');
		if (pid!=""){
			$.ajax({
				type: 'GET',
				url:  'database/5',
				data: { pid: pid  },
				success: function(val){
		            var obj = JSON.parse(val);
		            $.each(obj, function (id, value) {
		            	$('#dbid').append('<option value="'+value.id+'">'+value.dbname+'</option>');
		            });
		            $('#dbid').trigger("chosen:updated");
				},
				error: function(val){
					alert("Problem Occured");
				}
			});
		}
	});
	
	
	
	$('select[name="dbid"]').on('change', function() {
		Report();
		var dbid=$("#dbid").val();

		$('#tablename').empty().append('<option value="">Select Tables</option>');
		if (dbid!=""){
			$.ajax({
				type: 'GET',
				url: 'database/6',
				data: { dbid: dbid  },
				success: function(val){
					var obj = JSON.parse(val);
		            $.each(obj, function (id, value) {
		            	
		            	$('#tablename').append('<option value="'+value+'">'+value+'</option>');
		            });
		            $('#tablename').trigger("chosen:updated");
				},
				error: function(val){
					alert("Problem Occured");
				}
			});
		}
	});	

	
	
	$('select[name="tablename"]').on('change', function() {
		Report();
	});	
});

function Report(){
	
	if($("#project_id").val()!="" || $("#dbid").val()!="" || $("#tablename").val()!=""){
	    var postData = new FormData($("#mainform")[0]);
	    mytable.clear().draw();
		$.ajax({
	        type: 'post',
	        url:  'loadingconfshow',
	        contentType: false, // The content type used when sending data to the server.
	        data: postData,
	        cache: false,
	        processData: false,
	        success: function (val) {
	            var obj = JSON.parse(val);
	            $.each(obj, function (id, value) {
//	                var sbu= $("#sbu_id option[value='"+value.sbu_id+"']").text();
	                var buttons= '<a href="'+xmlPath+'?name='+value.project_id+'_'+value.dbinfo_id+'_'+value.name+'" download><img src="'+imagePath+'/download.png" onclick="changeFormInfo('+value.id+',2)"></a>&nbsp;&nbsp;&nbsp;' +
	                             '<img src="'+imagePath+'/remove.png" onclick="alert(\"Not implemented\")">';
	                var project= $("#project_id option[value='"+value.project_id+"']").text();
	                var dbid= $("#dbid option[value='"+value.dbinfo_id+"']").text();
	                mytable.row.add( [
	                    project,
	                    dbid,
	                    value.tablename ,
	                    value.name,
	                    value.description,
	                    buttons
	                ] ).node().id="tr_" + value.id;
	            });
	            mytable.draw( false );
			},
			error: function(val){
				var errMessage="The problem occured\n";

//			            var error = jQuery.parseJSON(val.responseText);
//			            $.each(error.message,function(key,value){
//		                    errMessage+=value;
//			            });
	            alert(errMessage);

			}
		});
	}
}
