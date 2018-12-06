/**
 * 
 */

var mytable;
//var tableNames = [];

var pagePieChart;
var config = {
        type: 'doughnut',
        data: {
            datasets: [],
            labels: [
                "Valid",
                "Error",
            ]
        },
        options: {
            responsive: true
        },
        animation: {
            animateScale: true,
            onComplete: function() {
                var width = this.chart.width,
                    height = this.chart.height;

                // var fontSize = (1).toFixed(2);
                if(!(error==0 && total==0)){
                    this.chart.ctx.font = "bold "+1.5+"vh Time" ;
                    this.chart.ctx.fillStyle = "#1b6d74";
                    this.chart.ctx.textBaseline = "middle";
                    var text  = ((total-error)/total*100).toFixed(2)+"%",
                        textX = Math.round((width-this.chart.ctx.measureText(text).width) / 2 ),
                        textY = height / 2;
                    this.chart.ctx.fillText(text , textX, textY);
                }

            },
            animateRotate: false
        }
    };


$(document).ready(function() {

	  var ctx = document.getElementById("chart-area").getContext("2d");
	  pagePieChart = new Chart(ctx, config);

	$.ajaxSetup({
        header: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
    });

	$("button").click(function(event){
		
	    event.preventDefault();
	    var action=0;
	    var postData = new FormData($("#mainform")[0]);

		switch(this.id){
			case "check":
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
			case "cancel":
				ClearForm();
				return;
		}
		postData.append('action', action);
		HoldOn.open({theme:'sk-fading-circle'});
		$.ajax({
	        type: 'post',
	        url: 'validation',
	        contentType: false, // The content type used when sending data to the server.
	        data: postData,
	        cache: false,
	        processData: false,
	        success: function (val) {
	        	HoldOn.close();
	        	var obj = JSON.parse(val);
	        	$('#testTable').empty().append(
		         '<table id="showlist" class="table table-striped table-hover">'+
	             '<thead>'+
	             '<tr>'+
	             '</tr>'+
	             '</thead>'+
	             '</table>');

	        	$('#showlist thead tr').remove();
	        	$('#showlist thead').append('<tr></tr>');
	        	$.each(obj[0]["colTitle"], function (id,value) {
        			$('#showlist thead tr').append('<th>'+ value +'</th>');
        		});

	        	mytable = $('#showlist').DataTable({
	                "scrollX": true,
	      	      lengthMenu: [
	      	          [ 10, 50, 100, -1 ],
	      	          [ '10', '50', '100', 'All' ]
	      	    ]});
	        	mytable.clear().draw();
	        	$.each(obj[0]["rows"], function (id,value) {
	        		value.accuracy.unshift(value.rowNumber);
	        		mytable.row.add( 
	        			value.accuracy
        			 ).node().id="tr_" + value.rowNumber;
	        	});
	        	mytable.draw( false );
	        	$("#resultgraph").empty();
	            var pieChart= piechartdraw(obj[0]["error"],obj[0]["total"]);
	            $("#resultgraph").append(pieChart);
	        	var isFirst=true;

	        	//alert("khodafez");
			},
			error: function(val){
				HoldOn.close();

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
	
	


	
	$('select[name="project_id"]').on('change', function() {
		
		pid=$("#project_id").val();
		$('#dbid').empty().append('<option value="">Select Databases</option>');
		$('#validator_id').empty().append('<option value="">Select Validator</option>');
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
				},
				error: function(val){
					alert("Problem Occured");
				}
			});

			$.ajax({
				type: 'GET',
				url: 'validator/5',
				async: false,
				data: { pid: pid },
				success: function(val){
					var obj = JSON.parse(val);
					var validatorCombo =$("#validator_id");
			        $.each(obj, function (id, value) {
			        	validatorCombo.append('<option value="'+value.id+'">'+value.name+'</option>');
			        });
			        
			        //alert(optionOfTables);
				},
				error: function(val){
					alert("Problem Occured:\n"+val);
				}
			});
		}
	});
    var pieChart= piechartdraw(0,0);

});






function piechartdraw(error,total) {


    config.data.datasets.splice(0, 1);
    pagePieChart.update();

    var color = "red";
    if(error==0 && total==0){
    	color = "LightGray";
    }

    var newDataset = {
		legends: ["salam", "khodafez"],
        backgroundColor: ["#06c5ac",color],

        data: [],

    };

    if(!(error==0 && total==0)){
        newDataset.data.push(total-error);
        newDataset.data.push(error);
    }
    else{
    	newDataset.data.push(0);
    	newDataset.data.push(100);
    }

    config.data.datasets.push(newDataset);

    pagePieChart.update();
    $("#resGraphValid").text(total-error);
    $("#resGraphError").text(error);
    $("#resGraphTotal").text(total);
    
    //return canvas;
    

}


