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
			remplir(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$("#insert").click(function(e) {
		e.preventDefault();
		var verif = true;
		var code = $("#codeI").val();
		var capacite = $("#capaciteI").val();
		var type = $("#typeI").val();
		if (code == "") {
			$("#codeI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#codeI").css("border", "1px solid #eaeaea");
		}

		if (capacite == "") {
			$("#capaciteI").css("border", "1px solid red");
			verif = false;
		} else {
			$("#capaciteI").css("border", "1px solid #eaeaea");
		}

		if (verif) {
			$.ajax({
				url: "SalleController",
				data: { op: "insert", code: code, capacite: capacite, type: type },
				type: 'POST',
				success: function(data, textStatus, jqXHR) {
					if (data != null) {
						$("#codeI").val("");
						$("#capaciteI").val("");
						$("#insert-salle").modal("hide");
						swal("Succès!", "Création de la salle avec succès!", "success").then(() => {
							remplir(data);
						});
					} else {
						swal("Echec!", "Echec lors de la création de la salle!", "warning");
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
		if ($("#type").is(":hidden")) {
			var recherche = $("#recherche").val();
		} else {
			var recherche = $("#type").val();
		}

		if (verif) {
			$.ajax({
				url: "SalleController",
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

	$("#champ").change(function() {
		if (this.value == "Type") {
			$("#recherche").attr("hidden", true);
			$("#type").attr("hidden", false);
		} else {
			$("#recherche").attr("hidden", false);
			$("#type").attr("hidden", true);
		}
	});

	$('#insert-salle').on('hidden.bs.modal', function() {
		$("#codeI").css("border", "1px solid #eaeaea");
		$("#capaciteI").css("border", "1px solid #eaeaea");
	});

	function remplir(data) {
		var ligne = "";
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				ligne += '<tr><td align="center">' + data[i].id + '</td><td align="center">' + data[i].code + '</td><td align="center">' + data[i].capacite + '</td><td align="center">' + data[i].type + '</td><td align="center"><div class="justify-content-center"><button class="btn btn-outline-dark btn-update" data-salle=\'' + JSON.stringify(data[i]) + '\' data-toggle="modal" data-target="#update-salle"><i class="icon icon-edit-72"></i></button><button class="btn btn-outline-danger btn-delete" data-id="' + data[i].id + '"><i class="icon icon-simple-remove"></i></button></div></td></tr>';
			}
		} else {
			ligne = '<td colspan="5" align="center"><p class="fs-2">Pas de salles !<p></td></tr>';
		}
		$("#salle-item").html(ligne);
		$(".btn-update").click(function() {
			var salle = $(this).data("salle");
			$("#salleIdU").val(salle.id);
			$("#codeU").val(salle.code);
			$("#capaciteU").val(salle.capacite);
			$("#typeU").val(salle.type);
		});

		$(".btn-delete").click(function() {
			swal({
				title: "Voulez-vous supprimer cette salle?",
				icon: "info",
				buttons: true,
				showcancelbutton: true,
			})
				.then((isConfirm) => {
					if (isConfirm) {
						var id = $(this).data("id");
						$.ajax({
							url: "SalleController",
							data: { op: "delete", id: id },
							type: 'POST',
							success: function(data, textStatus, jqXHR) {
								if (data != null) {
									swal("Succès!", "Suppression de la salle avec succès!", "success").then(() => {
										remplir(data);
									});
								} else {
									swal("Echec!", "Echec lors de la suppression de la salle!", "warning");
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
			var id = $("#salleIdU").val();
			var code = $("#codeU").val();
			var capacite = $("#capaciteU").val();
			var type = $("#typeU").val();
			if (code == "") {
				$("#codeU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#codeU").css("border", "1px solid #eaeaea");
			}

			if (capacite == "") {
				$("#capaciteU").css("border", "1px solid red");
				verif = false;
			} else {
				$("#capaciteU").css("border", "1px solid #eaeaea");
			}

			if (verif) {
				$.ajax({
					url: "SalleController",
					data: { op: "update", id: id, code: code, capacite: capacite, type: type },
					type: 'POST',
					success: function(data, textStatus, jqXHR) {
						if (data != null) {
							$("#update-salle").modal("hide");
							swal("Succès!", "Modification de la salle avec succès!", "success").then(() => {
								remplir(data);
							});
						} else {
							swal("Echec!", "Echec lors de la modification de la salle!", "warning");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log(errorThrown);
					}
				});
			}
		});

		$('#update-salle').on('hidden.bs.modal', function() {
			$("#codeU").css("border", "1px solid #eaeaea");
			$("#capaciteU").css("border", "1px solid #eaeaea");
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

