var baseUrl = window.location.href;
var hostPort = baseUrl.split("/")[2];
var host = hostPort.split(":")[0];
var port = hostPort.split(":")[1];

document.getElementById("submit_button").addEventListener("click",
		function() {
			var username = document.getElementById("user_name").value;
			if (username.trim() != "") {
				registerUser(username);
				this.disabled=true;
				document.getElementById("unregister_button").disabled=false;
			}

		}, false);

document.getElementById("unregister_button").addEventListener("click",
		function() {
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
		}
		
		)
		
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
		}
		
		)
		
	}).done(function(data) {
		console.log('AJAX call was successfully executed ;)');
	}).fail(function(data) {
		console.log(data);
		console.log('AJAX call failed :(');
	});
}