import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/download_txt")
public class DownloadTXT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=download.txt");
		
		try {
			PrintWriter outfile = response.getWriter();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			String sql_query = "SELECT * FROM `usr_accnt` WHERE `usr_unq_name` = '" + (String)request.getSession().getAttribute("UNAME_KEY") + "';";
			ResultSet results = stmt.executeQuery(sql_query);
			
			String outputString = "";
			
			if(results.next()) {
				outputString = "Your Information\r\n";
				outputString += "--------------------\r\n";
				outputString += String.format("%-15s : %s%n", "First Name", results.getString("usr_firstName"));
				outputString += String.format("%-15s : %s%n", "Last Name", results.getString("usr_lastName"));
				outputString += String.format("%-15s : %s%n", "Username", results.getString("usr_unq_name"));
				outputString += String.format("%-15s : %s%n", "Email Address", results.getString("usr_emailAddress"));
				outputString += String.format("%-15s : %s%n", "Password", results.getString("usr_password"));
				outputString += String.format("%-15s : %s%n", "Gender", results.getString("usr_gender"));
				outputString += String.format("%-15s : %s%n", "Country", results.getString("usr_country"));
				outputString += String.format("%-15s : %s", "Date of Birth", results.getString("usr_dateOfBirth"));
			}
			else {
				outputString = "Error occured";
			}
			
		    outfile.write(outputString);
		    outfile.close();
		}
		catch(Exception ex) {
			System.out.println("Exception thrown : " + ex.toString());
		}
	}
}