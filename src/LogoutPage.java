import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/logout")
public class LogoutPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		session.removeAttribute("UNAME_KEY");
		
		Cookie sessionCookie = request.getCookies()[0];
		sessionCookie.setMaxAge(0);
		response.addCookie(sessionCookie);
		
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
				"	<h2 style = \"padding: 20px 0px;\">Logged Out Successfully</h2>\r\n" +
				"	<div class = \"goto-btn-container\">" +
				"		<a href = \"home\" class = \"goto-btn\">Back to Home</a>\r\n" +
				"	</div>\r\n" +
				"</div>\r\n" +
				"</body>\r\n" +
				"</html>");
	}
}