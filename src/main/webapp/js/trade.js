$(document).ready(function() {

	$.get("http://localhost:8080/account/getAll", function(data, status) {
		if (status == 'success') {
			fillAccountFields(data);
		} else {
			alert("No account found registered!");
		}
	});

	$("#add-trade-btn").click(function(e) {
		e.preventDefault();
		addTransaction();
	});

	$('input:radio[name="buySell"]').change(function() {
		if (this.id == 'buy') {
            $('#account').empty();
            $('#related-account').empty();
            $('#account').append(exchangeAccounts);	
	        $('#related-account').append(myBrokerAccount);
		} else {
            $('#account').empty();
            $('#related-account').empty();
            $('#account').append(myBrokerAccount);	
	        $('#related-account').append(exchangeAccounts);
		}
	});

});

var exchangeAccounts = "";// with code EXC
var myBrokerAccount = ""; // with code MBR

function fillAccountFields(data) {
	var options = "";

	for (var i = 0; i < data.length; i++) {
		if (data[i].accountType.code == 'MBR') {
			myBrokerAccount += '<option value="' + data[i].id + '">'
				+ data[i].name + '</option>';
		} else if (data[i].accountType.code == 'EXC') {
			exchangeAccounts += '<option value="' + data[i].id + '">'
				+ data[i].name + '</option>';
		}
	}


	$('#account').append(exchangeAccounts);	
	$('#related-account').append(myBrokerAccount);
}

function addTransaction() {

	var accountVal = $('#account').val();
	var relatedAccountVal = $('#related-account').val();
	var amount = $('#amount').val();
	var transDate = $('#transaction-date').val();
	var description = $('#description').val();
	var exchange = $('#exchange').val();

	if (accountVal == 0 || relatedAccountVal == 0) {
		alert("Please select an account!");
	} else if (amount == '') {
		alert("Please specify an amount!");
	} else if (transDate == '') {
		alert("Please specify a transaction date!");
	} else if (description == '') {
		alert("Please specify a description!");
	} else {
		var transactionObject = {
			"amount" : amount,
			"description" : description,
			"transactionDate" : transDate,
			"accountId" : accountVal,
			"relatedAccountId" : relatedAccountVal,
			"incoming" : true,
			"exchange" : exchange
		};

		jQuery.ajax({
			url : "/addTransactionEntity",
			type : "POST",
			data : JSON.stringify(transactionObject),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				alert(data);
			}
		});

	}

}
