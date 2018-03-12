$(document).ready(function() {
	$("#add-data-btn").click(function(e) {
		e.preventDefault();
		addPeriod();
	});

});

function addPeriod() {

	var name = $('#name').val().trim();
	var tickerSymbol = $('#ticker-symbol').val().trim();
	var shares = $('#shares').val().replace(/[,|.]/g, '').trim();
	var earningsDate = $('#earnings-date').val().trim();

	var revenue = $('#revenue').val().replace(/[,|.]/g, '').trim();
	var grossProfit = $('#gross-profit').val().replace(/[,|.]/g, '').trim();

	var operationalProfit = $('#operational-profit').val().replace(/[,|.]/g, '').trim();
	var interestExpense = $('#interest-expenses').val().replace(/[,|.]/g, '').trim();
	var taxExpense = $('#tax-expenses').val().replace(/[,|.]/g, '').trim();
	var netProfit = $('#net-profit').val().replace(/[,|.]/g, '').trim();
	var interestIncome = $('#interest-income').val().replace(/[,|.]/g, '').trim();
	var nonInterestIncome = $('#non-interest-income').val().replace(/[,|.]/g, '').trim();
	var financeIncome = $('#finance-income').val().replace(/[,|.]/g, '').trim();
	var financeCost = $('#finance-cost').val().replace(/[,|.]/g, '').trim();

	var totalAssets = $('#total-assets').val().replace(/[,|.]/g, '').trim();
	var totalLiabilities = $('#total-liabilities').val().replace(/[,|.]/g, '').trim();
	var currentAssets = $('#current-assets').val().replace(/[,|.]/g, '').trim();
	var currentLiabilities = $('#current-liabilities').val().replace(/[,|.]/g,'').trim();
	var propertyPlantEquipment = $('#property-plant-equipment').val().replace(/[,|.]/g, '').trim();
	var inventories = $('#inventories').val().replace(/[,|.]/g, '').trim();
	var shortTermDebt = $('#short-term-debt').val().replace(/[,|.]/g, '').trim();
	var longTermDebt = $('#long-term-debt').val().replace(/[,|.]/g, '').trim();
	var cash = $('#cash').val().replace(/[,|.]/g, '').trim();
	var deposits = $('#deposits').val().replace(/[,|.]/g, '').trim();
	var loans = $('#loans').val().replace(/[,|.]/g, '').trim();
	var tradeReceivables = $('#trade-receivables').val().replace(/[,|.]/g, '').trim();
	var otherReceivables = $('#other-receivables').val().replace(/[,|.]/g, '').trim();
	var tradePayables = $('#trade-payables').val().replace(/[,|.]/g, '').trim();
    var otherPayables = $('#other-payables').val().replace(/[,|.]/g, '').trim();
    var prepayments = $('#prepayments').val().replace(/[,|.]/g, '').trim();
    var intangibleAssets = $('#intangible-assets').val().replace(/[,|.]/g, '').trim();

	var operatingActivitiesCash = $('#operating-activities-cash').val().replace(/[,|.]/g, '').trim();
	var investingActivitiesCash = $('#investing-activities-cash').val().replace(/[,|.]/g, '').trim();
	var financialActivitiesCash = $('#financial-activities-cash').val().replace(/[,|.]/g, '').trim();
	var debtRepaid = $('#debt-payments').val().replace(/[,|.]/g, '').trim();
	var dividendPayments = $('#dividend-payments').val().replace(/[,|.]/g, '').trim();
	var debtIssued = $('#debt-issued').val().replace(/[,|.]/g, '').trim();
	var interestPayments = $('#interest-payments').val().replace(/[,|.]/g, '').trim();
	var liquidity = $('#liquidity').val().replace(/[,|.]/g, '').trim();
	var notes = $('#notes').val();

	if (name == '') {
		alert("Field quarter name cannot be empty!");
	} else if (tickerSymbol == '') {
		alert("Field tickerSymbol cannot be empty!");
	} else if (shares == '') {
		alert("Field shares cannot be empty!");
	}

	// income statement
	else if (operationalProfit == '') {
      	alert("Field operationalProfit cannot be empty!");
    } else if (grossProfit == '') {
        alert("Field grossProfit cannot be empty!");
    } else if (netProfit == '') {
		alert("Field netProfit cannot be empty!");
	} else if (revenue == '') {
		alert("Field revenue cannot be empty!");
	} else if (financeIncome == '') {
      	alert("Field financeIncome cannot be empty!");
    } else if (financeCost == '') {
		alert("Field financeCost cannot be empty!");
	} else if (taxExpense == '') {
     	alert("Field taxExpense cannot be empty!");
     }


	// balance sheet
	else if (totalLiabilities == '') {
		alert("Field totalLiabilities cannot be empty!");
	} else if (currentAssets == '') {
		alert("Field currentAssets cannot be empty!");
	} else if (totalAssets == '') {
		alert("Field totalAssets cannot be empty!");
	} else if (currentLiabilities == '') {
		alert("Field currentLiabilities cannot be empty!");
	} else if (shortTermDebt == '') {
      	alert("Field shortTermDebt cannot be empty!");
    } else if (longTermDebt == '') {
     	alert("Field longTermDebt cannot be empty!");
    } else if (cash == '') {
     	alert("Field cash cannot be empty!");
    } else {

		var quarter = {
			"name" : name,
			"tickerSymbol" : tickerSymbol,
			"shares" : shares,
			"earningsDate" : earningsDate,

			"revenue" : revenue,
			"grossProfit" : grossProfit,

			"operationalProfit" : operationalProfit,
			"taxExpense" : taxExpense,
			"netProfit" : netProfit,
			"interestExpense" : interestExpense,
			"interestIncome" : interestIncome,
			"nonInterestIncome" : nonInterestIncome,
			"financeIncome" : financeIncome,
			"financeCost" : financeCost,

			"totalAssets" : totalAssets,
			"totalLiabilities" : totalLiabilities,
			"currentAssets" : currentAssets,
			"currentLiabilities" : currentLiabilities,
			"inventories" : inventories,
			"shortTermDebt" : shortTermDebt,
			"longTermDebt" : longTermDebt,
			"cash" : cash,
			"propertyPlantEquipment" : propertyPlantEquipment,
			"loans" : loans,
			"deposits" : deposits,
			"tradeReceivables" : tradeReceivables,
			"tradePayables": tradePayables,
			"otherPayables" : otherPayables,
			"otherReceivables" : otherReceivables,
			"prepayments": prepayments,
			"intangibleAssets" :intangibleAssets,

			"operatingActivitiesCash" : operatingActivitiesCash,
			"investingActivitiesCash" : investingActivitiesCash,
			"financialActivitiesCash" : financialActivitiesCash,

			"liquidity" : liquidity,
			"debtRepaid" : debtRepaid,
			"dividendPayments" : dividendPayments,
			"debtIssued" : debtIssued,
			"interestPayments" : interestPayments,
			"notes" : notes
		};

		jQuery.ajax({
					url : "/investor/period/addYearData",
					type : "POST",
					data : JSON.stringify(quarter),
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					complete : function(data) {
						if (data.status == 200) {
							alert("The period data was added successfully.");
						//	cleanFileds();
						} else {
							alert("Error code: " + data.status + " returned.");
						}
					}
				});
	}
}