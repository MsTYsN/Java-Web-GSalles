<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
   if(request.getSession(false).getAttribute("client") != null) {
	   response.sendRedirect("index.jsp");
   }
%>
<!DOCTYPE html>
<html lang="en" class="h-100">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Authentification Client</title>
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="../custom/images/favicon.png">
    <link href="../custom/css/style.css" rel="stylesheet">

</head>

<body class="h-100">
    <div class="authincation h-100">
        <div class="container-fluid h-100">
            <div class="row justify-content-center h-100 align-items-center">
                <div class="col-md-6">
                    <div class="authincation-content">
                        <div class="row no-gutters">
                            <div class="col-xl-12">
                                <div class="auth-form">
                                    <h4 class="text-center mb-4">Authentification client</h4>
                                    <form action="">
                                        <div class="form-group">
                                            <label><strong>CIN</strong></label>
                                            <input type="text" id="cin" class="form-control" placeholder="CIN">
                                        </div>
                                        <div class="form-group">
                                            <label><strong>Mot de passe</strong></label>
                                            <input type="password" id="password" class="form-control" placeholder="Mot de passe">
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" id="connect" class="btn btn-primary btn-block">Se connecter</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!--**********************************
        Scripts
    ***********************************-->
    <!-- Required vendors -->
    <script src="../custom/vendor/global/global.min.js"></script>
    <script src="../custom/js/quixnav-init.js"></script>
    <script src="../custom/js/custom.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="./script/login.js"></script>
</body>

</html>