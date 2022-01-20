$(document).ready(function () {
    $("#connect").click(function (e) {
		e.preventDefault();
        var email = $("#email").val();
        var password = $("#password").val();
        $.ajax({
            url: "AdminController",
            data: {op: "login",email: email, password: password},
            type: 'POST',
            success: function (data, textStatus, jqXHR) {
                if(data == "1") {
                    location.href = "index.jsp";
                }else {
					swal("Echec!", "Adresse email ou mot de passe invalide!", "warning");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

    });
});

