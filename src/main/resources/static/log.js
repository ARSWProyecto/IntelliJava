
function guardarNombre(){
    var nombre = $("#nombre").val();
    $.post("/intelijava/colaborador/",nombre, function(){
        sessionStorage.name = new Usuario(nombre);
        redireccionar();
    }).fail(function(){
        alert("El usuario no esta disponible");
    });
}

function redireccionar() {
    window.location.href="entrada.html";
}