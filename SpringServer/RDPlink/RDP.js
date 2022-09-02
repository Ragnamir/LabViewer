var destination=(WScript.Arguments(0));
var search='rdp://';
var rdpexe='C:\\WINDOWS\\system32\\mstsc.exe';
//WScript.Echo(destination);
destination=destination.replace(search, '');
destination=destination.replace('/', '');
var ws = new ActiveXObject("WScript.Shell");
//WScript.Echo(rdpexe + " /v:" + destination);
ws.Exec(rdpexe + " /v:" + destination);