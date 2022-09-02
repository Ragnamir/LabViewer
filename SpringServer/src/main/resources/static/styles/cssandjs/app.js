var ws;
var socket;
var sessionId = "";

var editmode = false; //Used for editing lab configuration

var editInProgress = false; //Used for editing messages only
var editId = '';
var isDirty = false;
var labs;

const unreachableImg = "data:image/gif;base64, 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";
const unreachableAlt = "Unreachable";
const busyImg = "data:image/gif;base64, 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";
const busyAlt = "Occupied";
const freeImg = "data:image/gif;base64, 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";
const freeAlt = "Free";

function createLab() {
    let newLab = new Object();
    newLab.name = "";
    newLab.vmWareURI = "";
    newLab.vmWareUsername = "";
    newLab.vmWarePwd = "";
    newLab.labPCs = [];
    labs.labs.push(newLab);
}

function createPC(i) {
    let newPC  = new Object();
    newPC.ipAddress = "";
    newPC.name = "";
    newPC.state = [];
    newPC.message = "";
    labs.labs[i].labPCs.push(newPC);
}

function removeLab(i) {
    labs.labs.splice(i, 1);
}

function removePC(i, j) {
    labs.labs[i].labPCs.splice(j, 1);
}

function extractData() {
    if (editmode) {
        for (let i = 0; i < labs.labs.length; i++) {
            labs.labs[i].name = $('#name_'+i+'_edit').get(0).value;
            labs.labs[i].vmWareURI = $('#vmWareURI_'+i+'_edit').get(0).value;
            labs.labs[i].vmWareUsername = $('#vmWareLogin_'+i+'_edit').get(0).value;
            labs.labs[i].vmWarePwd = $('#vmWarePass_'+i+'_edit').get(0).value;

            if (labs.labs[i].labPCs) {
                for (let j = 0; j < labs.labs[i].labPCs.length; j++) {
                    labs.labs[i].labPCs[j].ipAddress = $('#ipEdit_'+i+'_'+j+'_edit').get(0).value;
                    labs.labs[i].labPCs[j].name = $('#name_'+i+'_'+j+'_edit').get(0).value;
                }
            }
        }
    }
    //console.log(labs);
}

function startEditMode() {
    if (!editmode) {
        editmode = true;
        updateAll(labs);
    }
}

function endEditMode() {
    if (editmode) {
        editmode = false;
        updateAll(labs);
    }
}

function saveChanges() {
    extractData();
    sendLabUpdate();
    endEditMode();
}

function addLabclick(e) {
    //le.log(e);
    extractData();
    createLab();
    updateAll(labs);
}

function addLabPCclick(e) {
    //console.log(e);
    //console.log(e.target.parentElement.parentElement.parentElement.id.split('_')[1]);
    extractData();
    createPC(e.target.parentElement.parentElement.parentElement.id.split('_')[1]);
    updateAll(labs);
}

function removeLabclick(e) {
    //console.log(e);
    //console.log(e.target.parentElement.id.split('_')[1]);
    extractData();
    removeLab(e.target.parentElement.id.split('_')[1]);
    updateAll(labs);
}

function removeLabPCclick(e) {
    //console.log(e);
    let ind = e.target.parentElement.id.split('_');
    //console.log(ind);
    extractData();
    removePC(ind[1], ind[2]);
    updateAll(labs);
}

function redeployBeaconClick(e) {
    //console.log(e);
    let ind = e.target.parentElement.parentElement.id.split('_');
    //console.log(ind);
    extractData();
    sendRedeploy(labs.labs[ind[1]].labPCs[ind[2]].ipAddress);
}

function copyContentOnClick() {
    var text = this.innerHTML.split(' ')[1];
    navigator.clipboard.writeText(text).then(function() {
        //console.log('Async: Copying to clipboard was successful!');
    }, function(err) {
        //console.error('Async: Could not copy text: ', err);
    });
}

function editDone(obj) {
    if (editInProgress) {
        editInProgress = false;
        obj.contentEditable = false;
        isDirty = true;
        let idArray = obj.id.split("_");
        //console.log(idArray);
        labs.labs[idArray[1]].labPCs[idArray[2]].message = obj.innerHTML;
        sendMessageUpdate()
    }
}

function setEditable(obj) { //for message editing
    if (!editInProgress) {
        editInProgress = true;
        obj.toElement.contentEditable = true;

        obj.toElement.addEventListener('keydown', function(e) {
            if (e.keyCode === 13) {
                editDone(obj.toElement);
            }
        });
    }
}

function updateRow(labPC, table, i, j) {

    let c0, c1, c2, c3;
    let row;

    if ($('#row_' + i + '_' + j).get(0)) {
        row = $('#row_' + i + '_' + j).get(0);
        c0 = $('#cell_'+ i + '_' + j +'_0').get(0);
        c1 = $('#cell_'+ i + '_' + j +'_1').get(0);
        c2 = $('#cell_'+ i + '_' + j +'_2').get(0);
        c3 = $('#cell_'+ i + '_' + j +'_3').get(0);
    } else {
        row = document.createElement("tr");
        row.id = 'row_'+ i + '_' + j;

        c0 = document.createElement("td");
        c0.id = 'cell_'+ i + '_' + j +'_0';
        row.append(c0);

        c1 = document.createElement("td");
        c1.id = 'cell_'+ i + '_' + j +'_1';
        row.append(c1);

        c2 = document.createElement("td");
        c2.id = 'cell_'+ i + '_' + j +'_2';
        row.append(c2);

        c3 = document.createElement("td");
        c3.id = 'cell_'+ i + '_' + j +'_3';
        c3.ondblclick = setEditable;
        row.append(c3);

        table.append(row);
    }

    let state = labPC.state;

    c0.textContent = '';

    if (state.length == 0) {
        let img = document.createElement("img");
        img.src = freeImg;
        img.alt = freeAlt;
        c0.append(img);
    } else if (state[0] == "Unreachable") {
        let img = document.createElement("img");
        img.src = unreachableImg;
        img.alt = unreachableAlt;
        c0.append(img);

        if (!editmode) {
            let buttonDiv = document.createElement("div");
            c0.append(buttonDiv);

            let redeployButton = document.createElement("button");
            redeployButton.innerHTML = 'Redeploy?';
            redeployButton.className = "button";
            redeployButton.addEventListener('click', redeployBeaconClick);
            buttonDiv.append(redeployButton);

        }
    } else {
        let img = document.createElement("img");
        img.src = busyImg;
        img.alt = busyAlt;
        c0.append(img);

        if (state.length > 1) {
            let ul = document.createElement("ul");

            for (var k = 0; k<state.length; k++) {
                let li = document.createElement("li");
                li.innerHTML = state[k];
                ul.append(li);
            }

            c0.append(ul);
        } else {
            let div = document.createElement("div");
            //div.className = 'centerPseudo';
            div.innerHTML = state[0];
            c0.append(div);
        }
    }

    if (editmode) {
        c1.innerHTML = '';
        c2.innerHTML = '';
        c3.innerHTML = '';

        let ipP = document.createElement("p");
        ipP.innerHTML = "PC IP:";
        let ipEdit = document.createElement("input");
        ipEdit.value = labPC.ipAddress;
        ipEdit.id = 'ipEdit_'+i+'_'+j+'_edit';
        c1.append(ipP);
        c1.append(ipEdit);

        let nameP = document.createElement("p");
        nameP.innerHTML = "PC name:";
        let nameEdit = document.createElement("input");
        nameEdit.value = labPC.name;
        nameEdit.id = 'name_'+i+'_'+j+'_edit';
        c2.append(nameP);
        c2.append(nameEdit);

        let deleteButton = document.createElement("button");
        deleteButton.innerHTML = "- lab pc";
        deleteButton.className = "button";
        deleteButton.addEventListener('click', removeLabPCclick);
        c3.append(deleteButton);
    } else {

        c1.innerHTML = '<a href="rdp://' + labPC.ipAddress + '" title="Click to RDP">' + labPC.ipAddress + '</a>';
        c2.innerHTML =  labPC.name;
        c3.innerHTML = (labPC.message == '' ? 'Everything is fine' : labPC.message);
    }

}

function updateLab(lab, container, i) {
    let table;
    let tableName;
    let vmWareURI;
    let vmWareLogin;
    let vmWarePass;

    if ($('#table_'+i).get(0)) {
        table = $('#table_'+i).get(0);
        tableName =  $('#name_'+i).get(0);

        if (''!=lab.vmWareURI) {
            vmWareURI = $('#vmWareURI_'+i).get(0);
            vmWareLogin = $('#vmWareLogin_'+i).get(0);
            vmWarePass = $('#vmWarePass_'+i).get(0);
        }
    } else {
        table = document.createElement("table");
        table.id = 'table_' + i;
        table.className = "table";

        tableName = document.createElement("th");
        tableName.id = 'name_' + i;
        tableName.colSpan = 4;
        tableName.innerHTML = lab.name;
        table.append(tableName);

        if (''!=lab.vmWareURI || editmode) {
            let vmWareRow = document.createElement("tr");

            vmWareURI = document.createElement("td");
            vmWareURI.id = 'vmWareURI_' + i;
            vmWareURI.colSpan = 2;
            vmWareRow.append(vmWareURI);

            let vmWareCredentials = document.createElement("td");
            vmWareCredentials.colSpan = 2;
            vmWareRow.append(vmWareCredentials);

            vmWareLogin = document.createElement("div");
            vmWareLogin.id = 'vmWareLogin_' + i;
            vmWareLogin.onclick = copyContentOnClick;
            vmWareCredentials.append(vmWareLogin);

            vmWarePass = document.createElement("div");
            vmWarePass.id = 'vmWarePass_' + i;
            vmWarePass.onclick = copyContentOnClick;
            vmWareCredentials.append(vmWarePass);

            table.append(vmWareRow);
        }
    }

    if (editmode) {
        tableName.innerHTML = "";
        vmWareURI.innerHTML = "";
        vmWareLogin.innerHTML = "";
        vmWarePass.innerHTML = "";


        let nameP = document.createElement("p");
        nameP.innerHTML = "Lab name:";
        let nameEdit = document.createElement("input");
        nameEdit.value = lab.name;
        nameEdit.id = 'name_'+i+'_edit';
        let deleteLabButton = document.createElement("button");
        deleteLabButton.innerHTML = '- lab';
        deleteLabButton.addEventListener('click', removeLabclick);
        deleteLabButton.className = "button";
        tableName.append(nameP);
        tableName.append(nameEdit);
        tableName.append(deleteLabButton);

        let vmWareURIP = document.createElement("p");
        vmWareURIP.innerHTML = "WMWare UI URI:";
        let vmWareURIEdit = document.createElement("input");
        vmWareURIEdit.value = lab.vmWareURI;
        vmWareURIEdit.id = 'vmWareURI_'+i+'_edit';
        vmWareURI.append(vmWareURIP);
        vmWareURI.append(vmWareURIEdit);

        let vmWareLoginP = document.createElement("p");
        vmWareLoginP.innerHTML = "WMWare UI login:";
        let vmWareLoginEdit = document.createElement("input");
        vmWareLoginEdit.value = lab.vmWareUsername;
        vmWareLoginEdit.id = 'vmWareLogin_'+i+'_edit';
        vmWareLogin.append(vmWareLoginP);
        vmWareLogin.append(vmWareLoginEdit);

        let vmWarePassP = document.createElement("p");
        vmWarePassP.innerHTML = "WMWare UI password:";
        let vmWarePassEdit = document.createElement("input");
        vmWarePassEdit.value = lab.vmWarePwd;
        vmWarePassEdit.id = 'vmWarePass_'+i+'_edit';
        vmWarePass.append(vmWarePassP);
        vmWarePass.append(vmWarePassEdit);

    } else {
        tableName.innerHTML = lab.name;

        if (''!=lab.vmWareURI) {
            vmWareURI.innerHTML = 'VMWare: <a href="http://' + lab.vmWareURI + '" title="Click to open vmWare">' + lab.vmWareURI + '</a>';
            vmWareLogin.innerHTML = 'Login: ' + lab.vmWareUsername;
            vmWarePass.innerHTML = 'Pass: ' + lab.vmWarePwd;
        }
    }

    container.append(table);

    for (let j = 0; j < lab.labPCs.length; j++) {
        labPC = lab.labPCs[j];
        updateRow(labPC, table, i, j)
    }

    if (editmode) {
        let addLabRow = document.createElement("tr");
        addLabRow.className = 'editor_tool';

        let addLabCell = document.createElement("td");
        addLabCell.colSpan = 4;
        addLabRow.append(addLabCell);

        let addLabPCButton = document.createElement("button");
        addLabPCButton.innerHTML = "+ lab pc";
        addLabPCButton.addEventListener('click', addLabPCclick);
        addLabPCButton.className = "button";
        addLabCell.append(addLabPCButton);

        table.append(addLabRow);
    }
}

function updateAll(allLabs) {
    removeEditorTools();

    //editmode = true;

    for (let i = 0; i < allLabs.labs.length; i++) {
        let lab = labs.labs[i];
        updateLab(lab, $("#content").get(0), i);
    }

    if (editmode) {
        let addLabTable = document.createElement("table");
        addLabTable.id = "table_addlab";
        addLabTable.className = "table"
        addLabTable.className += " editor_tool";

        let addLabRow = document.createElement("tr");
        addLabTable.append(addLabRow);

        let addLabCell = document.createElement("td");
        addLabCell.colSpan = 4;
        addLabRow.append(addLabCell);

        let addLabButton = document.createElement("button");
        addLabButton.innerHTML = "+ lab";
        addLabButton.addEventListener('click', addLabclick);
        addLabButton.className = "button";
        addLabCell.append(addLabButton);

        $("#content").get(0).append(addLabTable);

        let controlsTable = document.createElement("table");
        controlsTable.id = "table_addlab";
        controlsTable.className = "table"
        controlsTable.className += " editor_tool";

        let controlsRow = document.createElement("tr");
        controlsTable.append(controlsRow);

        let controlsCell = document.createElement("td");
        controlsCell.colSpan = 4;
        controlsRow.append(controlsCell);

        let saveButton = document.createElement("button");
        saveButton.innerHTML = "Apply";
        saveButton.className = "button";
        saveButton.addEventListener('click', saveChanges);
        controlsCell.append(saveButton);

        let cancelButton = document.createElement("button");
        cancelButton.innerHTML = "Cancel";
        cancelButton.className = "button";
        cancelButton.addEventListener('click', endEditMode);
        controlsCell.append(cancelButton);

        $("#content").get(0).append(controlsTable);
    }
}

function removeEditorTools() {
    $("#content").get(0).innerHTML = "";
}

function requestUpdate() {
    ws.send("/app/getdata", {}, "");
}

function sendLabUpdate() {
    ws.send("/app/updatelab", {}, JSON.stringify(labs));
}

function sendMessageUpdate() {
    //console.log(labs);
    ws.send("/app/updatemessage", {}, JSON.stringify(labs));
}

function sendRedeploy(ip) {
    //console.log(ip);
    ws.send("/app/redeploybeacon", {}, JSON.stringify(ip));
}



function connect() {
    socket = new SockJS('/labviewer/websocket');
    ws = Stomp.over(socket);

    ws.connect({}, function(frame) {
        let url = ws.ws._transport.url;
        url = url.replace(
            "ws://localhost:8080/labviewer/websocket/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        //console.log("Your current session is: " + url);
        sessionId = url;

        ws.subscribe('/topic/data', function(message) {
            //console.log(message);
            if (!editmode) {
                labs = JSON.parse(message.body);
                //console.debug(labs);

                updateAll(labs);
            }
        });
        requestUpdate();
    }, function(error) {
        alert("STOMP error " + error);
    });
}

function disconnect() {
    if (ws != null) {
        ws.disconnect();
    }
}

window.onload = function () {
    connect();
    document.onkeydown = function(evt) {
        //console.log(evt);
        if (evt.ctrlKey && evt.code == 'KeyE') {
            startEditMode();
            evt.preventDefault();
        }
    }
    //setTimeout(requestUpdate, 100);

}