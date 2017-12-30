$(document).ready(function() {
	$.get("/investor/company/getEstimatedCompanies", function(data, status) {
		if (status == 'success') {
            var html = '';
            $('#companies-tbody').not(':first').not(':last').remove();

            for(i=0; i<data.length; i++) {
                html = html + addCompanyToTable(data[i]);
            }

            $('#companies-tbody').append(html);
        } else {
            alert("OUT" + status + "OUT");
        }
	});
});


function addCompanyToTable(data) {
	var tableRow = "<tr id='" + data.tickerSymbol + "'><td>";

	tableRow += data.tickerSymbol + " - " + data.companyName + "</td><td align='right'>";
    tableRow += data.currentPrice + "</td><td align='right' style='color:blue;'>";
    tableRow += Number(Math.round(data.price+'e2')+'e-2') + "</td><td align='right'>";
	tableRow += data.targetPrice + " (" +Math.round((((data.targetPrice - data.currentPrice)/data.currentPrice))*100) + "%)</td><td align='right'>";
	tableRow += data.targetDate + "</td><td align='right'>";
	tableRow += data.estimatedDate + "</td><td align='right'>";
	tableRow += data.estimatedBy + "</td><td align='left'>";

	if(data.description != '') {
	    tableRow += data.description + "</br>" + getNotes(data.notes) + "</td></tr>";
	} else {
	    tableRow += getNotes(data.notes) + "</td></tr>";
	}

	return tableRow;
}

function getNotes(notes){
    var result = "";

    for(j=0; j< notes.length; j++) {
        result +=  notes[j].importanceLevel.code + " - " + getDate(notes[j].issuedDate) +  " </br>- " + notes[j].note + "</br>";
    }

    return result;
}

function getDate(param) {
    var date = new Date(param);
    return date.getDate() + "." + date.getMonth() + "." + date.getFullYear();
}