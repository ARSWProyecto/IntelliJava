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
                $.ajax({
                    type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
                    url: "/intelijava/proyecto/"+theObject.nombreProy+"/colaborador/", // A valid URL
                    headers: {"X-HTTP-Method-Override": "POST", "Content-Type": "application/json"},
                    data: sessionStorage.name
                }).fail(function (response) {
                    console.log(response);
                    alert(response.responseText);
                }).then(function(){
                    sessionStorage.nameProject = theObject.nombreProy;
                    redireccionar();
                });
                /*
                 $.post("/intelijava/proyecto/"+theObject.nombreProy+"/colaborador/",sessionStorage.name, function () {
                 sessionStorage.nameProject = theObject.nombreProy;
                 redireccionar();
                 }).fail(function () {
                 alert("No se ha podido añadir al proyecto");
                 });*/

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
    var Duenno = sessionStorage.name;
    var nombreProyecto = $("#nombreProyecto").val();
    //sessionStorage.nameProject = nombreProyecto;
    $.ajax({
        type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
        url: "/intelijava/proyecto/" + nombreProyecto, // A valid URL
        headers: {"X-HTTP-Method-Override": "POST", "Content-Type": "application/json"},
        data: Duenno
    }).fail(function (response) {
        console.log(response);
        alert(response.responseText);
    }).then(refrescar);
    /*
     $.post("/intelijava/proyecto/"+nombreProyecto, Duenno, function () {
     sessionStorage.nameProject = nombreProyecto;
     redireccionar();
     }).fail(function () {
     alert("El proyecto ya existe");
     });*/
}

function refrescar() {
    var nombreProyecto = $("#nombreProyecto").val();
    sessionStorage.nameProject = nombreProyecto;
    redireccionar();
}


$(document).ready(
        function () {
            if(sessionStorage.name==null){
                window.location.href = "index.html";
            }
            $("#nombre").html("Esta logueado como " + sessionStorage.name);
            connect();
        }
);
