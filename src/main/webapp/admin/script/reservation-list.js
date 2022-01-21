$(document).ready(function() {
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
		url: "SalleController",
		data: { op: "findAll" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirSalle(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
	$.ajax({
		url: "ClientController",
		data: { op: "findWithoutReservation" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirClient(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
	$.ajax({
		url: "CreneauController",
		data: { op: "findAll" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplirCreneau(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$("#show").click(function() {
		var verif = true;
		var today = new Date();
		var idSalle = $("#salle-item").val();
		var date = $("#date").val();

		if (date == "") {
			$("#date").css("border", "1px solid red");
			verif = false;
		} else {
			$("#date").css("border", "1px solid #eaeaea");
			var compareDate = new Date(Date.parse(date));
			if (compareDate < today) {
				swal("Echec!", "Date invalide!", "warning");
				verif = false;
			}
		}

		if (idSalle == null) {
			$("#salle-item").css("border", "1px solid red");
			verif = false;
		} else {
			$("#salle-item").css("border", "1px solid #eaeaea");
		}

		if (verif) {
			$.ajax({
				url: "OccupationController",
				data: { op: "findBySalle", idSalle: idSalle, date: date },
				type: 'POST',
				success: function(data, textStatus, jqXHR) {
					console.log("wtf");
					console.log(data);
					remplirOccupation(data);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});
		}
	});

	function remplirCreneau(data) {
		var ligne = "";
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<tr data-creneau="' + data[i].id + '"><td align="center"></td><td align="center"></td><td align="center"></td><td align="center"></td><td align="center">' + data[i].heureDebut + ' à ' + data[i].heureFin + '</td><td align="center"></td><td align="center"></td></tr>';
			}
		} else {
			ligne = '<td colspan="7" align="center"><p class="fs-2">Pas de créneaux !<p></td></tr>';
		}
		$("#reservation-item").html(ligne);
	}

	function remplirSalle(data) {
		var ligne = '<option selected disabled>Choisir une salle</option>';
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<option value="' + data[i].id + '">' + data[i].code + '</option>';
			}
		}
		$("#salle-item").html(ligne);
	}

	function remplirClient(data) {
		var ligne = '<option selected disabled>Choisir un client</option>';
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<option value="' + data[i].id + '">' + data[i].cin + '</option>';
			}
		}
		$("#client-item").html(ligne);
	}

	function remplirOccupation(data) {
		$("#reservation-item").children("tr").each(function() {
			$(this).children("td").eq(0).empty();
			$(this).children("td").eq(1).empty();
			$(this).children("td").eq(2).empty();
			$(this).children("td").eq(3).empty();
			$(this).children("td").eq(5).html("Libre");
			$(this).children("td").eq(6).html('<div class="justify-content-center"><button class="btn btn-outline-success btn-insert"><i class="icon icon-window-add"></i></button></div>');
		});
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				$("#reservation-item").children("tr").children("td")
				$("#reservation-item").children("tr").each(function() {
					var idCreneau = $(this).data("creneau");
					if (idCreneau == data[i].creneau.id) {
						$(this).children("td").eq(0).html(data[i].id);
						$(this).children("td").eq(1).html(data[i].date);
						$(this).children("td").eq(2).html(data[i].client.nom + " " + data[i].client.prenom);
						$(this).children("td").eq(3).html(data[i].salle.code);
						$(this).children("td").eq(5).html(data[i].etat);
						if (data[i].etat == "Réservé") {
							$(this).children("td").eq(6).html('<div class="justify-content-center"><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div>');
						} else {
							$(this).children("td").eq(6).html('<div class="justify-content-center"><button class="btn btn-outline-success btn-validate" data-id="' + data[i].id + '"><i class="icon icon-check-2"></i></button><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div>');
						}
					}
				});
			}
		} else {
			$("#reservation-item").children("tr").each(function() {
				$(this).children("td").eq(0).empty();
				$(this).children("td").eq(1).empty();
				$(this).children("td").eq(2).empty();
				$(this).children("td").eq(3).empty();
				$(this).children("td").eq(5).html("Libre");
				$(this).children("td").eq(6).html('<div class="justify-content-center"><button class="btn btn-outline-success btn-insert"><i class="icon icon-window-add"></i></button></div>');
			});
		}

		$(".btn-insert").click(function() {
			var verif = true;
			var today = new Date();
			var date = $("#date").val();
			var idClient = $("#client-item").val();
			var idSalle = $("#salle-item").val();
			var idCreneau = $(this).parents().eq(2).data("creneau");

			if (idClient == null) {
				$("#client-item").css("border", "1px solid red");
				verif = false;
			} else {
				$("#client-item").css("border", "1px solid #eaeaea");
			}

			if (date == "") {
				$("#date").css("border", "1px solid red");
				verif = false;
			} else {
				$("#date").css("border", "1px solid #eaeaea");
				var compareDate = new Date(Date.parse(date));
				if (compareDate < today) {
					swal("Echec!", "Date invalide!", "warning");
					verif = false;
				}
			}

			if (idClient == null) {
				$("#client-item").css("border", "1px solid red");
				verif = false;
			} else {
				$("#client-item").css("border", "1px solid #eaeaea");
			}

			if (verif) {
				swal({
					title: "Voulez-vous réserver cette salle pour le client choisi?",
					icon: "info",
					buttons: true,
					showcancelbutton: true,
				})
					.then((isConfirm) => {
						if (isConfirm) {
							$.ajax({
								url: "OccupationController",
								data: { op: "insertAdmin", date: date, idClient: idClient, idSalle: idSalle, idCreneau: idCreneau },
								type: 'POST',
								success: function(data, textStatus, jqXHR) {
									if (data != null) {
										swal("Succès!", "Réservation de la salle avec succès!", "success").then(() => {
											remplirOccupation(data);
											$.ajax({
												url: "ClientController",
												data: { op: "findWithoutReservation" },
												type: 'POST',
												success: function(data, textStatus, jqXHR) {
													remplirClient(data);
												},
												error: function(jqXHR, textStatus, errorThrown) {
													console.log(errorThrown);
												}
											});
										});
									} else {
										swal("Echec!", "Echec lors de la réservation de la salle!", "warning");
									}
								},
								error: function(jqXHR, textStatus, errorThrown) {
									console.log(errorThrown);
								}
							});
						}
					});
			}
		});

		$(".btn-validate").click(function() {
			var id = $(this).data("id");
			var idSalle = $("#salle-item").val();
			swal({
				title: "Voulez-vous valider la réservation de cette salle?",
				icon: "info",
				buttons: true,
				showcancelbutton: true,
			})
				.then((isConfirm) => {
					if (isConfirm) {
						$.ajax({
							url: "OccupationController",
							data: { op: "validate", id: id, idSalle: idSalle },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Validation de la réservation de la salle avec succès!", "success").then(() => {
										remplirOccupation(data);
									});
								} else {
									swal("Echec!", "Echec lors de la validation de la réservation de la salle!", "warning");
								}
							},
							error: function(jqXHR, textStatus, errorThrown) {
								console.log(errorThrown);
							}
						});
					}
				});
		});

		$(".btn-delete").click(function() {
			var id = $(this).data("id");
			var date = $("#date").val();
			var idSalle = $("#salle-item").val();
			swal({
				title: "Voulez-vous supprimer la réservation de cette salle?",
				icon: "info",
				buttons: true,
				showcancelbutton: true,
			})
				.then((isConfirm) => {
					if (isConfirm) {
						$.ajax({
							url: "OccupationController",
							data: { op: "delete", id: id, idSalle: idSalle, date: date },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Suppression de la réservation de la salle avec succès!", "success").then(() => {
										remplirOccupation(data);
										$.ajax({
											url: "ClientController",
											data: { op: "findWithoutReservation" },
											type: 'POST',
											success: function(data, textStatus, jqXHR) {
												remplirClient(data);
											},
											error: function(jqXHR, textStatus, errorThrown) {
												console.log(errorThrown);
											}
										});
									});
								} else {
									swal("Echec!", "Echec lors de la suppression réservation de la salle!", "warning");
								}
							},
							error: function(jqXHR, textStatus, errorThrown) {
								console.log(errorThrown);
							}
						});
					}
				});
		});
	}

	$("#disconnect").click(function() {
		$.ajax({
			url: "AdminController",
			data: { op: "disconnect" },
			type: 'POST',
			success: function(data, textStatus, jqXHR) {
				if (data == "1") {
					location.href = "index.jsp";
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});
	});
});

