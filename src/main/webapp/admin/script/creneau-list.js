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
		url: "CreneauController",
		data: { op: "findAll" },
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			remplir(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$("#insert").click(function(e) {
		e.preventDefault();
		var verif = true;
		var heureDebut = $("#heureDebutI").val();
		var heureFin = $("#heureFinI").val();
		if (heureDebut == "") {
			$("#heureDebutI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#heureDebutI").css("border", "1px solid #eaeaea");
		}

		if (heureFin == "") {
			$("#heureFinI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#heureFinI").css("border", "1px solid #eaeaea");
		}

		if (verif) {
			$.ajax({
				url: "CreneauController",
				data: { op: "insert", heureDebut: heureDebut, heureFin: heureFin },
				type: 'POST',
				success: function(data, textStatus, jqXHR) {
					if (data != null) {
						$("#heureDebutI").val("");
						$("#heureFinI").val("");
						$("#insert-creneau").modal("hide");
						swal("Succès!", "Création du créneau avec succès!", "success").then(() => {
							remplir(data);
						});
					} else {
						swal("Echec!", "Echec lors de la création du créneau!", "warning");
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});
		}
	});

	$("#search").click(function() {
		var verif = true;
		var champ = $("#champ").val();
		var recherche = $("#recherche").val();

		if (verif) {
			$.ajax({
				url: "CreneauController",
				data: { op: "search", champ: champ, recherche: recherche },
				type: 'POST',
				success: function(data, textStatus, jqXHR) {
					if (data != null) {
						remplir(data);
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				}
			});
		}
	});

	$('#insert-creneau').on('hidden.bs.modal', function() {
		$("#heureDebutI").css("border", "1px solid #eaeaea");
		$("#heureFinI").css("border", "1px solid #eaeaea");
	});

	function remplir(data) {
		var ligne = "";
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<tr><td align="center">' + data[i].id + '</td><td align="center">' + data[i].heureDebut + '</td><td align="center">' + data[i].heureFin + '</td><td align="center"><div class="justify-content-center"><button class="btn btn-outline-dark btn-update" data-creneau=\'' + JSON.stringify(data[i]) + '\' data-toggle="modal" data-target="#update-creneau"><i class="icon icon-edit-72"></i></button><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div></td></tr>';
			}
		} else {
			ligne = '<td colspan="4" align="center"><p class="fs-2">Pas de créneaux !<p></td></tr>';
		}
		$("#creneau-item").html(ligne);
		$(".btn-update").click(function() {
			var creneau = $(this).data("creneau");
			$("#creneauIdU").val(creneau.id);
			$("#heureDebutU").val(creneau.heureDebut);
			$("#heureFinU").val(creneau.heureFin);
		});

		$(".btn-delete").click(function() {
			swal({
				title: "Voulez-vous supprimer ce créneau?",
				icon: "info",
				buttons: true,
				showcancelbutton: true,
			})
				.then((isConfirm) => {
					if (isConfirm) {
						var id = $(this).data("id");
						$.ajax({
							url: "CreneauController",
							data: { op: "delete", id: id },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Suppression du créneau avec succès!", "success").then(() => {
										remplir(data);
									});
								} else {
									swal("Echec!", "Echec lors de la suppression du créneau!", "warning");
								}
							},
							error: function(jqXHR, textStatus, errorThrown) {
								console.log(errorThrown);
							}
						});
					}
				});
		});



		$("#update").click(function(e) {
			e.preventDefault();
			var verif = true;
			var id = $("#creneauIdU").val();
			var heureDebut = $("#heureDebutU").val();
			var heureFin = $("#heureFinU").val();
			if (heureDebut == "") {
				$("#heureDebutU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#heureDebutU").css("border", "1px solid #eaeaea");
			}

			if (heureFin == "") {
				$("#heureFinU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#heureFinU").css("border", "1px solid #eaeaea");
			}

			if (verif) {
				$.ajax({
					url: "CreneauController",
					data: { op: "update", id: id, heureDebut: heureDebut, heureFin: heureFin },
					type: 'POST',
					success: function(data, textStatus, jqXHR) {
						if (data != null) {
							$("#update-creneau").modal("hide");
							swal("Succès!", "Modification du créneau avec succès!", "success").then(() => {
								remplir(data);
							});
						} else {
							swal("Echec!", "Echec lors de la modification du créneau!", "warning");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					}
				});
			}
		});

		$('#update-creneau').on('hidden.bs.modal', function() {
			$("#heureDebutU").css("border", "1px solid #eaeaea");
			$("#heureFinU").css("border", "1px solid #eaeaea");
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

