import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/login_submit")
public class LoginSubmission extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			String userIdentifier = request.getParameter("user_identifier");
			String login_type =  (userIdentifier.matches("^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)?@[a-z0-9]+\\.[a-z0-9]{2,}$") ? "EMAIL" : "USERNAME");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			
			String sql_query = "";
			
			if(login_type.equals("USERNAME"))
			{
				sql_query = "SELECT `usr_unq_name` FROM `usr_accnt` WHERE `usr_unq_name` = '" + userIdentifier + "';";
			}
			else if(login_type.equals("EMAIL"))
			{
				sql_query = "SELECT `usr_unq_name` FROM `usr_accnt` WHERE `usr_emailAddress` = '" + userIdentifier + "';";
			}
			
			ResultSet results = stmt.executeQuery(sql_query);
			
			HttpSession session = request.getSession();
			session.setAttribute("UNAME_KEY", results.getString("usr_unq_name"));
			
			stmt.close();
			conn.close();
			
			out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=\"UTF-8\">\r\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
					"<title>Login Account</title>\r\n" + 
					"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"<div style = \"padding: 25px; text-align: center;\">\r\n" +
					"	<h2 style = \"padding: 20px 0px;\">Account Logged in Successfully</h2>\r\n" +
					"	<div class = \"goto-btn-container\">" +
					"		<a href = \"home\" class = \"goto-btn\">Back to Home</a>\r\n" +
					"	</div>\r\n" +
					"</div>\r\n" +
					"</body>\r\n" +
					"</html>");
		}
		catch (Exception ex) {
			System.out.println("Exception thrown : " + ex.toString());
		}
	}
}