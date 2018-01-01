$(document).ready(function() {

	$.get("/investor/account/getAll", function(data, status) {
		if (status == 'success') {
			fillAccountFields(data);
		} else {
			alert("No account found registered!");
		}
	});

	$.get("/investor/transaction/getTransactionTypes", function(data, status) {
    		if (status == 'success') {
    			fillTransactionTypeFields(data);
    		} else {
    			alert("No account found registered!");
    		}
    	});

	$("#add-trans-btn").click(function(e) {
		e.preventDefault();
		addTransaction();
	});

});


function fillTransactionTypeFields(data) {
	var options = "";

	for (var i = 0; i < data.length; i++) {
		options += '<option value="' + data[i].id + '">' + data[i].name
				+ '</option>';
	}

	$('#ttype').append(options);
}

function fillAccountFields(data) {
	var options = "";

	for (var i = 0; i < data.length; i++) {
		options += '<option value="' + data[i].id + '">' + data[i].name
				+ '</option>';
	}

	$('#account').append(options);
}

function addTransaction() {
	var account = $('#account').val();
	var ttype = $('#ttype').val();
	var amount = $('#amount').val();
	var transDate = $('#transaction-date').val();
	var description = $('#description').val();
	var isdebit = $('#isdebit').val();

	if (account == 0) {
		alert("Please select an account!");
	} else if (ttype == 0) {
     		alert("Please specify an t. type!");
    } else if (amount == '') {
		alert("Please specify an amount!");
	} else if (transDate == '') {
		alert("Please specify a transaction date!");
	} else if (description == '') {
		alert("Please specify a description!");
	} else { 
		var transactionObject = {
			"amount" : amount,
			"description": description,
			"date": transDate,
			"account": account,
			"transactionType": ttype,
			"isDebit": isdebit
		};
			
		jQuery.ajax ({
		    url: "/investor/transaction/addTransaction",
		    type: "POST",
		    data: JSON.stringify(transactionObject),
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    success: function(data){
		        alert(data);
		    }
		});

	}

}
