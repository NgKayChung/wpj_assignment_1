var userIdentifier_errMsg = password_errMsg = login_errMsg = "";

// adds click event to login button
document.getElementById("login-account").addEventListener("click", function() {
	if(login_validFields()) {
		if(!check_user_credential()){
			/** 
			 * set timeout with value 500 milliseconds / 0.5 second
			 * to wait for the previous execution to finish - function check_user_credential()
			 * 
			 * before displaying error messages
			 */
			setTimeout(displayLoginErrors, 500);
		}
	} else {
		/** 
		 * set timeout with value 500 milliseconds / 0.5 second
		 * to wait for the previous execution to finish - function login_validFields()
		 * 
		 * before displaying error messages
		 */
		setTimeout(displayLoginErrors, 500);
	}
});

/**
 * function to check all the fields in login form
 * 
 * @returns boolean (true / false)
 */
function login_validFields()
{
	var log_succ = true;
	
	userIdentifier_errMsg = password_errMsg = login_errMsg = "";
	
	var login_user_identifier = document.getElementById("user_identifier").value;
	var login_user_password = document.getElementById("user_password").value;
	
	if(login_user_identifier == "")
	{
		log_succ = false;
		userIdentifier_errMsg = "Username is required";
	}
	
	if(login_user_password == "")
	{
		log_succ = false;
		password_errMsg = "Password is required";
	}
	
	return log_succ;
}

/**
 * function to check user login credential
 * send an AJAX (Asynchronous Javascript And XML) request to web server
 * 	server  - check for login type (Username / Email)
 * 			- query database with the login identifier and password
 * 			- returns back a message ("VALID" / "INVALID") to AJAX function
 * 
 * @precondition all fields in login form are not empty
 * 
 * @returns boolean (true / false)
 */
function check_user_credential()
{
	var login_user_identifier_val = document.getElementById("user_identifier").value;
	var login_user_password_val = document.getElementById("user_password").value;
	
	/**
	 * AJAX function using JQuery send AJAX request with login identifier and password as data
	 * check user from database
	 * 
	 * @source - Internet
	 * @siteURL - https://stackoverflow.com/questions/9293991/how-to-get-the-data-via-ajax-in-servlet
	 * 
	 * @returns string value ("VALID" / "INVALID")
	 */
	$.ajax
    (
        {
            url: 'check_login',
            data: { "user_identifier": login_user_identifier_val, "user_password": login_user_password_val },
            type: 'post',
            cache: false,
            success: function(data){
        		if(data == "VALID") {
        			var login_form = document.getElementById("loginForm");
        			
        			login_form.action = "login_submit";
        			login_form.submit();
        			
        			return true;
        		} else {
        			login_errMsg = "Incorrect username or password";
        			return false;
        		}
            },
            error:function(){
            	alert("error");
            }
        }
    );
}

// function to display login error message(s)
function displayLoginErrors()
{
	document.getElementById("identifier-err").innerHTML = userIdentifier_errMsg;
	document.getElementById("password-err").innerHTML = password_errMsg;
	document.getElementById("login-err").innerHTML = login_errMsg;
}