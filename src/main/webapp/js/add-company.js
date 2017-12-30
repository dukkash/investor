$(document).ready(function() {
	$.get("/investor/country/getAll", function(data, status) {

		if (status == 'success') {
			fillCountryField(data);
		} else {
			alert("No countries found registered!");
		}
	});
    
    $.get("/investor/companySector/getAll", function(data, status) {

		if (status == 'success') {
			fillCompanySectorField(data);
		} else {
			alert("No company sectors found registered!");
		}
	});
	
	$("#add-company-btn").click(function(e) {
		e.preventDefault();
		addCompany();
	});

	$("#reset-company-btn").click(function(e) {
		e.preventDefault();
		clearFields();	
	});
});

function clearFields() {
	$('#about').val("");
	$('#next-earnings-date').val("");
	$('#price').val("");
	$('#ticker-symbol').val("");
	$('#name').val("");  
	$('#yahoo-url').val("");
    $('#country').val("0");  
    $('#sector').val("0");
    $('#shares-outstanding').val('');
    $('#stock-url').val('');
}

function fillCountryField(data) {
	var allCountries = "";

	for (var i = 0; i < data.length; i++) {
		allCountries += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
	}

	$('#country').append(allCountries);
}

function fillCompanySectorField(data) {
	var options = "";

	for (var i = 0; i < data.length; i++) {
		options += '<option value="' + data[i].id + '">' + data[i].name
				+ '</option>';
	}

	$('#sector').append(options);
}

function addCompany() {
	var about = $('#about').val();
	var price = $('#price').val();
	var tickerSymbol = $('#ticker-symbol').val();
	var name = $('#name').val();	
	var sector = $('#sector').val();
	var country = $('#country').val();
    var stockUrl = $('#stock-url').val();
    var nextEarningsDate = $('#next-earnings-date').val();
    var sharesOutstanding = $('#shares-outstanding').val();

	if (tickerSymbol == '' || country == '0' || price == '' || sharesOutstanding == ''
			|| name == '' || sector == '0' || stockUrl == '') {
		alert("Please fill all required fields!");
	} else {
		var company = {
			"name" : name,
			"sector" : sector,
			"about" : about,
			"tickerSymbol" : tickerSymbol,
			"price" : price,
			"stockUrl": stockUrl,
            "country": country,
            "nextEarningsDate": nextEarningsDate,
            "sharesOutstanding": sharesOutstanding
		};

		jQuery.ajax({
			url : "/investor/company/addNewCompany",
			type : "POST",
			data : JSON.stringify(company),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			complete : function(data) {
				if (data.status == 200) {
					alert("The Company was added successfully.");
					cleanFileds();
				} else {
                    alert("Error code: " + data.status + " returned.");
                }
			}
		});
	}

}
