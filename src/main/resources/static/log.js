
function guardarNombre(){
    var nombre = $("#nombre").val();
    $.ajax({
        type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
        url: "/intelijava/colaborador/", // A valid URL
        headers: {"X-HTTP-Method-Override": "POST", "Content-Type": "application/json"},
        data: nombre
    }).fail(function(response){
        console.log(response);
        alert(response.responseText);
    }).then(refrescar);
}

function redireccionar() {
    window.location.href="entrada.html";
}

function refrescar(){
    console.log("entro");
    var nombre = $("#nombre").val();
    sessionStorage.name = nombre;
        redireccionar();
}

$(document).ready(
        function () {
            console.log(sessionStorage);
            if(sessionStorage.name!=null){
                console.log("entro");
                redireccionar();
            }
        }
);