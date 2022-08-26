var ws;
var editInProgress = false;
var editId = '';
var isDirty = false;
var labs;

const unreachableImg = "data:image/gif;base64, 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";
const unreachableAlt = "Unreachable";
const busyImg = "data:image/gif;base64, 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";
const busyAlt = "Occupied";
const freeImg = "data:image/gif;base64, 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";
const freeAlt = "Free";
	
window.onload = connect;

function copyContentOnClick() {
	var text = this.innerHTML.split(' ')[1];
	navigator.clipboard.writeText(text).then(function() {
	  console.log('Async: Copying to clipboard was successful!');
	}, function(err) {
	  console.error('Async: Could not copy text: ', err);
	});
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
		c3.ondblclick = "setEditable(this)";
		row.append(c3);
		
		table.append(row);
	}

	var state = JSON.parse(labPC.state);
	
	c0.textContent = '';
	if (state.length == 0) {
		let img = document.createElement("img");
		img.src = freeImg;
		img.alt = freeAlt;
		c0.append(img);
	} else if (state == "Unreachable") {
		let img = document.createElement("img");
		img.src = unreachableImg;
		img.alt = unreachableAlt;
		c0.append(img);
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
			div.className = 'centerPseudo';
			div.innerHTML = state[0];
			c0.append(div);
		}
	}

	c1.innerHTML = '<a href="rdp://' + labPC.ipAddress + '" title="Click to RDP">' + labPC.ipAddress + '</a>';
	c2.innerHTML =  labPC.name;
	c3.innerHTML = (labPC.message == '' ? 'Everything is fine' : labPC.message);
}

function updateLab(lab, container, i) {
	let table;
	
	if ($('#table_'+i).get(0)) {
		$('#name_'+i).get(0).innerHTML = lab.name;
		table = $('#table_'+i).get(0);
		
		if ($('#vmWareURI_'+i).get(0)) {
			$('#vmWareURI_'+i).get(0).innerHTML = 'VMWare: <a href="http://' + lab.vmWareURI + '" title="Click to open vmWare">' + lab.vmWareURI + '</a>';
			$('#vmWareLogin_'+i).get(0).innerHTML = 'Login: ' + lab.vmWareUsername;
			$('#vmWarePass_'+i).get(0).innerHTML = 'Pass: ' + lab.vmWarePwd;
		}
	} else {	
		table = document.createElement("table");
		table.id = 'table_' + i;
		table.className = "table";
		
		let tableTitle = document.createElement("th");
		tableTitle.id = 'name_' + i;
		tableTitle.colSpan = 4;
		tableTitle.innerHTML = lab.name;
		table.append(tableTitle);
		
		if (!""===lab.vmWareURI) {
			let vmWareRow = document.createElement("tr");
			
			let vmWareURI = document.createElement("td");
			vmWareURI.id = 'vmWareURI_' + i;
			vmWareURI.colSpan = 2;
			vmWareURI.innerHTML = 'VMWare: <a href="http://' + lab.vmWareURI + '" title="Click to open vmWare">' + lab.vmWareURI + '</a>';
			vmWareRow.append(vmWareURI);
			
			let vmWareCredentials = document.createElement("td");
			vmWareCredentials.colSpan = 2;
			vmWareRow.append(vmWareCredentials);
			
			let loginDiv = document.createElement("div");
			loginDiv.id = 'vmWareLogin_' + i;
			loginDiv.innerHTML = 'Login: ' + lab.vmWareUsername;
			loginDiv.onclick = copyContentOnClick;
			vmWareCredentials.append(loginDiv);
			
			let passDiv = document.createElement("div");
			passDiv.id = 'vmWarePass_' + i;
			passDiv.innerHTML = 'Pass: ' + lab.vmWarePwd;
			passDiv.onclick = copyContentOnClick;
			vmWareCredentials.append(passDiv);
			
			table.append(vmWareRow);
		}
		
		container.append(table);
	}
	
	for (var j = 0; j < lab.labPCs.length; j++) {
		labPC = lab.labPCs[j];
		updateRow(labPC, table, i, j)
	}
}
 
function connect() {
	//var url = 'ws://192.168.37.105:8080/labviewerws';
	var url = 'ws://localhost:8080/labviewerws';
	timedTask = setInterval(send,5000);
	ws = new WebSocket(url);
	ws.onopen = function () {
		send();
	};
	ws.onmessage = function (data) {
		labs = JSON.parse(data.data);
		console.debug(labs);
		
		for (var i = 0; i < labs.labs.length; i++) {
			let lab = labs.labs[i];
			updateLab(lab, $("#content").get(0), i);
		}
	};
}

function setEditable(obj) {
	if (!editInProgress) {
		editInProgress = true;
		obj.contentEditable = true;
		
		obj.addEventListener('keydown', function(e) {
		    if (e.keyCode === 13) {
		    	editDone(obj);
		      }
		    });
	}
}

function editDone(obj) {
	if (editInProgress) {
		editInProgress = false;
		obj.contentEditable = false;
		isDirty = true;
		let idArray = obj.id.split("_");
		console.log(idArray);
		labs.labs[idArray[1]].labPCs[idArray[2]].message = obj.innerHTML;
		send();
	}
}
   
 
function send() {
	if (ws.readyState==1) {
		console.debug('Sending stuff');
		if (isDirty) {
			ws.send(JSON.stringify(labs));
			isDirty = false;
		} else {
			ws.send('check');
		}
	} else {
		clearInterval(timedTask);
		$("#warning").css("display", "block");
	}
}
