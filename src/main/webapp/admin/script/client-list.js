$(document).ready(function() {
	$.ajax({
		url: "ClientController",
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
		var nom = $("#nomI").val();
		var prenom = $("#prenomI").val();
		var cin = $("#cinI").val();
		var password = $("#passwordI").val();
		if (nom == "") {
			$("#nomI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#nomI").css("border", "1px solid #eaeaea");
		}

		if (prenom == "") {
			$("#prenomI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#prenomI").css("border", "1px solid #eaeaea");
		}

		if (cin == "") {
			$("#cinI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#cinI").css("border", "1px solid #eaeaea");
		}

		if (password == "") {
			$("#passwordI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#passwordI").css("border", "1px solid #eaeaea");
		}

		if (verif) {
			$.ajax({
				url: "ClientController",
				data: { op: "insert", nom: nom, prenom: prenom, cin: cin, password: password },
				type: 'POST',
				success: function(data, textStatus, jqXHR) {
					if (data != null) {
						$("#nomI").val("");
						$("#prenomI").val("");
						$("#cinI").val("");
						$("#passwordI").val("");
						$("#insert-client").modal("hide");
						swal("Succès!", "Création du client avec succès!", "success").then(() => {
							remplir(data);
						});
					} else {
						swal("Echec!", "Echec lors de la création du client!", "warning");
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
				url: "ClientController",
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

	$('#insert-client').on('hidden.bs.modal', function() {
		$("#nomI").css("border", "1px solid #eaeaea");
		$("#prenomI").css("border", "1px solid #eaeaea");
		$("#cinI").css("border", "1px solid #eaeaea");
		$("#passwordI").css("border", "1px solid #eaeaea");
	});

	function remplir(data) {
		var ligne = "";
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<tr><td align="center">' + data[i].id + '</td><td align="center">' + data[i].nom + '</td><td align="center">' + data[i].prenom + '</td><td align="center">' + data[i].cin + '</td><td align="center">' + data[i].password + '</td><td align="center"><div class="justify-content-center"><button class="btn btn-outline-dark btn-update" data-client=\'' + JSON.stringify(data[i]) + '\' data-toggle="modal" data-target="#update-client"><i class="icon icon-edit-72"></i></button><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div></td></tr>';
			}
		} else {
			ligne = '<td colspan="6" align="center"><p class="fs-2">Pas de clients !<p></td></tr>';
		}
		$("#client-item").html(ligne);
		$(".btn-update").click(function() {
			var client = $(this).data("client");
			$("#clientIdU").val(client.id);
			$("#nomU").val(client.nom);
			$("#prenomU").val(client.prenom);
			$("#cinU").val(client.cin);
			$("#passwordU").val(client.password);
		});

		$(".btn-delete").click(function() {
			swal({
				title: "Voulez-vous supprimer ce client?",
				icon: "info",
				buttons: true,
				showcancelbutton: true,
			})
				.then((isConfirm) => {
					if (isConfirm) {
						var id = $(this).data("id");
						$.ajax({
							url: "ClientController",
							data: { op: "delete", id: id },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Suppression du client avec succès!", "success").then(() => {
										remplir(data);
									});
								} else {
									swal("Echec!", "Echec lors de la suppression du client!", "warning");
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
			var id = $("#clientIdU").val();
			var nom = $("#nomU").val();
			var prenom = $("#prenomU").val();
			var cin = $("#cinU").val();
			var password = $("#passwordU").val();
			if (nom == "") {
				$("#nomU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#nomU").css("border", "1px solid #eaeaea");
			}

			if (prenom == "") {
				$("#prenomU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#prenomU").css("border", "1px solid #eaeaea");
			}

			if (cin == "") {
				$("#cinU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#cinU").css("border", "1px solid #eaeaea");
			}

			if (password == "") {
				$("#passwordU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#passwordU").css("border", "1px solid #eaeaea");
			}

			if (verif) {
				$.ajax({
					url: "ClientController",
					data: { op: "update", id: id, nom: nom, prenom: prenom, cin: cin, password: password },
					type: 'POST',
					success: function(data, textStatus, jqXHR) {
						if (data != null) {
							$("#update-client").modal("hide");
							swal("Succès!", "Modification du client avec succès!", "success").then(() => {
								remplir(data);
							});
						} else {
							swal("Echec!", "Echec lors de la modification du client!", "warning");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					}
				});
			}
		});

		$('#update-client').on('hidden.bs.modal', function() {
			$("#nomU").css("border", "1px solid #eaeaea");
			$("#prenomU").css("border", "1px solid #eaeaea");
			$("#cinU").css("border", "1px solid #eaeaea");
			$("#passwordU").css("border", "1px solid #eaeaea");
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

