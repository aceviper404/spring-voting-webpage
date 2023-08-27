<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Realtime Data</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="/webjars/bootstrap/5.1.1/css/bootstrap.min.css">
<script>
	function updateTable() {
		$.ajax({
			url : "/getCandidates", // Controller mapping to get candidates data
			method : "GET",
			dataType : "json",
			success : function(data) {
				var tableHtml = "";
				$.each(data, function(index, candidate) {
					tableHtml += "<tr><td>" + candidate.name + "</td><td>"
							+ candidate.numberOfVotes + "</td></tr>";
				});

				$("#candidateTable").html(tableHtml);
			}
		});
	}
	$(document).ready(function() {
		updateTable();
		setInterval(updateTable, 1000); // Update every 5 seconds
		
		$("#downloadButton").click(function() {
	        // Make an AJAX request to trigger the CSV download
	        $.ajax({
	            url: "/downloadCSV", // Controller mapping to trigger CSV download
	            method: "GET",
	            xhrFields: {
	                responseType: 'blob' // Set response type to blob
	            },
	            success: function(data) {
	                // Create a blob URL from the response data
	                var blob = new Blob([data], { type: 'application/csv' });
	                var blobUrl = URL.createObjectURL(blob);

	                // Create a temporary anchor element to trigger the download
	                var downloadLink = document.createElement('a');
	                downloadLink.href = blobUrl;
	                downloadLink.download = 'candidates.csv';
	                document.body.appendChild(downloadLink);
	                downloadLink.click();
	                document.body.removeChild(downloadLink);

	                // Clean up the blob URL
	                URL.revokeObjectURL(blobUrl);
	            }
	        });
	    });
	});
	
	
</script>
</head>
<body>
	<div class="container-sm">
		<div class="row">
			<div class="col">
				<div class="downloadContainer" style=" display: flex; flex-direction: row-reverse;">
					<button id="downloadButton" class="btn btn-primary"
						style="margin: 10px;">Download
						CSV</button>
				</div>
				<table
					class='table table-striped table-hover table-bordered text-center col-6 table-striped'>
					<tr>
						<th>Name</th>
						<th>Number of Votes</th>
					</tr>
					<tbody id="candidateTable"></tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>