var baseUrl = window.location.href;
var tablUpdIntId;

var fileList = document.getElementById("files").value;
var files = fileList.substring(1,fileList.length-1);
files = files.split(',');

var tbody = document.getElementById('file_body'), tr, td;
files.forEach(function(element) {
	tr = document.createElement('tr');
	td = document.createElement('td');
	var t = document.createTextNode(element);
	td.appendChild(t);
    tr.appendChild(td);
    tbody.appendChild(tr);
    console.log(element);
});

var fileUpdateID;



document.getElementById("filesearch").addEventListener("keypress",
		function(e) {
	var key = e.which || e.keyCode;
    if (key === 13) { // 13 is enter
    	var searchelement = document.getElementById("filesearch").value;

    	searchFile(searchelement);
      document.getElementById("filesearch").value="";
//      fileUpdateID=setInterval(function(){
//
//    	  searchNodeFile(searchelement);
//
//    	}, 1000);
      searchNodeFile(searchelement);
//      clearInterval(fileUpdateID)

    }

		}, false);
document.getElementById("submit_button").addEventListener("click",
		function() {
			var username = document.getElementById("user_name").value;
			if (username.trim() != "") {
				registerUser(username);
				tablUpdIntId = setInterval(function(){
					getNeighbours();
				}, 60000);
				this.disabled=true;
				document.getElementById("unregister_button").disabled=false;
				document.getElementById("user_name").value="";
			}

		}, false);

document.getElementById("unregister_button").addEventListener("click",
		function() {
			clearInterval(tablUpdIntId);
			var myip = document.getElementById("ip").value;
			var myport = document.getElementById("port").value;
			var username = document.getElementById("user_name").value;
			if (username.trim() != "") {
				unregisterUser(ip,port);
				this.disabled=true;
				document.getElementById("submit_button").disabled=false;
			}

		}, false);

function registerUser(username) {
	
	$.ajax({
		contentType : 'application/json;charset=UTF-8',
		url : baseUrl + 'register/user',
		dataType : 'json',
		type : 'POST',
		cache : false, // Force requested pages not to be cached by the browser
		processData : false, // Avoid making query string instead of JSON
		data : JSON.stringify({
			user : username
		})
		
	}).done(function(data) {
		console.log('AJAX call was successfully executed ;)');
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}

function unregisterUser(myip,myport) {
	
	$.ajax({
		contentType : 'application/json;charset=UTF-8',
		url : baseUrl + 'register/nouser',
		dataType : 'json',
		type : 'POST',
		cache : false, // Force requested pages not to be cached by the browser
		processData : false, // Avoid making query string instead of JSON
		data : JSON.stringify({
			ip : myip,
			port : myport
		})
		
	}).done(function(data) {
		console.log('AJAX call was successfully executed ;)');
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}

function searchFile(fileName) {

	$.ajax({
		contentType : 'application/json;charset=UTF-8',
		url : baseUrl + 'selfSearch',
		dataType : 'json',
		type : 'POST',
		cache : false, // Force requested pages not to be cached by the browser
		processData : false, // Avoid making query string instead of JSON
		data : JSON.stringify({
			file_name : fileName
		}

		)

	}).done(function(data) {
		console.log('AJAX call was successfully executed ;)');
		var searchedbody = document.getElementById('searched_file'), tr, td;
		console.log(data);
		for (var key in data) {
			var val = data[key];
			tr = document.createElement('tr');
			td1 = document.createElement('td');
			td2 = document.createElement('td');
			var t1 = document.createTextNode(baseUrl);
			td1.appendChild(t1);
			var t2 = document.createTextNode(val);
			td2.appendChild(t2);
		    tr.appendChild(td1);
		    tr.appendChild(td2);
		    searchedbody.appendChild(tr);


		}
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}

function searchNodeFile(fileName) {

	$.ajax({
		contentType : 'application/json;charset=UTF-8',
		url : baseUrl + 'result',
		dataType : 'json',
		type : 'POST',
		cache : false, // Force requested pages not to be cached by the browser
		processData : false, // Avoid making query string instead of JSON
		data : JSON.stringify({
			file_name : fileName
		}

		)

	}).done(function(data) {
		console.log('AJAX call was successfully executed ;)');
		var searchedbody = document.getElementById('searched_file'), tr, td;
		console.log(data);
		for (var key in data) {
			var val = data[key];
			tr = document.createElement('tr');
			td1 = document.createElement('td');
			td2 = document.createElement('td');
			var t1 = document.createTextNode(baseUrl);
			td1.appendChild(t1);
			var t2 = document.createTextNode(val);
			td2.appendChild(t2);
		    tr.appendChild(td1);
		    tr.appendChild(td2);
		    searchedbody.appendChild(tr);


		}
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}







function getNeighbours() {
	$.ajax({
		contentType : 'application/json;charset=UTF-8',
		url : baseUrl + '/request/neighbours',
		dataType : 'json',
		type : 'GET',
		cache : false, // Force requested pages not to be cached by the browser
		processData : false, // Avoid making query string instead of JSON

	}).done(function(data) {

		console.log('AJAX call was successfully executed ;)');
		console.log(data);
		var neighBody = document.getElementById("table_neighbours");
		while(neighBody.firstChild) {
			neighBody.removeChild(neighBody.firstChild);
		}

		data.forEach(function(element){
			tr = document.createElement('tr');
			td = document.createElement('td');
			var t = document.createTextNode(element.ip);
			td.appendChild(t);
		    tr.appendChild(td);
		    td = document.createElement('td');
			var t = document.createTextNode(element.port);
			td.appendChild(t);
			tr.appendChild(td);
			neighBody.appendChild(tr);
		    console.log(element);
		});

	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}

