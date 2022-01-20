<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if (request.getSession(false).getAttribute("admin") == null) {
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Accueil Admin</title>
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="../custom/images/favicon.png">
<link href="../custom/vendor/pg-calendar/css/pignose.calendar.min.css"
	rel="stylesheet">
<link href="../custom/vendor/chartist/css/chartist.min.css"
	rel="stylesheet">
<link href="../custom/css/style.css" rel="stylesheet">
</head>
<body>
	<%@include file="../template/header.jsp"%>
	<%@include file="../template/menu.jsp"%>
	<%@include file="../template/footer.jsp"%>

	<div class="content-body">
		<div class="container-fluid">
			<div class="row">
				<div class="col-12">
					<div class="row">
						<div class="col-lg-6 col-sm-6">
							<div class="card">
								<div class="card-header">
									<h4 class="card-title">Les salles les plus réservés</h4>
								</div>
								<div class="card-body">
									<canvas id="barChart_1"></canvas>
								</div>
							</div>
						</div>
						<div class="col-lg-6 col-sm-6">
							<div class="card">
								<div class="card-header">
									<h4 class="card-title">Nombre de réservations par mois</h4>
								</div>
								<div class="card-body">
									<canvas id="barChart_2"></canvas>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="../custom/vendor/global/global.min.js"></script>
	<script src="../custom/js/quixnav-init.js"></script>
	<script src="../custom/js/custom.min.js"></script>

	<script src="../custom/vendor/chart.js/Chart.bundle.min.js"></script>

	<script src="../custom/vendor/moment/moment.min.js"></script>
	<script src="../custom/vendor/pg-calendar/js/pignose.calendar.min.js"></script>

	<script src="../custom/js/dashboard/dashboard-2.js"></script>

	<script src="./script/index.js"></script>
</body>
</html>