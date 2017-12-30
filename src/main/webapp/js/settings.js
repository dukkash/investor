$(document).ready(function() {
	$("#symbol-btn").click(function(e) {
		e.preventDefault();

		var symbol = $('#com-tic-symbol').val().trim();

		if (symbol == '') {
			alert("Field symbol cannot be empty!");
		} else {
			$.get("/investor/admin/addKapCompanyFinancialData", {
				tickerSymbol : symbol
			}).done(function(data) {
				alert("Process completed.");
			}).fail(function() {
				alert("The process could not be completed.");
			});
		}
	});

	$("#bist-upd-btn").click(function(e) {
		e.preventDefault();

		$.get("/investor/admin/updateBistCompPrices").done(function(data) {
			alert("Process completed.");
		}).fail(function() {
			alert("The process could not be completed.");
		});
	});
});