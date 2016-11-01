
function guardarNombre(){
    var nombre = $("#nombre").val();
    sessionStorage.name = nombre;
    $.post("/intelijava/colaboradores/",nombre);
}
