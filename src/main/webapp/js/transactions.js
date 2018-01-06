var dailyData = [];
var monthlyData = {};
var yearlyData = {};

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

    $.get("/investor/transaction/getAll", {
    		accountId : 0
    	}).done(function(data) {
    		var html = '';

            $('#transactions-tbody').not(':first').not(':last').remove();

           	for(i=0; i<data.length; i++) {
            	html = html + addTransactionsToTable(data[i]);
         	}

          	$('#transactions-tbody').append(html);
          	drawchart();
    	}).fail(function() {
    		alert("The transactions could not be retrieved.");
    });
});

function addTransactionsToTable(data) {
	var tableRow = "<tr>";
	tableRow += "<td align='left' style='color:blue;'><b>" + data.account.name + "</b> - " + data.account.currencyCode + "</td>";
	tableRow += "<td align='left'>" + formatDate(data.date) + "</td>";

	if(data.isDebit) {
	    tableRow += "<td align='right' style='color:green;'>" + data.amount + "</td><td align='right'>0.00</td>";
	} else {
        tableRow += "<td align='right'>0.00</td><td align='right' style='color:red;'>" + data.amount + "</td>";
	}

    tableRow += "<td align='left'>" + data.transactionType.name + "</td>";
    tableRow += "<td align='left'>" + data.description + "</td>";
	tableRow += "</tr>";

	return tableRow;
}

function formatDate(arg) {
    var date = new Date(arg);
    var strDate = date.getDate();

    if(date.getDate() < 10) {
        strDate = '0' + date.getDate();
    }

    if(date.getMonth() < 9) {
        strDate += '.0' + (date.getMonth()+1) + '.' + date.getFullYear();
    } else {
        strDate += '.' + (date.getMonth()+1) + '.' + date.getFullYear();
    }

    return strDate;
}

function drawchart() {
    Highcharts.chart('container', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Days'
        },
        xAxis: {
            categories: ['01.01.2018', '02.01.2018']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total amount'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                }
            }
        },
        series: [{
            name: 'Lunch',
            data: [5, 3]
        }, {
            name: 'Drinks',
            data: [2, 2]
        }, {
            name: 'Vegetables',
            data: [3, 4]
        }]
    });

}
