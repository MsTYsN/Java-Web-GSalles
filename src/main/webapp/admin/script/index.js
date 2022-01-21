(function($) {
	"use strict"

	//basic bar chart

	const barChart_1 = document.getElementById("barChart_1").getContext('2d');
	const barChart_2 = document.getElementById("barChart_2").getContext('2d');

	barChart_1.height = 200;
	barChart_2.height = 200;

	function remplirMostReserved(data) {
		var i = 0;
		var salles = new Array(data.length);
		var counters = new Array(data.length);
		$.each(data, function(key, value) {
			salles[i] = key;
			counters[i] = value;
			i = i + 1;
		});

		new Chart(barChart_1, {
			type: 'bar',
			data: {
				defaultFontFamily: 'Poppins',
				labels: salles,
				datasets: [
					{
						label: "",
						data: counters,
						borderColor: 'rgba(26, 51, 213, 1)',
						borderWidth: "0",
						backgroundColor: 'rgba(26, 51, 213, 1)'
					}
				]
			},
			options: {
				legend: false,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true
						}
					}],
					xAxes: [{
						// Change here
						barPercentage: 0.5
					}]
				}
			}
		});
	}

	function remplirMonthReserved(data) {
		var i = 0;
		var months = new Array(data.length);
		var counters = new Array(data.length);
		$.each(data, function(key, value) {
			months[i] = key;
			counters[i] = value;
			i = i + 1;
		});

		new Chart(barChart_2, {
			type: 'bar',
			data: {
				defaultFontFamily: 'Poppins',
				labels: months,
				datasets: [
					{
						label: "",
						data: counters,
						borderColor: 'rgba(26, 51, 213, 1)',
						borderWidth: "0",
						backgroundColor: 'rgba(26, 51, 213, 1)'
					}
				]
			},
			options: {
				legend: false,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true
						}
					}],
					xAxes: [{
						// Change here
						barPercentage: 0.5
					}]
				}
			}
		});
	}

	$.ajax({
		url: "AdminController",
		data: { op: "display" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			console.log(data);
			$("#displayName").html("Bienvenue " + data.nom + " " + data.prenom);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$.ajax({
		url: "OccupationController",
		data: { op: "mostReserved" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirMostReserved(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$.ajax({
		url: "OccupationController",
		data: { op: "mostReserved" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirMostReserved(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$.ajax({
		url: "OccupationController",
		data: { op: "monthReserved" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirMonthReserved(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$("#disconnect").click(function() {
		$.ajax({
			url: "AdminController",
			data: { op: "disconnect" },
			type: 'POST',
			success: function(data, textStatus, jqXHR) {
				if(data == "1") {
					location.href="index.jsp";
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});
	});
})(jQuery);