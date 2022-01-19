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
                    alert("Adresse email ou mot de passe invalide");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

    });
});

