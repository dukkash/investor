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

    $('#company-symbol').text(result.symbol);

    $.get("/investor/company/getImportanceLevels", function(data, status) {

		if (status == 'success') {
			fillImportanceLevels(data);
		} else {
			alert("No Importance Levels found!");
		}
	});

	$("#add-note-btn").click(function(e) {
		e.preventDefault();
		var note = $('#note').val();
		var importanceLevelId = $('#imp-level').val();

		if (note == '') {
        	alert("Please fill all required fields!");
        	return;
        }
        var note = {"symbol" : result.symbol, "note" : note, "importanceLevelId": importanceLevelId};

		jQuery.ajax({
        			url : "/investor/company/addCompanyNote",
        			type : "POST",
        			data : JSON.stringify(note),
        			dataType : "json",
        			contentType : "application/json; charset=utf-8",
        			complete : function(data) {
        				if (data.status == 200) {
        					alert("The Company note was added successfully.");
        				} else {
                            alert("Error code: " + data.status + " returned.");
                        }
        			}
        		});

	});

});

function fillImportanceLevels(data){
    var allLevels = "";

	for (var i = 0; i < data.length; i++) {
		allLevels += '<option value="' + data[i].id + '">' + data[i].code + '</option>';
	}

	$('#imp-level').append(allLevels);
}
