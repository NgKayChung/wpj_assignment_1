import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/reg_confirm")
public class ConfirmRegistration extends HttpServlet {
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
			
			if(existingCookies != null) {
				//if JSESSIONID exists, this indicates user already logged in -> redirect user back to home page
				//Note - this case only occurs when user edit the browser URL to "localhost:18080/ServletAsignment/reg_confirm"
				if(existingCookies.length == 1 && existingCookies[0].getName().equals("JSESSIONID")) {
					out.println("<!DOCTYPE html>\r\n" + 
							"<html>\r\n" + 
							"<head>\r\n" + 
							"<meta charset=\"UTF-8\">\r\n" + 
							"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
							"<title>Logged in</title>\r\n" + 
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
				
				//1. get cookies value
				//2. delete cookies
				for(Cookie cookie : existingCookies)
				{
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					
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
				
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
				
				//store the registration information into database
				Statement stmt = conn.createStatement();
				String sql_query = "INSERT INTO usr_accnt(`usr_firstName`, `usr_lastName`, `usr_unq_name`, `usr_emailAddress`, `usr_password`, `usr_gender`, `usr_country`, `usr_dateOfBirth`) "
						+ "VALUES('" + firstname_val + "', '" + lastname_val + "', '" + username_val + "', '" + emailAddress_val + "', '" + password_val + "', '" + gender_val + "', '" + country_val + "', '" + dateOfBirth_val + "');";
				stmt.execute(sql_query);
				
				stmt.close();
				conn.close();
				
				//set user login session
				//username as key
				HttpSession session = request.getSession();
				session.setAttribute("UNAME_KEY", username_val);
				
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<head>\r\n" + 
						"<meta charset=\"UTF-8\">\r\n" + 
						"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
						"<title>Registration Successful</title>\r\n" + 
						"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
						"</head>\r\n" + 
						"<body>\r\n" + 
						"<div style = \"padding: 25px; text-align: center;\">\r\n" +
						"	<h2 style = \"padding: 20px 0px;\"><label>Account Registered Successfully</label></h2>\r\n" +
						"	<center>\r\n" +
						"		<table border = \"1\" style = \"text-align: left;\">\r\n" +
						"			<tr align = \"center\">\r\n" +
						"				<th><label>Field</label></th>\r\n" +
						"				<th><label>Data</label></th>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>First Name</label></td>\r\n" +
						"				<td><label>" + firstname_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Last Name</label></td>\r\n" +
						"				<td><label>" + lastname_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Username</label></td>\r\n" +
						"				<td><label>" + username_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Email Address</label></td>\r\n" +
						"				<td><label>" + emailAddress_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Password</label></td>\r\n" +
						"				<td><label>" + password_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Gender</label></td>\r\n" +
						"				<td><label>" + gender_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Country</label></td>\r\n" +
						"				<td><label>" + country_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"			<tr>\r\n" +
						"				<td><label>Date of Birth</label></td>\r\n" +
						"				<td><label>" + dateOfBirth_val + "</label></td>\r\n" +
						"			</tr>\r\n" +
						"		</table>\r\n" +
						"	</center>\r\n" +
						"	<p style = \"margin-top: 20px;\"><label>Download as <a target = \"_blank\" href = \"download_txt\" class = \"goto-btn\">TXT</a> or <a target = \"_blank\" href = \"download_pdf\" class = \"goto-btn\">PDF</a></label></p>\r\n" +
						"	<div class = \"goto-btn-container\">\r\n" +
						"		<a href = \"home\" class = \"goto-btn\">Go to Home</a>\r\n" +
						"	</div>\r\n" +
						"</div>\r\n" +
						"</body>\r\n" +
						"</html>");
			}
		}
		catch(Exception ex) {
			System.out.println("Exception thrown : " + ex.toString());
		}
	}
}