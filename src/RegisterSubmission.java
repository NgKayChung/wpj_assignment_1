import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/reg_submit")
public class RegisterSubmission extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			//get all the values from the register form
			String firstname = request.getParameter("first_name");
			String lastname = request.getParameter("last_name");
			String username = request.getParameter("user_name");
			String emailAddress = request.getParameter("user_email");
			String password = request.getParameter("user_password");
			String gender = request.getParameter("user_gender");
			String country = request.getParameter("country");
			String dateOfBirth = request.getParameter("date_of_birth");
			
			//if existing cookies, delete them
			Cookie existingCookies[] = request.getCookies();
			
			if(existingCookies != null)
			{
				for (int i = 0; i < existingCookies.length; i++) {
		            Cookie cookie = existingCookies[i];

		            cookie.setMaxAge(0);
		            response.addCookie(cookie);
		         }
			}
			
			//create cookies here
			Cookie firstname_cookie = new Cookie("first_name", firstname);
			Cookie lastname_cookie = new Cookie("last_name", lastname);
			Cookie username_cookie = new Cookie("user_name", username);
			Cookie emailAddress_cookie = new Cookie("email_address", emailAddress);
			Cookie password_cookie = new Cookie("password", password);
			Cookie gender_cookie = new Cookie("gender", gender);
			Cookie country_cookie = new Cookie("country", country);
			Cookie dateOfBirth_cookie = new Cookie("date_of_birth", dateOfBirth);
			
			//set max age of the cookies to 10 years
			firstname_cookie.setMaxAge(60*60*24*365*10);
			lastname_cookie.setMaxAge(60*60*24*365*10);
			username_cookie.setMaxAge(60*60*24*365*10);
			emailAddress_cookie.setMaxAge(60*60*24*365*10);
			password_cookie.setMaxAge(60*60*24*365*10);
			gender_cookie.setMaxAge(60*60*24*365*10);
			country_cookie.setMaxAge(60*60*24*365*10);
			dateOfBirth_cookie.setMaxAge(60*60*24*365*10);
			
			//set the cookies to user browser
			response.addCookie(firstname_cookie);
			response.addCookie(lastname_cookie);
			response.addCookie(username_cookie);
			response.addCookie(emailAddress_cookie);
			response.addCookie(password_cookie);
			response.addCookie(gender_cookie);
			response.addCookie(country_cookie);
			response.addCookie(dateOfBirth_cookie);
			
			out.println("<!DOCTYPE HTML>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ "<title>Register Account Submission</title>\n"
					+ "<link rel = \"stylesheet\" href = \"include/css/style.css\" />\n"
					+ "<style type = \"text/css\">\n"
					+ "p\n"
					+ "{\n" 
					+ "	margin: 3px 0px;\n"
					+ "}\n"
					+ "</style>\n"
					+ "</head>\n"
					+ "<body>\n"
					+ "	<div style = \"padding: 10px;\">\n"
					+ "		<h2 style = \"margin: 5px 0px;\">Your Information</h2>\n"
					+ "		<p><label><b>First Name : </b></label>" + firstname + "</p>\n"
					+ "		<p><label><b>Last Name : </b></label>" + lastname + "</p>\n"
					+ "		<p><label><b>Username : </b></label>" + username + "</p>\n"
					+ "		<p><label><b>Email Address : </b></label>" + emailAddress + "</p>\n"
					+ "		<p><label><b>Password : </b></label>" + password + "</p>\n"
					+ "		<p><label><b>Gender : </b></label>" + gender + "</p>\n"
					+ "		<p><label><b>Country : </b></label>" + country + "</p>\n"
					+ "		<p><label><b>Date of Birth : </b></label>" + dateOfBirth + "</p>\n"
					+ "		<p style = \"margin: 20px 0px;\"><a class = \"edit-btn\" href = \"register\">Edit</a><a class = \"confirm-btn\" href = \"reg_confirm\">Confirm</a>\n"
					+ " </div>\n"
					+ "</body>\n"
					+ "</html>");
		}
		catch (Exception ex) {
			System.out.println("Exception thrown : " + ex.toString());
		}
	}
}