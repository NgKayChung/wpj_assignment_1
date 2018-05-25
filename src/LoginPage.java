import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Cookie existingCookies[] = request.getCookies();
		
		if(existingCookies != null) {
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
						"<div style = \"padding: 25px;  text-align: center;\">\r\n" +
						"	<h2 style = \"padding: 20px 0px;\">Already logged in</h2>\r\n" +
						"	<div class = \"goto-btn-container\">" +
						"		<a href = \"home\" class = \"goto-btn\">Back to Home</a>\r\n" +
						"	</div>\r\n" +
						"</div>\r\n" +
						"</body>\r\n" +
						"</html>");
				return;
			}
		}
		
		out.println("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<title>Login to Account | My Website</title>\r\n" + 
				"<link rel = \"stylesheet\" href = \"include/css/style.css\" />\r\n" + 
				"<script type = \"text/javascript\" src = \"include/js/jquery-latest-min.js\"></script>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<div class = \"container\">\r\n" + 
				"		<div class = \"col lr-col\"></div>\r\n" + 
				"		<div class = \"col center-col\">\r\n" + 
				"			<div class = \"row ud-row\"></div>\r\n" + 
				"			<div class = \"row center-row\">\r\n" + 
				"				<div class = \"form-title\">\r\n" + 
				"					<h2><label>Login to My Website account</label></h2>\r\n" + 
				"				</div>\r\n" + 
				"				<div class = \"form-container\">\r\n" + 
				"					<form id = \"loginForm\" method = \"post\">\r\n" + 
				"						<ul class = \"log-info\">\r\n" + 
				"							<li>\r\n" + 
				"								<div class = \"input-field\">\r\n" + 
				"									<input type = \"text\" id = \"user_identifier\" class = \"text-field\" name = \"user_identifier\" placeholder = \"Username or email\" />\r\n" + 
				"								</div>\r\n" + 
				"								<div class = \"err-field\">\r\n" + 
				"									<p id = \"identifier-err\" class = \"err-input\"></p>\r\n" + 
				"								</div>\r\n" + 
				"							</li>\r\n" + 
				"							<li>\r\n" + 
				"								<div class = \"input-field\">\r\n" + 
				"									<input type = \"password\" id = \"user_password\" class = \"text-field\" name = \"user_password\" placeholder = \"Password\" />\r\n" + 
				"								</div>\r\n" + 
				"								<div class = \"err-field\">\r\n" + 
				"									<p id = \"password-err\" class = \"err-input\"></p>\r\n" + 
				"								</div>\r\n" + 
				"							</li>\r\n" + 
				"							<li>\r\n" + 
				"								<div class = \"err-field\">\r\n" + 
				"									<p id = \"login-err\" class = \"err-input\"></p>\r\n" + 
				"								</div>\r\n" + 
				"							</li>\r\n" + 
				"							<li>\r\n" + 
				"								<div class = \"login-btn-container\">\r\n" + 
				"									<a href = \"javascript:void(0);\" id = \"login-account\" class = \"submit login-btn\">Login</a>\r\n" + 
				"								</div>\r\n" + 
				"							</li>\r\n" + 
				"							<li>\r\n" + 
				"								<div class = \"goto-btn-container\">\r\n" + 
				"									<a href = \"register\" class = \"goto-btn\">Not having an account?</a>\r\n" + 
				"								</div>\r\n" + 
				"							</li>\r\n" + 
				"						</ul>\r\n" + 
				"					</form>\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"			<div class = \"row ud-row\"></div>\r\n" + 
				"		</div>\r\n" + 
				"		<div class = \"col lr-col\"></div>\r\n" + 
				"	</div>\r\n" + 
				"	<script type = \"text/javascript\" src = \"include/js/script_login.js\" ></script>\r\n" + 
				"</body>\r\n" + 
				"</html>");
	}

}
