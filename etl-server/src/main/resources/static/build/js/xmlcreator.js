/**

 * 
 */

var columnType = [];
var mytable;
var editor;
var unkhownRows="";




$(document).ready(function() {
   columnType[1]="integer";
   columnType[2]="currency";
   columnType[3]="string";
   columnType[4]="timestamp";
   columnType[5]="bool";
   
   mytable = $('#showlist').DataTable({
	   "paging": false,
	   "scrollX": true,
   });	   

	$.ajaxSetup({
        header: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

	$("button").click(function(event){
		
	    event.preventDefault();
	    var action=0;
	    //var postData = new FormData($("#mainform")[0]);

	    
		switch(this.id){
			case "test":
				action = 4;
				break;
			case "register":
				action = 1;
				break;
			case "change":
				action = 2;
				break;
			case "delete":
				action = 3;
				break;
			case "addcolxml":
				//alert($('#columns').val());
				var res= $('#columns').val().split("|");
				if($('#tr_'+res[1]).length<1){
					AddRow(res[0],res[1],res[2],res[3],res[4]);
					mytable.draw( false );
				}
				else
					alert("Column: "+res[1]+" already exists");
				return;
			case "cancel":
				ClearForm();
				return;
			case "exitmodal":
				return;
		}
		
		var postData = $("#mainform").serialize()+'&action='+action;

		$.ajax({
	        type: 'post',
	        url: 'xmlcreator',
	        data: postData,
	        success: function (val) {
	        	alert("The XML config created Successfully\n");
//		        	alert(val);
	        	$('#modalHeader').text("The XML "+$('#name').val()+" is as follow");
	        	$('#modalXML').text(val);
	        	$('#xmlModal').modal('toggle');
	        	$('#xmlModal').modal('show');
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
	

	$('select[name="project_id"]').on('change', function() {
		
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
		
		var dbid=$("#dbid").val();
		var tablename=$("#tablename").val();
		

	    mytable.order([]);
	    mytable.clear().draw();
		if (tablename!=""){
			$.ajax({
				type: 'GET',
				url: 'database/7',
				data: { dbid: dbid, tablename: tablename  },
				success: function(val){
					var obj = JSON.parse(val);
					var count = 0;
					$("#columns").append('<option value="0|rowid|1|0|">rowid</option>');
		            $.each(obj, function (id, value) {
		            	++count;
		            	$("#columns").append('<option value="'+count+'|'+value.colName+'|'+value.colType+'|'+value.isNull+'|'+(value.defaultVal==null?"&#x200B":value.defaultVal)+'">'+value.colName+'</option>');
		            	AddRow(count,value.colName,value.colType,value.isNull,value.defaultVal);

//		            	var buttons= '<img src="img/remove.png" onclick="deleteRow(\''+value.colName+'\')">';
//		                var defaultVal=(value.defaultVal==null?"&#x200B":value.defaultVal);
//
//		                mytable.row.add( [
//		                	'<input type="text" name="fieldname['+count+']" class="input2lable"  value="'+value.colName+'" readonly="readonly"/>',
//		                	'<input type="text" name="start['+count+']" value="'+count+'"class="editor-active" size="10">',
//		                	'<input type="text" name="end['+count+']" value="'+count+'"class="editor-active" size="10">',
//		                	'<input type="checkbox" name="iskey['+count+']" class="editor-active">',
//		                	'<input type="checkbox" name="isoptional['+count+']" class="editor-active">',
//		                	'<input type="checkbox" name="isexternal['+count+']" class="editor-active">',
//		                	'<input type="text" name="defaultval['+count+']" value="'+defaultVal+'" class="editor-active" size="15">',
//		                	'<input type="checkbox" id="islookup'+count+'" name="islookup['+count+']" class="editor-active" onchange="Lookup(this,'+count+')">'+
//		                							'<div id="lbutton'+count+'" style="display:none;">'+
//		                							'<img src="img/add.png" style="padding-left:4px;" onclick="AddLookupRow('+count+')">'+
//	                								'<img src="img/minus.png" style="padding-left:2px;" onclick="DelLookupRow('+count+')">'+
//	                								'</div>',
//		                	'<input type="text" name="coltype['+count+']" class="input2lable"  size="5" value="'+value.colType+'">',
//		                	'<input type="text" name="nullable['+count+']" class="input2lable" size="5" value="'+value.isNull+'">',
//		                	buttons
//		                    
//		                ] ).node().id="tr_" + value.colName;
	                
		            });
		            mytable.draw( false );
		            $('#modifycol').show();
		            $('#columns').trigger("chosen:updated");
		            $('#modifycol').hide();
				},
				error: function(val){
					alert("Problem Occured");
				}
			});
		}
	});	
	

	
	$("#filename").on('change',function(){
		var event = $(this);
	    var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xml)$/;
	    var regexCSV = /^([a-zA-Z0-9\s_\\.\-:])+(.csv)$/;
	    //Checks whether the browser supports HTML5
	    if (typeof (FileReader) == "undefined") 
	    	alert("Sorry! Your browser does not support HTML5!"); 
	    var reader = new FileReader();
	    //Checks whether the file is a valid xml or csv file
	    if (regex.test(event.val().toLowerCase())) {
	    	readerForXML(reader);
	    	reader.readAsText($("#filename")[0].files[0]);
	    }else if (regexCSV.test(event.val().toLowerCase())) {
	    	readerForCSV(reader);
	    	reader.readAsText($("#filename")[0].files[0]);
	    }else
	        alert("Please upload a valid XML or CSV file!");
	});
	
	
	$("#ispost").on('change',function(){
		if($(this).prop('checked')){
			$("#post").html(CreatePostTable('post'));
			$("#lbuttonpost").show();
			$("#post").show();
			
		}else{
			$("#post").hide();
			$("#lbuttonpost").hide();
		}
	});

	$("#isdefault").on('change',function(){
		if($(this).prop('checked')){
			$("#usedefault").hide();
			
		}else{
			$("#usedefault").show();
		}
	});

	$("#ismodifycol").on('change',function(){
		if($(this).prop('checked')){
			$("#modifycol").show();
			
		}else{
			$("#modifycol").hide();
		}
	});
	
	
    $('#modifycol').hide();
});


function readerForXML(reader){
    reader.onload = function (e) {
    	try{
    		var xmlDoc = $.parseXML(e.target.result);
            $(xmlDoc).find("generatedkey").each(function(id,value){
            	FillTexbox("generatedkey",$(value).text());
            });
            $(xmlDoc).find("generatedkeyseqname").each(function(id,value){
            	FillTexbox("generatedkeyseqname",$(value).text());
            });

            $(xmlDoc).find("refreshdata").each(function(id,value){
            	FillTexbox("refreshtimeout",$(value).attr('timeout'));
            	
        		if ($(value).attr('key')=="true"){
        			$("input[name=refreshdata][value='true']").prop('checked', true);
        		}
        		else{
        			$("input[name=refreshdata][value='false']").prop('checked', true);
    			}
            });
            unkhownRows="";
            
//            $(xmlDoc).find("load").each(function(id,value){
//            	alert(value.attr('type')+"\n"+
//        			value.attr('delimiter')+"\n"+
//        			value.attr('fullcache')+"\n"+
//        			value.attr('mode')+"\n"+
//        			value.attr('padline')+"\n"+
//        			value.attr('verifyfilekey')+"\n"
//            	);
//            }

            
            $(xmlDoc).find("field").each(function(id,value){
        		
//            	alert("key is: "+$(value).attr('iskey')+"\n"+
//            	"external is: "+$(value).attr('isexternal')+"\n"+
//            	"optional is: "+$(value).attr('isoptional')+"\n"+
//            	"column name is: "+$(value).find('name').text()+"\n"+
//            	"column defaultval is: "+$(value).find('default').text()+"\n"+
//            	"location start is: "+$(value).find('location').attr('start')+" end is: "+$(value).find('location').attr('end'));
            	var isLookup=false;
            	var lookup_query =RemoveAllSpaces($(value).find('lookup').find('query').text());
            	var lookup_fullcache = $(value).find('lookup').find('fullcache');
            	var lookup_insert = RemoveAllSpaces($(value).find('lookup').find('fullcatche').text());
            	var lookup_insertkey = RemoveAllSpaces($(value).find('lookup').find('insertkey').text());
            	if(lookup_query.length || lookup_fullcache.length || lookup_insert.length || lookup_insertkey.length){
            		isLookup=true;
//            		alert($(value).find('name').text().toLowerCase()+"\n"+
//            				lookup_query+"\n"+
//            				lookup_fullcache+"\n"+
//            				lookup_insert+"\n"+
//            				lookup_insertkey+"\n");
            	}
            	FillRowData($(value).find('name').text().toLowerCase(),$(value).find('location').attr('start'),
            			$(value).find('location').attr('end'),$(value).attr('iskey'),$(value).attr('isoptional'),
            			$(value).attr('isexternal'),$(value).find('default').text(),isLookup,lookup_query,
        				lookup_fullcache,lookup_insert,lookup_insertkey);
            	
            	//check the lookup
            	//trigger lookup
            	//for each lookup add proper
            });
            if (unkhownRows.length>0)
            	alert("Column which are not on table structure are\n"+unkhownRows)
    	}
    	catch(event){
    		alert("The file is not Valid XML");
    	}
    }  

}

function readerForCSV(reader){
	
	reader.onload = function(e){
		unkhownRows="";
		var lines = e.target.result.split(/\r?\n/);
	    for(var i = 0; i < lines.length; i++){

	    	var cols = lines[i].split(",");
		    if(cols.length==1)
		    	continue;
	    	if(cols.length!=7){
	    		
	    		alert("The file is not valid CSV");
	    		return;
	    	}
	    	FillRowByCSV(cols[0],cols[1],cols[2],cols[3],cols[4],cols[5],cols[6]);
	    }
	    if (unkhownRows.length>0)
	 	   alert("Column which are not on table structure are\n"+unkhownRows)
	};
}



function AddRow(rowNumber,colName,colType,isNull,defVal){
    var buttons= '<img src="'+imagePath+'/remove.png" onclick="deleteRow(\''+colName+'\')">';
    var defaultVal=(defVal==null?"&#x200B":defVal);
    mytable.row.add( [
    	'<input type="text" name="fieldname['+rowNumber+']" class="input2lable"  value="'+colName+'" readonly="readonly"/>',
    	'<input type="text" name="start['+rowNumber+']" value="'+rowNumber+'"class="editor-active" size="10">',
    	'<input type="text" name="end['+rowNumber+']" value="'+rowNumber+'"class="editor-active" size="10">',
    	'<input type="checkbox" name="iskey['+rowNumber+']" class="editor-active">',
    	'<input type="checkbox" name="isoptional['+rowNumber+']" class="editor-active">',
    	'<input type="checkbox" name="isexternal['+rowNumber+']" class="editor-active">',
    	'<input type="text" name="defaultval['+rowNumber+']" value="'+defaultVal+'" class="editor-active" size="15">',
    	'<input type="checkbox" id="islookup'+rowNumber+'" name="islookup['+rowNumber+']" class="editor-active" onchange="Lookup(this,'+rowNumber+')">'+
    							'<div id="lbutton'+rowNumber+'" style="display:none;">'+
    							'<img src="'+imagePath+'/add.png" style="padding-left:4px;" onclick="AddLookupRow('+rowNumber+')">'+
								'<img src="'+imagePath+'/minus.png" style="padding-left:2px;" onclick="DelLookupRow('+rowNumber+')">'+
								'</div>',
    	'<input type="text" name="coltype['+rowNumber+']" class="input2lable"  size="5" value="'+colType+'">',
    	'<input type="text" name="nullable['+rowNumber+']" class="input2lable" size="5" value="'+isNull+'">',
    	buttons
    ] ).node().id="tr_" + colName;
}


function Lookup(cb,id)
{
	//alert("salam: "+cb.checked);
	
    var tr = $(cb).closest('tr');
    var row = mytable.row( tr );
    
    if ( ! cb.checked ) {
        // close the lookup part
    	$("#lbutton"+id).hide();
        row.child.hide();
    }
    else {
        // Open the lookup part
    	$("#lbutton"+id).show();
        row.child(format(id)).show();
    }
}
    

function CreatePostTable(lookupid) {
    // `d` is the original data object for the row
//    return '<table cellpadding="0" cellspacing="0" border="0" width="100%" style="padding: 0 0 0 0;margin: 0 0 0 0">'+
    return '<table id="lookupTable'+lookupid+'" width="100%">'+
    	'<tr id="lookupRow'+lookupid+'_0">'+
            '<td width="15%">'+
            	'<select  name="lookuptype'+lookupid+'[]" id="lookuptype'+lookupid+'_0">'+
            		'<option value="" selected>Select Lookup</option>'+
            		'<option value="insertinsertion">insertinsertion</option>'+
            		'<option value="insertupdate">insertupdate</option>'+
            		'<option value="updateinsertion">updateinsertion</option>'+
            		'<option value="updateupdate">updateupdate</option>'+
        		'</select>'+
            '</td>'+
            '<td width="80%">'+
            	'<textarea placeholder="Query" class="boxsizingBorder" name="query'+lookupid+'[]" id="query'+lookupid+'_0"></textarea>'+
            '</td>'+
        '</tr>'+
    '</table>';
}

function format(lookupid) {
    // `d` is the original data object for the row
//    return '<table cellpadding="0" cellspacing="0" border="0" width="100%" style="padding: 0 0 0 0;margin: 0 0 0 0">'+
    return '<table id="lookupTable'+lookupid+'" width="100%">'+
    	'<tr id="lookupRow'+lookupid+'_0">'+
            '<td width="15%">'+
            	'<select  name="lookuptype'+lookupid+'[]" id="lookuptype'+lookupid+'_0">'+
            		'<option value="" selected>Select Lookup</option>'+
            		'<option value="query">query</option>'+
            		'<option value="fullcache">fullcache</option>'+
            		'<option value="insert">insert</option>'+
            		'<option value="insertkey">insertkey</option>'+
        		'</select>'+
            '</td>'+
            '<td width="80%">'+
            	'<textarea placeholder="Query" class="boxsizingBorder" name="query'+lookupid+'[]" id="query'+lookupid+'_0"></textarea>'+
            '</td>'+
        '</tr>'+
    '</table>';
}

function AddLookupRow(rowid){
	var table="#lookupTable"+rowid;
	//alert("str is: "+table+"and length is: "+$(table).length);
	var competCount = $(table+" tr").length;
	//alert("row count is:"+competCount);
	$("#lookupRow"+rowid+"_0").clone().appendTo(table)
    .attr("id","lookupRow"+rowid+"_"+competCount)
    .find("*")
    .each(function() {
        var id = this.id || "";
        var match = id.match(idregex) || [];
        if (match.length == 3) {
            this.id = match[1] + (competCount);
        }
    });

}

function DelLookupRow(rowid)
{
	var table="#lookupTable"+rowid;
	var competCount = $(table+" tr").length;
    if(competCount >1)
        $(table+' tr:last').remove();
}

function RemoveAllSpaces(str)
{
	return str.replace(/(\r\n|\n|\r)/gm,"").replace(/ +(?= )/g,'');
}

function FillTexbox(name,value){
	$("#"+name).val(value);
}
		                
function FillRowByCSV(id,start,end,isKey,isOptional,isExternal,defaultVal)
{
	var x="#tr_"+id;
	if($(x).length==0)
	{
		//alert("Column "+id+" is not in structure of this table");
		unkhownRows+=id+"\n";
	}
	else{
		$(x).find('td:nth-child(2) input[type="text"]').val(start);
		$(x).find('td:nth-child(3) input[type="text"]').val(end);
		
		if (isKey==1)
			$(x).find('td:nth-child(4) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(4) input[type="checkbox"]').prop('checked', false);

		if (isOptional==1)
			$(x).find('td:nth-child(5) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(5) input[type="checkbox"]').prop('checked', false);

		if (isExternal==1)
			$(x).find('td:nth-child(6) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(6) input[type="checkbox"]').prop('checked', false);
		$(x).find('td:nth-child(7) input[type="text"]').val(defaultVal);

	}
}



function FillRowData(id,start,end,iskey,isoptional,isexternal,defaultval,islookup,lookup_query,
		lookup_fullcache,lookup_insert,lookup_insertkey)
{
	var x="#tr_"+id;
	if($(x).length==0)
	{
		//alert("Column "+id+" is not in structure of this table");
		unkhownRows+=id+"\n";
	}
	else{
		$(x).find('td:nth-child(2) input[type="text"]').val(start);
		$(x).find('td:nth-child(3) input[type="text"]').val(end);
		if (iskey=="true")
			$(x).find('td:nth-child(4) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(4) input[type="checkbox"]').prop('checked', false);

		if (isoptional=="true")
			$(x).find('td:nth-child(5) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(5) input[type="checkbox"]').prop('checked', false);

		if (isexternal=="true")
			$(x).find('td:nth-child(6) input[type="checkbox"]').prop('checked', true);
		else
			$(x).find('td:nth-child(6) input[type="checkbox"]').prop('checked', false);
		$(x).find('td:nth-child(7) input[type="text"]').val(defaultval);

		
		var cbObject=$(x).find('td:nth-child(8) input[type="checkbox"]');
		if (islookup){

//			$(x).find('td:nth-child(8) input[type="checkbox"]').prop('checked', true);
//			$(x).find('td:nth-child(8) input[type="checkbox"]').trigger("change");
			cbObject.prop('checked', true);
			cbObject.trigger('change');

			var rowId=cbObject.attr('id').replace('islookup','');

			///finding row id

			var additionalRow=0;
			if(lookup_query.length){
				$("#lookuptype"+rowId+"_"+additionalRow).val("query");
				$("#query"+rowId+"_"+additionalRow).val(lookup_query);
				++additionalRow;
			}
			if(lookup_fullcache.length){
				if(additionalRow>0)
					AddLookupRow(rowId);
				$("#lookuptype"+rowId+"_"+additionalRow).val("fullcache");
				$("#query"+rowId+"_"+additionalRow).val("");
				++additionalRow;
				
			}
			if(lookup_insert.length){
				if(additionalRow>0)
					AddLookupRow(rowId);
				$("#lookuptype"+rowId+"_"+additionalRow).val("insert");
				$("#query"+rowId+"_"+additionalRow).val(lookup_insert.replace(/(\r\n|\n|\r)/gm,"").trim());
				++additionalRow;
				
			}
			if(lookup_insertkey.length){
				if(additionalRow>0)
					AddLookupRow(rowId);
				$("#lookuptype"+rowId+"_"+additionalRow).val("insertkey");
				$("#query"+rowId+"_"+additionalRow).val(lookup_insertkey.replace(/(\r\n|\n|\r)/gm,"").trim());
				++additionalRow;
				
			}

		}
		else{
			cbObject.prop('checked', false);
			cbObject.trigger("change");
		}

		
	}
}


function deleteRow(rowId){
	mytable.row($("#tr_"+rowId)).remove().draw();
}
