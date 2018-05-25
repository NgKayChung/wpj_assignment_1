import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/home")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Cookie existingCookies[] = request.getCookies();
		
		if(existingCookies == null) {
			out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=\"UTF-8\">\r\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
					"<title>My Website</title>\r\n" + 
					"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<header>\r\n" + 
					"		<div class = \"header-item\"><a href = \"home\">Home</a></div>\r\n" + 
					"		<div class = \"header-item\"><a href = \"register\">Register an Account</a></div>\r\n" + 
					"		<div class = \"header-item\"><a href = \"login\">Login to Account</a></div>\r\n" + 
					"	</header>\r\n" + 
					"</body>\r\n" + 
					"</html>");
		}
		else if(existingCookies.length == 1 && existingCookies[0].getName().equals("JSESSIONID")) {
			out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=\"UTF-8\">\r\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
					"<title>My Website</title>\r\n" + 
					"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<header>\r\n" + 
					"		<div class = \"header-item\"><a href = \"home\">Home</a></div>\r\n" + 
					"		<div class = \"header-item\"><a href = \"logout\">Logout</a></div>\r\n" + 
					"	</header>\r\n" + 
					"	<h2 style = \"margin: 20px;\"><label>Hello " + (String)request.getSession().getAttribute("UNAME_KEY") + "!</label></h2>\r\n" +
					"</body>\r\n" + 
					"</html>");
		}
		
		
	}
}