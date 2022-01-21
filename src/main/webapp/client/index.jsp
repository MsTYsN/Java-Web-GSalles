<%@page import="beans.Client"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if (request.getSession(false).getAttribute("client") == null) {
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
<title>Accueil Client</title>
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="../custom/images/favicon.png">

<link href="../custom/css/style.css" rel="stylesheet">
<link href="./css/style.css" rel="stylesheet">
</head>
<body>
	<%@include file="../template/header.jsp"%>
	<%@include file="../template/menuClient.jsp"%>


	<div class="content-body">
		<div class="container-fluid">
			<div class="row page-titles mx-0 justify-content-center">
				<div class="col-sm-6 p-md-0">
					<div class="row">
						<h4 class="mt-2">Planning de réservation des salles</h4>
					</div>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-lg-9">
					<div class="card">
						<div class="card-body">
							<div class="input-group mb-3 w-50">
								<select class="form-control" id="salle-item">
								</select>
								<input class="form-control ml-2" type="date" id="date"> 
								<button class="btn btn-primary ml-2" type="button" id="show">Afficher</button>
								<input hidden type="text" class="form-control input-default"
									id="clientId" value="<%= request.getSession(false).getAttribute("client") %>">
							</div>
							<div class="table-responsive pre-scrollable">
								<table class="table table-hover">
									<thead>
										<tr align="center">
											<th class="w-25">Créneau</th>
											<th class="w75">Etat</th>
											<th class="text-end">Action</th>
										</tr>
									</thead>
									<tbody class="" id="planning-item">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="../template/footer.jsp"%>

	<script src="../custom/vendor/global/global.min.js"></script>
	<script src="../custom/js/quixnav-init.js"></script>
	<script src="../custom/js/custom.min.js"></script>


	<script src="../custom/vendor/moment/moment.min.js"></script>


	<script src="../custom/js/dashboard/dashboard-2.js"></script>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="./script/index.js"></script>


</body>
</html>