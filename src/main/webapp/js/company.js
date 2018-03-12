var parseQueryString = function(url) {
 	var urlParams = {};

 	url.replace(new RegExp("([^?=&]+)(=([^&]*))?", "g"), function($0, $1, $2, $3) {
 		urlParams[$1] = $3;
 	});

 	return urlParams;
 }

$(document).ready(function() {
	var urlToParse = location.search;
	var result = parseQueryString(urlToParse);

	$.get("/investor/company/getCompany", {
		tickerSymbol : result.symbol
	}).done(function(data) {
		var tableData = "";
		
		tableData += "<th style='text-align: left;' >" + result.symbol + "</th>";
		
		for (i = 0; i < data.length; i++) {
			tableData += "<th style='text-align: right;' >" + data[i].name + "</th>";
		}
				
		$('#com-table-headers').append(tableData);
		$('#comp-table-body').not(':first').not(':last').remove();		
		tableData = getQuarterData(data);
		
		$('#comp-table-body').append(tableData);
	}).fail(function() {
		alert("The process could not be completed.");
	});
});

function getQuarterData(data) {
	var tableRow = "";

	tableRow += "<tr><td><b>Ratios</b></td></tr><tr>";

	for (i = 0; i < data.length+1; i++) {
    	tableRow += "<td align='right'></td>";
    }

	tableRow += "<tr><td><b>BALANCE SHEET</b></td></tr><tr>";
	
	for (i = 0; i < data.length+1; i++) {
		tableRow += "<td align='right'></td>";
	}
		
	tableRow += "</tr><td>Equity</td>";
		
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.equity) + "</td>";
	}	
	
	tableRow += "</tr><td>&nbsp;Total Assets</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.totalAssets) + "</td>";
	}
	
	tableRow += "</tr><td>&nbsp;Total Liabilities</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.totalLiabilities) + "</td>";
	}
		
	tableRow += "</tr><td>&nbsp;&nbsp;Total Current Assets</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.totalCurrentAssets) + "</td>";
	}

    tableRow += "</tr><td>&nbsp;&nbsp;Total Current Liabilities</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.totalCurrentLiabilities) + "</td>";
	}
	
	tableRow += "</tr><td>Prepayments</td>";
			
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.prepayments) + "</td>";
	}
			
	tableRow += "</tr><td>Inventories</td>";
				
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.inventories) + "</td>";
	}

	tableRow += "</tr><td>Property, Plant & Equipment</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.propertyPlantEquipment) + "</td>";
	}

	tableRow += "</tr><td>Trade Receivables</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.tradeReceivables) + "</td>";
	}	
	
	tableRow += "</tr><td>Other Receivables</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.otherReceivables) + "</td>";
	}	
	
	tableRow += "</tr><td>Trade Payables</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.tradePayables) + "</td>";
	}	
	
	tableRow += "</tr><td>Other Payables</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.otherPayables) + "</td>";
	}
		
	tableRow += "</tr><td>Intangible Assets</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.intangibleAssets) + "</td>";
	}	
	
	tableRow += "</tr><td>Total Debt</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.totalDebt) + "</td>";
	}	
	
	tableRow += "</tr><td>&nbsp;&nbsp;&nbsp;Short Term Debt</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.shortTermDebt) + "</td>";
	}	
	
	tableRow += "</tr><td>&nbsp;&nbsp;&nbsp;Long Term Debt</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.longTermDebt) + "</td>";
	}	
	
	tableRow += "</tr><td>Cash</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].balanceSheet.cash) + "</td>";
	}	

	
	tableRow += "</tr><tr>";
	
	for (i = 0; i < data.length+1; i++) {
		tableRow += "<td align='right'></td>";
	}

	tableRow += "</tr><tr><td><b>INCOME STATEMENT</b></td></tr><tr>";
	
	for (i = 0; i < data.length+1; i++) {
		tableRow += "<td align='right'></td>";
	}
	
	tableRow += "</tr><td>Revenue</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.revenue) + "</td>";
	}	
	
	tableRow += "</tr><td>Cost Of Sales</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.costOfSales) + "</td>";
	}	
	
	tableRow += "</tr><td>Gross Profit</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.grossProfit) + 
		"</br>(<span  style='color:blue;'>" +Math.round(((data[i].incomeStatement.revenue + data[i].incomeStatement.costOfSales)/data[i].incomeStatement.revenue)*100 ) + "</span>)</td>";
	}
	
	tableRow += "</tr><td>Operating Profit</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.operatingProfit) + "</td>";
	}	
	
	tableRow += "</tr><td>Tax Expenses</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.taxExpenses) + "</td>";
	}	
	
	tableRow += "</tr><td>Finance Income</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.financeIncome) + "</td>";
	}	
	
	tableRow += "</tr><td>Finance Cost</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.financeCost) + "</td>";
	}	
	
	tableRow += "</tr><td>Interest Income</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.interestIncome) + "</td>";
	}	
	
	tableRow += "</tr><td>Interest Expenses</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.interestExpenses) + "</td>";
	}	
	
	tableRow += "</tr><td>Non-interest Income</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.nonInterestIncome) + "</td>";
	}	
	
	tableRow += "</tr><td>Net Profit</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].incomeStatement.netProfit) + "</td>";
	}	

	tableRow += "</tr><tr>";
	
	for (i = 0; i < data.length+1; i++) {
		tableRow += "<td align='right'></td>";
	}

	tableRow += "</tr><tr><td><b>CASH FLOW</b></td></tr><tr>";	

	for (i = 0; i < data.length+1; i++) {
		tableRow += "<td align='right'></td>";
	}
	
	tableRow += "</tr><td>Operating Activities Cash</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.operatingActivitiesCash) + "</td>";
	}		

	tableRow += "</tr><td>Investing Activities Cash</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.investingActivitiesCash) + "</td>";
	}		
		
	tableRow += "</tr><td>Financing Ativities Cash</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.financingAtivitiesCash) + "</td>";
	}

	tableRow += "</tr><td>Depr. & Amort. Exp.</td>";

    for (i = 0; i < data.length; i++) {
   		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.depAndAmrtExpenses) + "</td>";
   	}
	
	tableRow += "</tr><td>Interest Expences</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.interestExpences) + "</td>";
	}		
	
	tableRow += "</tr><td>Interest Income</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.interestIncome) + "</td>";
	}		
	
	tableRow += "</tr><td>Dividend Payment</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.dividendPayment) + "</td>";
	}		
	
	tableRow += "</tr><td>Debt Issued</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.debtIssued) + "</td>";
	}		
	
	tableRow += "</tr><td>Debt Payments</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.debtPayments) + "</td>";
	}		
	
	tableRow += "</tr><td>Liquidity</td>";
	
	for (i = 0; i < data.length; i++) {
		tableRow += "<td align='right'>" + formatValue(data[i].cashFlow.liquidity) + "</td>";
	}		
	
	
	
	
	tableRow += "</tr>";
	
	return tableRow;
}


function formatValue(value) {
    var newValue = value.toString();
    var counter = 0;
    var dotIndex = 0;
    var isNegativeNumber = false;
    var tempValue = '';

    if(value < 0) {
        isNegativeNumber = true;
    }

    if(newValue.length < 4) {
        return value;
    } else {
        if(value < 0) {
            dotIndex = (newValue.length-1) % 3;
            value = value * -1;
            newValue = value.toString();
            tempValue = '-';
        } else {
            dotIndex = newValue.length % 3;
        }
    }

    for(var i=0; i<newValue.length ; i++) {
        tempValue += newValue.charAt(i);
        dotIndex--;

        if(dotIndex == 0 && (i+1)<newValue.length ) {
            tempValue += '.';
            dotIndex = 3;
        } else if(dotIndex == -1) {
            dotIndex = 2;
        }
    }

    return tempValue;
}
