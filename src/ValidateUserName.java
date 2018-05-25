import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/check_user")
public class ValidateUserName extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			String username = request.getParameter("user_name");
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			String sql_query = "SELECT `usr_unq_name` FROM `usr_accnt` WHERE `usr_unq_name` = BINARY '" + username + "'";
			ResultSet results = stmt.executeQuery(sql_query);
			
			String searchResString = "";
			
			if(results.next()) {
				searchResString = "FOUND";
			} else {
				searchResString = "NOT FOUND";
			}
			
			stmt.close();
			conn.close();
			
			out.write(searchResString);
		}
		catch(Exception ex) {
			System.out.println("Exception Thrown : " + ex.toString());
		}
	}
}