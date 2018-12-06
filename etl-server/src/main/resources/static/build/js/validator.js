/**
 * 
 */

var validatorItemTable;
var validatorsTable;
//var tableNames = [];

var optionOfTables="";



$(document).ready(function() {
	$.ajaxSetup({
        header: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });
	validatorItemTable = $('#showlist').DataTable({
	"paging": false,
//    "scrollX": true,

	});	

	validatorsTable = $('#validatorList').DataTable({
		"paging": false,
//	    "scrollX": true,
	    "scrollY": true,
		buttons: [
			'pageLength'
	    ]
		});	

	ShowValidators();
	
	$("button").click(function(event){
		
	    event.preventDefault();
	    var action=0;
	    //var postData = new FormData($("#mainform")[0]);

	    var id= $("#identifier").val();
	    
	    
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
			case "addrow":
					AddRow();
				return;
			case "delrow":
					DelRow()
				return;
			case "cancel":
				ClearForm();
				return;
		}
		var postData = $("#mainform").serialize()+"&action=" + action;
		//postData.append('dataTable', validatorItemTable.data());

 
		$.ajax({
	        type: 'post',
	        url: 'validator',
//	        contentType: false, // The content type used when sending data to the server.
	        //processData: false,
	        data: postData,
	        success: function (val) {
	        	alert("Data registered successfully\n" + val);
//	        	ShowProject();
			},
			error: function(val){
				var a=JSON.stringify(val);
				var errMessage="The problem occured\n"+a;
//				alert("salam");
//	            var error = JSON.parse(val);
//	            alert("salam111111111");
//	            $.each(error,function(key,value){
//                    errMessage+=value;
//	            });
	            alert(errMessage);

			}
		});
	});
	
	
	$.ajax({
		type: 'GET',
		url: 'database/6',
		async: false,
		data: { dbid: "4"},
		success: function(val){
			var obj = JSON.parse(val);
			var count = 0;
			optionOfTables='<option value="" selected>Select Table</option>';
	        
			$.each(obj, function (id, value) {
				++count;
	        	optionOfTables+='<option value="'+value+'">'+value+'</option>';
	        });
	        //alert(optionOfTables);
		},
		error: function(val){
			alert("Problem Occured:\n"+val);
		}
	});
	


	ValidatorTable();

});




function ValidatorTable() {
	validatorItemTable.order([]);
    validatorItemTable.clear().draw();

	//alert("str is: "+table+"and length is: "+$(table).length);

    var buttons= '<img src="'+imagePath+'/remove.png" onclick="deleteRow()">';
    validatorItemTable.row.add([
    	'<input type="text" id="csvorder_0" name="csvorder[]" class="editor-active"  value="1" size="5"/>',
    	'<input type="text" id="csvtitle_0" name="csvtitle[]" class="editor-active" >',
    	'<select id="tablename_0" name="tablename[]" class="editor-active" onchange="FillCombo(this)">'+optionOfTables+'</select>',
    	'<select id="columnname_0" name="columnname[]" class="editor-active"><option value="" selected>Select Column</option></select>',
    	'<textarea type="text" id="conditions_0" name="conditions[]" class="editor-active"></textarea>',
    	'<input type="text" id="cksequence_0" name="cksequence[]" class="editor-active" size="5">',
    	'<input type="text" id="desc_0" name="desc[]" class="editor-active" size="10">',
    	//'<input type="text" id="type_0" name="cksequence[]" class="input2lable" size="5">',
    	buttons
    ]).node().id="tr_0";
    validatorItemTable.draw( false );
}



function AddRow(){
	var table='#showlist';
	//alert("str is: "+table+"and length is: "+$(table).length);
	var competCount = $(table+" tr").length;
	//alert("row count is:"+competCount);
	$("#tr_0").clone().appendTo(table)
    .attr("id","tr_"+competCount)
    .find("*")
    .each(function() {
        var id = this.id || "";
        var match = id.match(idregex) || [];
        if (match.length == 3) {
            this.id = match[1] + (competCount);
        }
    });
}


function DelRow(){
	var table='#showlist';
	var competCount = $(table+" tr").length;
    if(competCount >2)
        $(table+' tr:last').remove();
}

function FillCombo(row){
	var selectId=$(row).prop('id');
	var tableName=$(row).val();
	var charIndex = selectId.indexOf('_');
	if(charIndex>0)
	{
		var rowId =	selectId.substr(charIndex+1,selectId.length+1);
		var coulmnCombo=$("#columnname_"+rowId);
		$.ajax({
			type: 'GET',
			url: 'database/7',
			async: false,
			data: { dbid: "4", tablename: tableName },
			success: function(val){
				var obj = JSON.parse(val);
				var count = 0;
				coulmnCombo.empty();
				coulmnCombo.append('<option value="">Select Column</option>');
	            $.each(obj, function (id, value) {
	            	coulmnCombo.append('<option value="'+value.colName+'#'+value.colType+'">'+value.colName+'</option>');
	            });
			},
			error: function(val){
				alert("Problem Occured"+ val);
			}
		});
	}
}


function ShowValidators(){
	$.ajax({
		type: 'GET',
		url:  'validatorshow',
		async: true,
		success: function(val){
            var obj = JSON.parse(val);
            $.each(obj, function (id, value) {
            	var project= $("#project_id option[value='"+value.project_id+"']").text();
                var buttons= '<img src="'+imagePath+'/edit.png" onclick="privateChangeFormInfo('+value.id+',2)">&nbsp;&nbsp;&nbsp;' +
                             '<img src="'+imagePath+'/remove.png" onclick="privateChangeFormInfo('+value.id+',3)">';
                validatorsTable.row.add( [
                	project,
                    value.name,
                    value.description,
                    buttons
                ] ).node().id="tr_" + value.id;
            });
            validatorsTable.draw( false );
        },
		error: function(val){
			alert("Problem Occured:\n"+val);
		}
	});	
}

function privateChangeFormInfo(id,type){
	changeFormInfo(id,type);
	ValidatorTable();
	$.ajax({
		type: 'GET',
		url:  'validationitemshow',
		async: false,
		data: {validator_id: id},
		success: function(val){
			
            var obj = JSON.parse(val);
            var count=0;
            $.each(obj, function (id, value) {
            	if(count!=0){
            		AddRow();
            		count+=1;
            	}
            	else
            		count+=2;
            	var test=$('#showlist tr:last').prop('id').split('_');
            	var rowId=test[1];
            	$("#csvorder_"+rowId).val(value.csvorder);
            	$("#csvtitle_"+rowId).val(value.csvtitle);
            	$("#tablename_"+rowId).val(value.tablename);

        		var coulmnCombo=$("#columnname_"+rowId);
        		var colName="";
        		$.ajax({
        			type: 'GET',
        			url: 'database/7',
        			async: false,
        			data: { dbid: "4", tablename: value.tablename },
        			success: function(val){
        				var obj = JSON.parse(val);
        				var count = 0;
        				coulmnCombo.empty();
        				coulmnCombo.append('<option value="" selected>Select Column</option>');
        	            $.each(obj, function (id, value2) {
        	            	coulmnCombo.append('<option value="'+value2.colName+'#'+value2.colType+'">'+value2.colName+'</option>');
        	            	if(value2.colName==value.columnname)
        	            		colName=value2.colName+'#'+value2.colType;
        	            });
        			},
        			error: function(val){
        				alert("Problem Occured"+ val);
        			}
        		});

             	$("#columnname_"+rowId).val(colName);
            	$("#conditions_"+rowId).val(value.conditions);
            	$("#cksequence_"+rowId).val(value.sequence);
            	$("#desc_"+rowId).val(value.description);

            	
            	//fill table
        		++count;
            });

        },
		error: function(val){
			alert("Problem Occured:\n"+val);
		}
	});	
	
	
}