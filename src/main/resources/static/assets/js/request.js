var baseUrl = window.location.href;

document.getElementById("submit_button").addEventListener("click",
		function() {
			var username = document.getElementById("user_name").value;
			if (username.trim() != "") {
				document.getElementById("user_name").value = "";
				registerUser(username);
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