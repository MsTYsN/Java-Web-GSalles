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
<meta charset="utf-8">
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
</head>
<body>
	<%@include file="../template/header.jsp"%>
	<%@include file="../template/menu.jsp"%>
	<%@include file="../template/footer.jsp"%>

	<script src="../custom/vendor/global/global.min.js"></script>
	<script src="../custom/js/quixnav-init.js"></script>
	<script src="../custom/js/custom.min.js"></script>

	<script src="../custom/vendor/apexcharts/apexcharts.min.js"></script>

	<script src="../custom/vendor/moment/moment.min.js"></script>
	<script src="../custom/vendor/pg-calendar/js/pignose.calendar.min.js"></script>


	<script src="../custom/js/dashboard/dashboard-2.js"></script>
</body>
</html>