var dmp = new diff_match_patch();
movido = 0;
function connect() {
    $("#connect").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/project.' + sessionStorage.nameProject, function (data) {
            var obj = JSON.parse(data.body);
            //console.log(sessionStorage.name);
            //console.log(data);
            /*obj = data.body.split("AUTOR");
            author = obj[1];
            text = obj[0];*/
            if (obj.author != sessionStorage.name) {
            //if (author != sessionStorage.name) {
                /*var patches = dmp.patch_fromText(obj.text);
                text1 = $("#orig").val();
                
                //console.log(cursor);
                var results = dmp.patch_apply(patches, text1);
                //console.log(patches);
                $("#orig").val(results[0]);
                editor.setValue(results[0], 1);*/
                
                editor.setValue(obj.text, 1);
                //editor.setValue(text, 1);
                col = parseInt(obj.col);
                row = parseInt(obj.row);
                col_ant = parseInt(obj.col_ant);
                row_ant = parseInt(obj.row_ant);
                myRow = parseInt(sessionStorage.cursor_row);
                myCol = parseInt(sessionStorage.cursor_col);
                //console.log("myRow: "+myRow);
                //console.log("myCol: "+myCol);
                newCol = myCol;
                newRow = myRow;
                if(row==myRow){
                    if(col<myCol){
                        newCol++;
                    }
                }if(row<=myRow){
                    //console.log("Sí "+row+"<="+myRow);
                    if(row_ant!=row){
                        newRow+=(row-row_ant);
                    }
                }
                //console.log("Me tengo que mover a: "+newRow+", "+newCol);
                editor.moveCursorTo(newRow, newCol);
                sessionStorage.cursor_row = newRow;
                sessionStorage.cursor_col = newCol;
            }
            /*else {
                $("#orig").val(editor.getValue());
            }*/
        });
        stompClient.subscribe('/topic/waitingBan.' + sessionStorage.nameProject, function (data) {
            //console.log(data);
            var theObject = JSON.parse(data.body);
            //console.log(theObject);
            var nombre = sessionStorage.name;
            if (theObject.name == nombre){
                alert("Has sido eliminado del proyecto");
                desconectar();
            }

        });
    });
}

function enviarInvitacion() {
    $.get("intelijava/proyecto/" + sessionStorage.nameProject + "/duenno").then(function (data) {
        if (data == sessionStorage.name) {
            var nombreInvitado = filterXSS($("#Ncolaborador").val());
            //$("#Ncolaborador").val("");
            var miNombre = sessionStorage.name;
            var nombreProy = sessionStorage.nameProject;
            //console.log(miNombre + " " + nombreProy);
            stompClient.send("/topic/waiting." + nombreInvitado, {}, JSON.stringify({miNombre: miNombre, nombreProy: nombreProy}));
        } else {
            alert("No eres el dueño de este proyecto");
        }

    });
}

function borrarColaborador() {
    $.get("intelijava/proyecto/" + sessionStorage.nameProject + "/duenno").then(function (data) {
        if (data == sessionStorage.name) {
            var nombreInvitado = filterXSS($("#Bcolaborador").val());
            if (nombreInvitado != sessionStorage.name) {
                var nombreProy = sessionStorage.nameProject;
                //$.post("/intelijava/proyecto/" + nombreProy + "/delcolaborador/", nombreInvitado, function () {});
                $.ajax({
                    type: 'DELETE', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
                    url: "/intelijava/proyecto/" + nombreProy + "/" + nombreInvitado, // A valid URL
                    headers: {"X-HTTP-Method-Override": "DELETE", "Content-Type": "application/json"}
                }).fail(function (response) {
                    //console.log(response);
                    alert(response.responseText);
                }).then(function(){
                    stompClient.send("/topic/waitingBan." + sessionStorage.nameProject, {}, JSON.stringify({name: nombreInvitado}));
                    alert("Has baneado exitosamente a "+nombreInvitado);
                });
            }
        } else {
            alert("No eres el dueño de este proyecto");
        }
    });
}

function end() {
    var usuario = sessionStorage.name;
    var nombreProy = sessionStorage.nameProject;
    $.ajax({
        type: 'DELETE', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
        url: "/intelijava/proyecto/" + nombreProy + "/" + usuario, // A valid URL
        headers: {"X-HTTP-Method-Override": "DELETE", "Content-Type": "application/json"}
    }).fail(function (response) {
        //console.log(response);
        alert(response.responseText);
    }).then(desconectar);
    /*
     $.post("/intelijava/proyecto/" + nombreProy + "/delcolaborador/", usuario, function () {
     redireccionar();
     disconnect();
     }).fail(function () {
     alert("El usuario ya existe");
     });*/
}

function desconectar() {
    sessionStorage.removeItem("nameProject");
    sessionStorage.removeItem("name");
    //sessionStorage.name = null;
    redireccionar();
    disconnect();
}

function redireccionar() {
    window.location.href = "index.html";
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

function compilar(){
    $.get("intelijava/proyecto/" + sessionStorage.nameProject + "/colaborador/"+ sessionStorage.name + "/compilado").then(function (data) {
        $("#resCompilacion").html(data);
    });
}

function limpiarCompilacion(){
    $("#resCompilacion").html("Procesando... Por favor espere.");
}

function crearArbol(){
    $('#tree').jstree();
}

$(document).ready(
        function () {
            if(sessionStorage.name==null || sessionStorage.nameProject==null){
                redireccionar();
            }
            crearArbol();
            $("#titulo").html(sessionStorage.nameProject);
            $("#nombre_usuario").html("Hola, "+sessionStorage.name+" <span class='caret'></span>");
            //$("#colaboran").html(sessionStorage.)
            connect();
            editor = ace.edit("text");
            editor.setFontSize(18);
            editor.getSession().setMode("ace/mode/java");
            editor.setTheme("ace/theme/monokai");
            sessionStorage.cursor_row = editor.getCursorPosition().row;
            sessionStorage.cursor_col = editor.getCursorPosition().column;
            $('.ace_text-input').on('input keypress keydown', function () {
            //editor.getSession().on('change',function(){
                //stompClient.send("/topic/project." + sessionStorage.nameProject, {}, JSON.stringify({text: patch_text, author: sessionStorage.name}));*/
                //stompClient.send("/app/project." + sessionStorage.nameProject, {}, editor.getValue() +"AUTOR"+sessionStorage.name);
                stompClient.send("/app/project." + sessionStorage.nameProject, {}, JSON.stringify({text: editor.getValue(), author: sessionStorage.name, col: editor.getCursorPosition().column, row: editor.getCursorPosition().row, col_ant: sessionStorage.cursor_col, row_ant: sessionStorage.cursor_row}));
                sessionStorage.cursor_row = editor.getCursorPosition().row;
                sessionStorage.cursor_col = editor.getCursorPosition().column;
            });
            
            $('#text').on('click', function () {
                sessionStorage.cursor_row = editor.getCursorPosition().row;
                sessionStorage.cursor_col = editor.getCursorPosition().column;

	    //editor.getSession().on('change', function(){
                /*text1 = $("#orig").val();
                //text2 = $("#text2").val();
                text2 = editor.getValue();
                var diff = dmp.diff_main(text1, text2, true);
                var patch_list = dmp.patch_make(text1, text2, diff);
                patch_text = dmp.patch_toText(patch_list);
                stompClient.send("/topic/project." + sessionStorage.nameProject, {}, JSON.stringify({text: patch_text, author: sessionStorage.name}));*/
                //sessionStorage.cursor = editor.getCursorPosition();
                //alert(editor.getValue());
                //stompClient.send("/app/project." + sessionStorage.nameProject, {}, editor.getValue() +"AUTOR"+sessionStorage.name);
            });
            
            $.get("intelijava/proyecto/"+sessionStorage.nameProject+"/paquete/0/archivo/0").then(function (data) {
               editor.setValue(data, 1); 
            });
        }
);

/*
 var dmp = new diff_match_patch();
 var Range = require("ace/range").Range;
 
 function connect() {
 $("#connect").prop("disabled", true);
 $("#disconnect").prop("disabled", false);
 var socket = new SockJS('/stompendpoint');
 stompClient = Stomp.over(socket);
 stompClient.connect({}, function (frame) {
 console.log('Connected: ' + frame);
 
 stompClient.subscribe('/topic/newpoint', function (data) {
 doc = editor.getSession().getDocument();
 var patch_text = JSON.parse(data.body);
 diff = patch_text.text;
 var offset = 0;
 diff.forEach(function (chunk) {
 var op = chunk[0];
 var text = chunk[1];
 
 if (op === 0) {
 offset += text.length;
 } else if (op === -1) {
 doc.remove(Range.fromPoints(
 doc.indexToPosition(offset),
 doc.indexToPosition(offset + text.length)
 ));
 } else if (op === 1) {
 doc.insert(doc.indexToPosition(offset), text);
 offset += text.length;
 }
 });
 $("#orig").val(editor.getValue());
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
 
 $(document).ready(
 function () {
 connect();
 editor = ace.edit("text");
 editor.getSession().setMode("ace/mode/python");
 editor.setTheme("ace/theme/monokai");
 $('#text').on('input selectionchange propertychange', function () {
 text1 = $("#orig").val();
 //text2 = $("#text2").val();
 text2 = editor.getValue();
 var diff = dmp.diff_main(text1, text2, true);
 stompClient.send("/app/newpoint", {}, JSON.stringify({text: diff}));
 });
 }
 );
 */
