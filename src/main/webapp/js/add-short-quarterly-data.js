$(document).ready(function() {
	$("#add-short-quarter-btn").click(function(e) {
		e.preventDefault();
		addQuarterlyData();
	});
});

function addQuarterlyData() {
	var name = $('#name').val().trim();
	var tickerSymbol = $('#ticker-symbol').val().trim();
	var shares = $('#shares').val().replace(/[,|.]/g, '').trim();
	var revenue = $('#revenue').val().replace(/[,|.]/g, '').trim();
	var netProfit = $('#net-profit').val().replace(/[,|.]/g, '').trim();
	var tAssets = $('#t-assets').val().replace(/[,|.]/g, '').trim();
	var tLiabilities = $('#t-liabilities').val().replace(/[,|.]/g, '').trim();
	var equity = $('#equity').val().replace(/[,|.]/g, '').trim();
	var notes = $('#notes').val();

	if (name == '') {
		alert("Field quarter name cannot be empty!");
	} else if (tickerSymbol == '') {
		alert("Field ticker symbol cannot be empty!");
	} else if (shares == '') {
		alert("Field shares cannot be empty!");
	} else if (revenue == '') {
		alert("Field revenue cannot be empty!");
	} else if (netProfit == '') {
      	alert("Field net profit cannot be empty!");
    } else if (tAssets == '') {
		alert("Field total assets cannot be empty!");
	} else if (tLiabilities == '') {
     		alert("Field t. liabilities cannot be empty!");
    } else if (equity == '') {
     		alert("Field equity cannot be empty!");
    } else {
		var quarter = {
			"name" : name,
			"tickerSymbol" : tickerSymbol,
			"shares" : shares,
			"revenue" : revenue,
			"equity" : equity,
			"netProfit" : netProfit,
			"notes" : notes,
			"totalAssets": tAssets,
			"totalLiabilities": tLiabilities
		};

		jQuery.ajax({
					url : "/investor/quarterlyData/addShortQuarterlyData",
					type : "POST",
					data : JSON.stringify(quarter),
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					complete : function(data) {
						if (data.status == 200) {
							alert("The short quarterly data was added successfully.");
						} else {
							alert("Error code: " + data.status + " returned.");
						}
					}
				});
	}
}