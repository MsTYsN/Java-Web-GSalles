<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% HttpSession s = request.getSession(false);
   if(session.getAttribute("admin") == null) {
	   response.sendRedirect("login.jsp");
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Focus - Bootstrap Admin Dashboard</title>
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="../custom/images/favicon.png">
<link href="../custom/vendor/pg-calendar/css/pignose.calendar.min.css"
	rel="stylesheet">
<link href="../custom/vendor/chartist/css/chartist.min.css"
	rel="stylesheet">
<link href="../custom/css/style.css" rel="stylesheet">
<link href="./css/style.css" rel="stylesheet">
</head>
<body>
	<%@include file="../template/header.jsp"%>
	<%@include file="../template/menu.jsp"%>


	<div class="content-body">
		<div class="container-fluid">
			<div class="row page-titles mx-0 justify-content-center">
				<div class="col-sm-6 p-md-0">
					<div class="row">
						<h4 class="mt-2">Liste des clients</h4>
						<button class="btn btn-outline-success ml-3" data-toggle="modal"
							data-target="#insert-client">
							<i class="icon icon-window-add"></i>
						</button>
					</div>
				</div>
			</div>
			<!-- row -->
			<div class="row justify-content-center">
				<div class="col-lg-6">
					<div class="card">
						<div class="card-body">
							<div class="input-group mb-3">
								<select class="form-control" id="champ">
									<option>Nom</option>
									<option>Prenom</option>
									<option>CIN</option>
								</select> <input type="text" class="form-control w-25" id="recherche"
									placeholder="Recherche par champ">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="search">Rechercher</button>
								</div>
							</div>
							<div class="table-responsive pre-scrollable">
								<table class="table table-hover">
									<thead>
										<tr align="center">
											<th>ID</th>
											<th>Nom</th>
											<th>Prénom</th>
											<th>CIN</th>
											<th>Mot de passe</th>
											<th class="text-end">Action</th>
										</tr>
									</thead>
									<tbody class="" id="client-item">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal fade none-border" id="insert-client">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">
								<strong>Ajouter un client</strong>
							</h4>
						</div>
						<form>
							<div class="modal-body">
								<div class="basic-form">
									<input hidden type="text" class="form-control input-default"
										id="clientIdI">
									<div class="form-group">
										<label>Nom <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="Nom" id="nomI">
									</div>
									<div class="form-group">
										<label>Prénom <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="Prénom" id="prenomI">
									</div>
									<div class="form-group">
										<label>CIN <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="CIN" id="cinI">
									</div>
									<div class="form-group">
										<label>Mot de passe <span class="required">*</span></label> <input
											type="type" class="form-control input-default"
											placeholder="Mot de passe" id="passwordI">
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default waves-effect"
									data-dismiss="modal">Annuler</button>
								<button type="submit"
									class="btn btn-success save-event waves-effect waves-light"
									id="insert">Ajouter</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal fade none-border" id="update-client">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">
								<strong>Modifer un client</strong>
							</h4>
						</div>
						<form>
							<div class="modal-body">
								<div class="basic-form">
									<input hidden type="text" class="form-control input-default"
										id="clientIdU">
									<div class="form-group">
										<label>Nom <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="Nom" id="nomU">
									</div>
									<div class="form-group">
										<label>Prénom <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="Prénom" id="prenomU">
									</div>
									<div class="form-group">
										<label>CIN <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="CIN" id="cinU">
									</div>
									<div class="form-group">
										<label>Mot de passe <span class="required">*</span></label> <input
											type="text" class="form-control input-default"
											placeholder="Mot de passe" id="passwordU">
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default waves-effect"
									data-dismiss="modal">Annuler</button>
								<button type="submit"
									class="btn btn-success save-event waves-effect waves-light"
									id="update">Modifier</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="../template/footer.jsp"%>

	<script src="../custom/vendor/global/global.min.js"></script>
	<script src="../custom/js/quixnav-init.js"></script>
	<script src="../custom/js/custom.min.js"></script>

	<script src="../custom/vendor/chartist/js/chartist.min.js"></script>

	<script src="../custom/vendor/moment/moment.min.js"></script>
	<script src="../custom/vendor/pg-calendar/js/pignose.calendar.min.js"></script>


	<script src="../custom/js/dashboard/dashboard-2.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

	<script src="./script/client-list.js"></script>
</body>
</html>