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

    $.get("/investor/company/getAll", {
    		countryCode : result.exchange
    	}).done(function(data) {
    		var html = '';

            $('#companies-tbody').not(':first').not(':last').remove();

           	for(i=0; i<data.length; i++) {
            	html = html + addCompanyToTable(data[i]);
         	}

          	$('#companies-tbody').append(html);
    	}).fail(function() {
    		alert("The companies could not be completed.");
    });
});

function addCompanyToTable(data) {
	var tableRow = "<tr id='" + data.symbol + "'>";

	if(data.buyIndicator > 15) {
	    tableRow += "<td align='left' style='color:red;'><a href='/investor/company.html?symbol=" + data.tickerSymbol +"'>" +  data.tickerSymbol + " - " + data.name + "</a></td>";
	} else {
	   tableRow += "<td align='left' style='color:green;'><a href='/investor/company.html?symbol=" + data.tickerSymbol +"'>" +  data.tickerSymbol + " - " + data.name + "</a></td>";
	}
	
	if(data.buyIndicator > 22.5) {
		tableRow += "<td align='right' style='color:red;'>" + data.buyIndicator + "</td><td align='right'>";
	} else if(data.buyIndicator > 0 ) {
		tableRow += "<td align='right' style='color:green;'>" + data.buyIndicator + "</td><td align='right'>";
	} else {
		tableRow += "<td align='right'>" + data.buyIndicator + "</td><td align='right'>";
	}

	tableRow += data.lastTwoQuartersPE + "</td><td align='right'>";
    tableRow += data.priceEarning + "</td><td align='right'>";
	tableRow += data.priceBook + "</td><td align='right' style='color:blue;'>";
	tableRow += data.price + "</td><td align='right'>";
	tableRow += formatValue(data.marketCap) + "</td><td align='right'>";
	tableRow += formatValue(data.equity) + "</td><td align='right'>";
	tableRow += formatValue(data.totalDebt) + "</td><td align='right'>";
	tableRow += formatValue(data.workingCapital) + "</td><td align='right'>";
	tableRow += formatValue(data.cash) + "</td><td align='right'>";
	tableRow += "<a href='/investor/addCompanyNote.html?symbol=" + data.tickerSymbol + "'>Add Note</a></td></tr>";

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
