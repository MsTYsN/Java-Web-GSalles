$(document).ready(function() {
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

	$("#salle-item").change(function() {
		var idSalle = this.value;
		$.ajax({
			url: "OccupationController",
			data: { op: "findBySalle", idSalle: idSalle },
			type: 'POST',
			success: function(data, textStatus, jqXHR) {
				remplirOccupation(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});
	});

	function remplirCreneau(data) {
		var ligne = "";
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<tr data-creneau="' + data[i].id + '"><td align="center" class="w-25">' + data[i].heureDebut + ' à ' + data[i].heureFin + '</td><td align="center" class="w-50"></td><td align="center" class="w-25"></td></tr>';
			}
		} else {
			ligne = '<td colspan="6" align="center"><p class="fs-2">Pas de créneaux !<p></td></tr>';
		}
		$("#planning-item").html(ligne);
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

	function remplirOccupation(data) {
		$("#planning-item").children("tr").each(function() {
			$(this).children("td").eq(1).html("Libre");
			$(this).children("td").eq(2).html('<div class="justify-content-center"><button class="btn btn-outline-success btn-insert"><i class="icon icon-window-add"></i></button></div>');
		});
		var idClient = $("#clientId").val();
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				$("#planning-item").children("tr").each(function() {
					var idCreneau = $(this).data("creneau");
					if (idCreneau == data[i].creneau.id) {
						$(this).children("td").eq(1).html(data[i].etat);
						if (idClient == data[i].client.id) {
							$(this).children("td").eq(2).html('<div class="justify-content-center"><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div>');
						} else {
							$(this).children("td").eq(2).empty();
						}
					}
				});
			}
		} else {
			$("#planning-item").children("tr").each(function() {
				$(this).children("td").eq(1).html("Libre");
				$(this).children("td").eq(2).html('<div class="justify-content-center"><button class="btn btn-outline-success btn-insert"><i class="icon icon-window-add"></i></button></div>');
			});
		}

		$(".btn-insert").click(function() {
			var verif = true;
			var date = $("#date").val();
			var idClient = $("#clientId").val();
			var idSalle = $("#salle-item").val();
			var idCreneau = $(this).parents().eq(2).data("creneau");
			if (date == "") {
				$("#date").css("border", "1px solid red");
				verif = false;
			} else {
				$("#date").css("border", "1px solid #eaeaea");
			}

			if (verif) {
				$.ajax({
					url: "ClientController",
					data: { op: "findWithoutReservation" },
					type: 'POST',
					success: function(data, textStatus, jqXHR) {
						if (data.length > 0) {
							var exists = false;
							for (var i = 0; i < data.length; i++) {
								if (idClient == data[i].id) {
									swal({
										title: "Voulez-vous réserver cette salle?",
										icon: "info",
										buttons: true,
										showcancelbutton: true,
									})
										.then((isConfirm) => {
											if (isConfirm) {
												$.ajax({
													url: "OccupationController",
													data: { op: "insert", date: date, idClient: idClient, idSalle: idSalle, idCreneau: idCreneau },
													type: 'POST',
													success: function(data, textStatus, jqXHR) {
														if (data != null) {
															swal("Succès!", "Réservation de la salle avec succès, en attente de la confirmation!", "success").then(() => {
																remplirOccupation(data);
															});
														} else {
															swal("Echec!", "Echec lors de la réservation de la salle!", "warning");
														}
														exists = true;
														break;
													},
													error: function(jqXHR, textStatus, errorThrown) {
														console.log(errorThrown);
													}
												});
											}
										});
								}
							}
							if(!exists) {
								swal("Echec!", "Vous avez déjà réservé une salle!", "warning");
							}
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					}
				});
			}
		});

		$(".btn-delete").click(function() {
			var id = $(this).data("id");
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
							data: { op: "delete", id: id, idSalle: idSalle },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Suppression de la réservation de la salle avec succès!", "success").then(() => {
										remplirOccupation(data);
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
});

