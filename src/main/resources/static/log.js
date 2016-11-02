
function guardarNombre(){
    var nombre = $("#nombre").val();
    $.ajax({
        type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
        //dataType: 'json', // Set datatype - affects Accept header
        url: "/intelijava/colaborador/", // A valid URL
        headers: {"X-HTTP-Method-Override": "POST", "Content-Type": "application/json"},
        data: nombre
    }).fail(function(response){
        console.log(response);
        alert(response.responseText);
    }).then(refrescar);
    /*
    $.post("/intelijava/colaborador/",nombre, function(){
        sessionStorage.name = nombre;
        redireccionar();
    }).fail(function(){
        alert("El usuario no esta disponible");
    });*/
}

function redireccionar() {
    window.location.href="entrada.html";
}

function refrescar(){
    console.log("entro")
    var nombre = $("#nombre").val();
    sessionStorage.name = nombre;
        redireccionar();
}