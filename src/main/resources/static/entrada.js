function connect() {
    $("#connect").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/waiting.'+sessionStorage.name, function (data) {
            var theObject = JSON.parse(data.body);
            console.log(theObject);
            var irAProyecto = confirm(theObject[0]+" te ha invitado a su proyecto ¿Aceptas?"+theObject[1]);
            if(irAProyecto){
                redireccionar();
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
    window.location.href="proyecto.html";
}

function guardarNombre(){
    var nombreProyecto = $("#nombreProyecto").val();
    sessionStorage.nameProject = nombreProyecto;
    redireccionar();
}

$(document).ready(
        function() {
            $("#nombre").html("Esta logueado como "+sessionStorage.name);
            connect();
        }
);
