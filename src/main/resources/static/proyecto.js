var dmp = new diff_match_patch();

function connect() {
    $("#connect").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/project.'+sessionStorage.nameProject, function (data) {
            var patch_text = JSON.parse(data.body);
            var patches = dmp.patch_fromText(patch_text.text);
            text1 = $("#orig").val();
            //cursor = editor.selection.getCursor();
            //console.log(cursor);
            var results = dmp.patch_apply(patches, text1);
            console.log(patches);
            $("#orig").val(results[0]);
            editor.setValue(results[0],1);
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
            $("#titulo").html(sessionStorage.nameProject);
            connect();
            editor = ace.edit("text");
            editor.setFontSize(18);
            editor.getSession().setMode("ace/mode/java");
            editor.setTheme("ace/theme/monokai");
            $('#text').on('input selectionchange propertychange', function () {
                text1 = $("#orig").val();
                //text2 = $("#text2").val();
                text2 = editor.getValue();
                var diff = dmp.diff_main(text1, text2, true);
                var patch_list = dmp.patch_make(text1, text2, diff);
                patch_text = dmp.patch_toText(patch_list);
                stompClient.send("/topic/project."+sessionStorage.nameProject, {}, JSON.stringify({text: patch_text}));
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