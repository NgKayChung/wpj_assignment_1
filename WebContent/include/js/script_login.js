var userIdentifier_errMsg = password_errMsg = login_errMsg = "";

document.getElementById("login-account").addEventListener("click", function() {
	if(login_validFields()) {
		if(!check_user_credential()){
			//displays error messages
			setTimeout(displayLoginErrors, 500);
		}
	} else {
		//displays error messages
		setTimeout(displayLoginErrors, 500);
	}
});

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

function check_user_credential()
{
	var login_user_identifier_val = document.getElementById("user_identifier").value;
	var login_user_password_val = document.getElementById("user_password").value;
	
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

function displayLoginErrors()
{
	document.getElementById("identifier-err").innerHTML = userIdentifier_errMsg;
	document.getElementById("password-err").innerHTML = password_errMsg;
	document.getElementById("login-err").innerHTML = login_errMsg;
}