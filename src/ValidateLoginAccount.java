import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/check_login")
public class ValidateLoginAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			String userIdentifier = request.getParameter("user_identifier");
			String userLoginType = (userIdentifier.matches("^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)?@[a-z0-9]+\\.[a-z0-9]{2,}$") ? "EMAIL" : "USERNAME");
			String userPassword = request.getParameter("user_password");
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			
			String sql_query = "";
			
			if(userLoginType.equals("USERNAME"))
			{
				sql_query = "SELECT `usr_unq_name`, `usr_password` FROM `usr_accnt` WHERE `usr_unq_name` = BINARY '" + userIdentifier + "' AND `usr_password` = BINARY '" + userPassword + "';";
			}
			else if(userLoginType.equals("EMAIL"))
			{
				sql_query = "SELECT `usr_emailAddress`, `usr_password` FROM `usr_accnt` WHERE `usr_emailAddress` LIKE '" + userIdentifier + "' AND `usr_password` = BINARY '" + userPassword + "';";
			}
			
			ResultSet results = stmt.executeQuery(sql_query);
			
			String userCredentialState = "";
			
			if(results.next()) {
				userCredentialState = "VALID";
			} else {
				userCredentialState = "INVALID";
			}
			
			stmt.close();
			conn.close();
			
			out.write(userCredentialState);
		}
		catch(Exception ex) {
			System.out.println("Exception Thrown : " + ex.toString());
		}
	}
}