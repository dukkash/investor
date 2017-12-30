$(document).ready(function() {
	$("#search-btn").click(function(e) {
		e.preventDefault();
		getCompany();
	});

	$("#search-area").keypress(function(e) {
        if(e.which == 13) {
          // getCompany();
        }
    });
});

function getCompany(){
    var searchElement = $("#search-area").val().trim();

    if(searchElement == '') {
        alert("Please provide a ticker symbol or company name.");
        return;
    }
    $.get("/investor/company/checkCompany", {
    	    searchElement : searchElement
        }).done(function(data) {
            if(data != '') {
                window.location.href = "/investor/company.html?symbol=" + data;
            } else {
                alert("The company could not be found.");
            }

        }).fail(function() {
    	    alert("The company could not be found.");
    });

}