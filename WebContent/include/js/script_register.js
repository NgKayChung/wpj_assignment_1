var fullname_errMsg = username_errMsg = email_errMsg = password_errMsg = gender_errMsg = country_errMsg = dob_errMsg = terms_errMsg = "";
var userExisted = false;

// adds a click event to submit button
document.getElementById("register-account").addEventListener("click", function() {
	var register_form = document.getElementById("regForm");
	
	if(register_validFields()) {
		//submit the form
		register_form.action = "reg_submit";
		register_form.submit();
	} else {
		//displays all error messages
		displayRegisterErrors();
	}
});

/**
 * adds a focus out event function to check for username
 * send an AJAX (Asynchronous Javascript And XML) request to web server
 * 	server  - query database with the login username
 * 			- returns back a message ("FOUND" / "NOT FOUND") to AJAX function
 * 
 * @returns boolean (true / false)
 */
document.getElementById("user_name").addEventListener("focusout", function() {
	var usr_name_val = this.value;
	
	var username_msgField = document.getElementById("username-err");
	
	if(usr_name_val != "") {
		if(usr_name_val.indexOf(" ") >= 0) {
			username_msgField.style.color = "red";
			username_msgField.innerHTML = "Username should NOT contains whitespace";
		}
		else {
			$.ajax
		    (
		        {
		            url:'check_user',
		            data:{ "user_name": usr_name_val },
		            type:'get',
		            cache:false,
		            success:function(data){
		            	username_msgField = document.getElementById("username-err");
		        		
		        		if(data == "FOUND") {
		        			username_msgField.style.color = "red";
		        			username_msgField.innerHTML = "Username existed";
		        			userExisted = true;
		        		} else {
		        			username_msgField.style.color = "green";
		        			username_msgField.innerHTML = "Username available";
		        			userExisted = false;
		        		}
		            },
		            error:function(){
		            	alert("error");
		            }
		        }
		    );
		}
	}
});

/**
 * add a focus in event to user password input field
 * set error message field to empty
 */
document.getElementById("user_password").addEventListener("focusin", function() {
	document.getElementById("password-err").innerHTML = "";
});

/**
 * add a focus out event to user password input field
 * check for password length when focus changes from this field to other 
 */
document.getElementById("user_password").addEventListener("focusout", function() {
	var usr_password_val = this.value;
	
	if(usr_password_val == "")
	{
		return;
	}
	
	if(usr_password_val.length < 8)
	{
		document.getElementById("password-err").innerHTML = "Password MUST consists of 8+ characters";
	}
});

/**
 * add a focus in event to user email input field
 * set error message field to empty
 */
document.getElementById("user_email").addEventListener("focusin", function() {
	document.getElementById("email-err").innerHTML = "";
});

/**
 * add a focus out event to user email input field
 * check for valid email when focus changes from this field to other 
 */
document.getElementById("user_email").addEventListener("focusout", function() {
	var usr_email_val = this.value;
	
	if(usr_email_val == "")
	{
		return;
	}
	
	if(!usr_email_val.match(/^[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+(\.[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+)?@[a-z0-9]+\.[a-z0-9]{2,}$/))
	{
		document.getElementById("email-err").innerHTML = "Invalid email address";
	}
});

/**
 * function to check all the fields in register form
 * 
 * @returns boolean (true / false)
 */
function register_validFields() {
	var success = true;
	
	fullname_errMsg = username_errMsg = email_errMsg = password_errMsg = gender_errMsg = country_errMsg = dob_errMsg = terms_errMsg = "";
	
	var firstName = document.getElementById("first_name").value;
	var lastName = document.getElementById("last_name").value;
	var userName = document.getElementById("user_name").value;
	var userEmail = document.getElementById("user_email").value;
	var userPassword = document.getElementById("user_password").value;
	var userGender = document.getElementsByName("user_gender");
	var countrySelected = document.getElementById("country").value;
	var dateOfBirth = document.getElementById("date_of_birth").value;
	var termsAndPrivacy = document.getElementById("terms_and_privacy").checked;
	
	var fn_error = false;
	
	if(firstName == "")
	{
		fn_error = true;
		success = false;
		fullname_errMsg = "First name is required";
	}
	
	if(lastName == "")
	{
		if(fn_error) {
			success = false;
			fullname_errMsg = "First and Last name are required";
		}
		else {
			success = false;
			fullname_errMsg = "Last name is required";
		}
	}
	
	if(userName == "")
	{
		success = false;
		username_errMsg = "Username is required";
	}
	else if(userName.indexOf(" ") >= 0)
	{
		success = false;
		username_errMsg = "Username should NOT contains whitespace";
	}
	else if(userExisted)
	{
		success = false;
		username_errMsg = "Username existed";
	}
	
	if(userEmail == "")
	{
		success = false;
		email_errMsg = "Email address is required";
	}
	else if(!userEmail.match(/^[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+(\.[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+)?@[a-z0-9]+\.[a-z0-9]{2,}$/))
	{
		success = false;
		email_errMsg = "Invalid email address";
	}
	
	if(userPassword == "")
	{
		success = false;
		password_errMsg = "Password is required";
	}
	else if(userPassword.length < 8)
	{
		success = false;
		password_errMsg = "Password MUST consists of 8+ characters";
	}
	
	if(userGender[0].checked === false && userGender[1].checked === false)
	{
		success = false;
		gender_errMsg = "Gender is required";
	}
	
	if(countrySelected == "")
	{
		success = false;
		country_errMsg = "Country is required";
	}
	
	if(dateOfBirth == "")
	{
		success = false;
		dob_errMsg = "Date of birth is required";
	}
	
	if(termsAndPrivacy === false)
	{
		success = false;
		terms_errMsg = "Please tick the checkbox";
	}
	
	return success;
}

// function to display login error message(s)
function displayRegisterErrors()
{
	document.getElementById("fullname-err").innerHTML = fullname_errMsg;
	document.getElementById("username-err").style.color = "red";
	document.getElementById("username-err").innerHTML = username_errMsg;
	document.getElementById("email-err").innerHTML = email_errMsg;
	document.getElementById("password-err").innerHTML = password_errMsg;
	document.getElementById("gender-err").innerHTML = gender_errMsg;
	document.getElementById("country-err").innerHTML = country_errMsg;
	document.getElementById("dob-err").innerHTML = dob_errMsg;
	document.getElementById("terms-err").innerHTML = terms_errMsg;
}