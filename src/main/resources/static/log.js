
function guardarNombre(){
    var nombre = $("#nombre").val();
    $.post("/intelijava/colaborador/",nombre, function(){
        sessionStorage.name = nombre;
        redireccionar();
    }).fail(function(){
        alert("El usuario ya existe");
    });
}

function redireccionar() {
    window.location.href="entrada.html";
}