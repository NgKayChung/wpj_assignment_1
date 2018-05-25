//add a click event to update profile button
document.getElementById("update-account").addEventListener("click", function() {
	var profile_form = document.getElementById("accForm");
	
	if(updateProfile_validFields()) {
		if(confirm('Are you sure you want to change your profile?')) {
		    //change profile
		}
		else {
			return;
		}
	} else {
		//displays all error messages
		displayRegisterErrors();
	}
});

var textElems = document.querySelectorAll("input[type='text']");

textElems.forEach(function(elem) {
	elem.addEventListener("focusin", function() {
		this.style.borderColor = "grey";
	});
});

function updateProfile_validFields()
{
	var succ = true;
	
	var firstName_field = document.getElementById("first_name");
	var lastName_field = document.getElementById("last_name");
	var email_field = document.getElementById("user_email");
	//var country_field = document.getElementById("country");
	
	if(firstName_field.value == "")
	{
		firstName_field.style.borderColor = "red";
		succ = false;
	}
	
	if(lastName_field.value == "")
	{
		lastName_field.style.borderColor = "red";
		succ = false;
	}
	
	if(email_field.value == "" || !email_field.value.match(/^[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+(\.[A-Za-z0-9\!\#\$\%\&\'\*\+\/\=\?\^\_\`\{\|\}\~\-]+)?@[a-z0-9]+\.[a-z0-9]{2,}$/))
	{
		email_field.style.borderColor = "red";
		succ = false;
	}
	
	return succ;
}