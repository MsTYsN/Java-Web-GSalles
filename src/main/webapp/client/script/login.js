$(document).ready(function () {
    $("#connect").click(function (e) {
		e.preventDefault();
        var cin = $("#cin").val();
        var password = $("#password").val();
        $.ajax({
            url: "ClientController",
            data: {op: "login",cin: cin, password: password},
            type: 'POST',
            success: function (data, textStatus, jqXHR) {
                if(data == "1") {
                    location.href = "index.jsp";
                }else {
                    alert("CIN ou mot de passe invalide");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

    });
});

