import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

// this Servlet page will call by javascript (AJAX function)
// for checking user login data
@WebServlet("/check_login")
public class ValidateLoginAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			// determine the user login type
			// username or email
			String userIdentifier = request.getParameter("user_identifier");
			String userLoginType = (userIdentifier.matches("^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)?@[a-z0-9]+\\.[a-z0-9]{2,}$") ? "EMAIL" : "USERNAME");
			String userPassword = request.getParameter("user_password");
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			
			String sql_query = "";
			
			// #if login using username
			//		query user record using username and password
			// #else if login using email
			//		query user record using email address and password
			// #endif
			if(userLoginType.equals("USERNAME"))
			{
				sql_query = "SELECT * FROM `usr_accnt` WHERE `usr_unq_name` = BINARY '" + userIdentifier + "' AND `usr_password` = BINARY '" + userPassword + "';";
			}
			else if(userLoginType.equals("EMAIL"))
			{
				sql_query = "SELECT * FROM `usr_accnt` WHERE `usr_emailAddress` = '" + userIdentifier + "' AND `usr_password` = BINARY '" + userPassword + "';";
			}
			
			ResultSet results = stmt.executeQuery(sql_query);
			
			String userCredentialState = "";
			
			// if record is found set to VALID, otherwise INVALID
			if(results.next()) {
				userCredentialState = "VALID";
			} else {
				userCredentialState = "INVALID";
			}
			
			stmt.close();
			conn.close();
			
			// return (record found) value to javascript function
			out.write(userCredentialState);
		}
		catch(Exception ex) {
			System.out.println("Exception Thrown : " + ex.toString());
		}
	}
}