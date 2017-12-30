$(document).ready(function() {

	$("#add-estimation-btn").click(function(e) {
		e.preventDefault();
		addEstimation();
	});

	$("#reset-estimation-btn").click(function(e) {
    		e.preventDefault();
    	$('#ticker-symbol').val("");
    	$('#current-price').val("");
    	$('#estimated-date').val("");
    	$('#estimated-by').val("");
    	$('#description').val("");
    	$('#target-price').val("");
    	$('#target-date').val("");
    });

});

function addEstimation() {

	var tickerSymbol = $('#ticker-symbol').val();
	var currentPrice = $('#current-price').val();
	var estimatedDate = $('#estimated-date').val();
	var estimatedBy = $('#estimated-by').val();
	var description = $('#description').val();
	var targetPrice = $('#target-price').val();
	var targetDate = $('#target-date').val();

	 if (tickerSymbol == '') {
		alert("Please specify an ticker symbol!");
	} else if (estimatedBy == '') {
		alert("Please specify a estimatedBy name!");
	} else if (targetPrice == '') {
		alert("Please specify a target price!");
	} else {
		var estimation = {
			"tickerSymbol" : tickerSymbol,
			"currentPrice" : currentPrice,
			"estimatedDate" : estimatedDate,
			"estimatedBy" : estimatedBy,
			"description" : description,
			"targetPrice" : targetPrice,
			"targetDate" : targetDate
		};

		jQuery.ajax({
			url : "/investor/company/addEstimation",
			type : "POST",
			data : JSON.stringify(estimation),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete : function(data) {
            	if (data.status == 200) {
            		alert("The estimation was added successfully.");
            	} else {
                    alert("Error code: " + data.status + " returned.");
                }
            }
		});

	}

}
