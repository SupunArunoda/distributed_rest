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
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}

