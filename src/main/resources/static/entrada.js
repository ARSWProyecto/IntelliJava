function connect() {
    $("#connect").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/waiting.' + sessionStorage.name.nombre, function (data) {
            var theObject = JSON.parse(data.body);
            console.log(theObject.miNombre + " " + theObject.nombreProy);
            var irAProyecto = confirm(theObject.miNombre + " te ha invitado a su proyecto " + theObject.nombreProy + " ¿Aceptas?");
            if (irAProyecto) {
                //hacer post
                $.post("/intelijava/proyecto/"+theObject.nombreProy+"/colaborador/",sessionStorage.name, function () {
                    sessionStorage.nameProject = theObject.nombreProy;
                    redireccionar();
                }).fail(function () {
                    alert("No se ha podido añadir al proyecto");
                });
                
            }
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        $("#connect").prop("disabled", false);
        $("#disconnect").prop("disabled", true);
        stompClient.disconnect();
    }
    //setConnected(false);
    console.log("Disconnected");
}

function redireccionar() {
    disconnect();
    window.location.href = "proyecto.html";
}

function guardarProyecto() {
    var nombreProyecto = $("#nombreProyecto").val();
    var Duenno = sessionStorage.name;
    $.post("/intelijava/proyecto/"+nombreProyecto, Duenno, function () {
        sessionStorage.nameProject = new Proyecto(nombreProyecto, Duenno);
        redireccionar();
    }).fail(function () {
        alert("El proyecto ya existe");
    });
}

$(document).ready(
        function () {
            $("#nombre").html("Esta logueado como " + sessionStorage.name.nombre);
            connect();
        }
);
