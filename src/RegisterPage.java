import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/register")
public class RegisterPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			Cookie existingCookies[] = request.getCookies();
			
			String firstname_val = "";
			String lastname_val = "";
			String username_val = "";
			String emailAddress_val = "";
			String password_val = "";
			String gender_val = "";
			String country_val = "";
			String dateOfBirth_val = "";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			String sql_query = "SELECT * FROM `country_tb` ORDER BY country_name ASC;";
			ResultSet results = stmt.executeQuery(sql_query);
			
			ArrayList<String> countries = new ArrayList<String>();
			
			while(results.next()) {
				countries.add(results.getString("country_name"));
			}
			
			stmt.close();
			conn.close();
			
			if(existingCookies != null) {
				//retrieve cookies value and assign to the variables -> to set the values to the form
				//if JSESSIONID exists, this indicates user already logged in -> redirect user back to home page
				if(existingCookies.length == 1 && existingCookies[0].getName().equals("JSESSIONID")) {
					out.println("<!DOCTYPE html>\r\n" + 
							"<html>\r\n" + 
							"<head>\r\n" + 
							"<meta charset=\"UTF-8\">\r\n" + 
							"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
							"<title>Logged in</title>\r\n" + 
							"<script type = \"text/javascript\" src = \"include/js/jquery-latest-min.js\"></script> \r\n" + 
							"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
							"</head>\r\n" + 
							"<body>\r\n" + 
							"<div style = \"padding: 25px; text-align: center;\">\r\n" +
							"	<h2 style = \"padding: 20px 0px;\">Already logged in</h2>\r\n" +
							"	<div class = \"goto-btn-container\">" +
							"		<a href = \"home\" class = \"goto-btn\">Back to Home</a>\r\n" +
							"	</div>\r\n" +
							"</div>\r\n" +
							"</body>\r\n" +
							"</html>");
					return;
				}
				
				for(Cookie cookie : existingCookies)
				{
					switch(cookie.getName())
					{
						case "first_name":
							firstname_val = cookie.getValue();
							break;
							
						case "last_name":
							lastname_val = cookie.getValue();
							break;
							
						case "user_name":
							username_val = cookie.getValue();
							break;
							
						case "email_address":
							emailAddress_val = cookie.getValue();
							break;
							
						case "password":
							password_val = cookie.getValue();
							break;
							
						case "gender":
							gender_val = cookie.getValue();
							break;
							
						case "country":
							country_val = cookie.getValue();
							break;
							
						case "date_of_birth":
							dateOfBirth_val = cookie.getValue();
							break;
					}
				}
			}
			
			String outputHTML = "<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=\"UTF-8\">\r\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
					"<title>Register an Account | My Website</title>\r\n" + 
					"<script type = \"text/javascript\" src = \"include/js/jquery-latest-min.js\"></script> \r\n" + 
					"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<div class = \"container\">\r\n" + 
					"		<div class = \"col lr-col\"></div>\r\n" + 
					"		<div class = \"col center-col\">\r\n" + 
					"			<div class = \"ud-row\"></div>\r\n" + 
					"			<div class = \"center-row\">\r\n" + 
					"				<div class = \"form-title\">\r\n" + 
					"					<h2><label>Register for a new account</label></h2>\r\n" + 
					"				</div>\r\n" + 
					"				<div class = \"form-container\">\r\n" + 
					"					<form id = \"regForm\" method = \"post\">\r\n" + 
					"						<ul class = \"reg-info\">\r\n" + 
					"							<li>\r\n" + 
					"								<div class = \"input-field inline-field\">\r\n" + 
					"									<input type = \"text\" id = \"first_name\" class = \"text-field\" name = \"first_name\" placeholder = \"First Name\" value = \"" + firstname_val + "\" />\r\n" + 
					"								</div>\r\n" + 
					"								<div class = \"input-field inline-field last-name-field clear-fix\">\r\n" + 
					"									<input type = \"text\" id = \"last_name\" class = \"text-field\" name = \"last_name\" placeholder = \"Last Name\" value = \"" + lastname_val + "\" />\r\n" + 
					"								</div>\r\n" + 
					"								<div class = \"err-field\">\r\n" + 
					"									<p id = \"fullname-err\" class = \"err-input\"></p>\r\n" + 
					"								</div>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<input type = \"text\" id = \"user_name\" class = \"text-field\" name = \"user_name\" placeholder = \"Username\" value = \"" + username_val + "\" />\r\n" + 
					"								<p id = \"username-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<input type = \"text\" id = \"user_email\" class = \"text-field\" name = \"user_email\" placeholder = \"Email\" value = \"" + emailAddress_val + "\" /><br/>\r\n" + 
					"								<p id = \"email-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<input type = \"password\" id = \"user_password\" class = \"text-field\" name = \"user_password\" placeholder = \"Password\" value = \"" + password_val + "\" /><br/>\r\n" + 
					"								<p id = \"password-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" +
					"							<li>\r\n" + 
					"								<label>Gender</label><br/>\r\n" + 
					"								<input type = \"radio\" name = \"user_gender\" value = \"MALE\" " + (gender_val.equals("MALE") ? "checked" : "") + " />Male<input type = \"radio\" name = \"user_gender\" value = \"FEMALE\" " + (gender_val.equals("FEMALE") ? "checked" : "") + " />Female<br/>\r\n" + 
					"								<p id = \"gender-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" +
					"							<li>\r\n" + 
					"								<label>Country</label><br/>\r\n" + 
					"								<select id = \"country\" name = \"country\">\r\n" +
					"									<option value = \"\">Select Country</option>\r\n";
			
			//
			//print all countries
			for(String country : countries) {
				outputHTML += "<option value = \"" + country + "\" " + (country.equals(country_val) ? "selected" : "") + " >" + country + "</option>\r\n";
			}
			
			outputHTML += "							</select><br/>\r\n" + 
					"								<p id = \"country-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<label>Date Of Birth</label><br/>\r\n" + 
					"								<input type = \"date\" id = \"date_of_birth\" name = \"date_of_birth\" value = \"" + dateOfBirth_val + "\" /><br/>\r\n" + 
					"								<p id = \"dob-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<input type = \"checkbox\" id = \"terms_and_privacy\" name = \"terms_and_privacy\" /><label> I have agreed to Terms of Service and Privacy Policy</label><br/>\r\n" + 
					"								<p id = \"terms-err\" class = \"err-input\"></p>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<div class = \"register-btn-container\">\r\n" + 
					"									<a href = \"javascript:void(0);\" id = \"register-account\" class = \"submit register-btn\">Register For Free</a>\r\n" + 
					"								</div>\r\n" + 
					"							</li>\r\n" + 
					"							<li>\r\n" + 
					"								<div class = \"goto-btn-container\">\r\n" + 
					"									<a href = \"login\" class = \"goto-btn\">Already Have an Account?</a>\r\n" + 
					"								</div>\r\n" + 
					"							</li>\r\n" + 
					"						</ul>\r\n" + 
					"					</form>\r\n" + 
					"				</div>\r\n" + 
					"			</div>\r\n" + 
					"			<div class = \"ud-row\"></div>\r\n" + 
					"		</div>\r\n" + 
					"		<div class = \"col lr-col\"></div>\r\n" + 
					"	</div>\r\n" + 
					"	<script type = \"text/javascript\" src = \"include/js/script_register.js\" ></script>\r\n" + 
					"</body>\r\n" + 
					"</html>";
			
			out.println(outputHTML);
		}
		catch (Exception ex) {
			System.out.println("Exception thrown : " + ex.toString());
		}
	}
}