function connect() {
    $("#connect").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/waiting.' + sessionStorage.name, function (data) {
            var theObject = JSON.parse(data.body);
            console.log(theObject.miNombre + " " + theObject.nombreProy);
            var irAProyecto = confirm(theObject.miNombre + " te ha invitado a su proyecto " + theObject.nombreProy + " ¿Aceptas?");
            if (irAProyecto) {
                //hacer post
                var nombreProyecto = theObject.nombreProy;
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
        sessionStorage.nameProject = nombreProyecto;
        redireccionar();
    }).fail(function () {
        alert("El proyecto ya existe");
    });
}

$(document).ready(
        function () {
            $("#nombre").html("Esta logueado como " + sessionStorage.name);
            connect();
        }
);
