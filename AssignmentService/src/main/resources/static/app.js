function getAll() {
	$.ajax({
		url: "http://localhost:8080/getAllPhoneNumbers"
	}).then(function(data) {
		var tableData = '<table><tr><td>Id</td><td>Number</td><td>Status</td></tr>';
		$.each(data, function(index, data) {
			tableData += '<tr><td>' + data.customerID + '</td><td>' + data.phoneNumber + '</td><td>' + data.status + '</td><td>';
		});

		$('div').html(tableData);
	});
}

function getCustomerData() {
    $.ajax({
		url: "http://localhost:8080/getPhoneNumbers/"+ $('#customerId').val()
	}).then(function(data) {
		var tableData = '<table><tr><td>Id</td><td>Number</td><td>Status</td></tr>';
		$.each(data, function(index, data) {
			tableData += '<tr><td>' + data.customerID + '</td><td>' + data.phoneNumber + '</td><td>' + data.status + '</td><td>';
		});

		$('div').html(tableData);
	});
}

function updateCustomerData() {
	$.ajax({
		url: 'http://localhost:8080/activatePhoneNumbers/',
    dataType: 'json',
    type: 'patch',
    contentType: 'application/json',
    data: JSON.stringify( { "status": $('#status').val(), "customerID": $('#customerId').val(), "phoneNumber": $('#phoneNumber').val() } ),
    processData: false,
    success: function( data, textStatus, jQxhr ){
        $('#response pre').html( JSON.stringify( data ) );
    },
    error: function( jqXhr, textStatus, errorThrown ){
        console.log( errorThrown );
    }
	
	});
	
	
	
}


function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#getAll").click(function() {	
		getAll();
	});
	$("#getCustomerData").click(function() {
		getCustomerData();
	});
	$("#updateCustomerData").click(function() {
		updateCustomerData();
	});
});