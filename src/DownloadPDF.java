import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/download_pdf")
public class DownloadPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		Document document = new Document();
		
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            
            document.open();
            
            document.addTitle("MY WEBSITE - Profile Details");
            
            Font datetime_f = new Font();
            datetime_f.setStyle(Font.ITALIC);
            datetime_f.setSize(8);
            
            Paragraph datetime_p = new Paragraph(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-LLL-uuuu, HH:mm:ss")), datetime_f);
            datetime_p.setAlignment(Element.ALIGN_RIGHT);
            
            Font sitename_f = new Font();
            sitename_f.setStyle(Font.BOLD);
            sitename_f.setSize(22);
            
            Paragraph sitename_p = new Paragraph("MY WEBSITE", sitename_f);
            sitename_p.setAlignment(Element.ALIGN_CENTER);
            
            Paragraph empty_p = new Paragraph(" ");
            
            Font title_f = new Font();
            title_f.setStyle(Font.BOLD);
            title_f.setSize(18);
            
            Paragraph title_p = new Paragraph("Profile Information", title_f);
            
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_website_db", "user1", "1111");
			
			Statement stmt = conn.createStatement();
			String sql_query = "SELECT * FROM `usr_accnt` WHERE `usr_unq_name` = '" + (String)request.getSession().getAttribute("UNAME_KEY") + "';";
			ResultSet results = stmt.executeQuery(sql_query);
            
			if(results.next()) {
				Paragraph firstname_p = new Paragraph(String.format("%-20s : %s", "First Name", results.getString("usr_firstName")));
	            Paragraph lastname_p = new Paragraph(String.format("%-20s : %s", "Last Name", results.getString("usr_lastName")));
	            Paragraph username_p = new Paragraph(String.format("%-20s : %s", "Username", results.getString("usr_unq_name")));
	            Paragraph emailAddress_p = new Paragraph(String.format("%-20s : %s", "Email Address", results.getString("usr_emailAddress")));
	            Paragraph password_p = new Paragraph(String.format("%-20s : %s", "Password", results.getString("usr_password")));
	            Paragraph gender_p = new Paragraph(String.format("%-20s : %s", "Gender", results.getString("usr_gender")));
	            Paragraph country_p = new Paragraph(String.format("%-20s : %s", "Country", results.getString("usr_country")));
	            Paragraph dateOfBirth_p = new Paragraph(String.format("%-20s : %s", "Date of Birth", results.getString("usr_dateOfBirth")));
	            
	            document.add(datetime_p);
	            document.add(sitename_p);
	            document.add(empty_p);
	            document.add(title_p);
	            document.add(empty_p);
	            document.add(firstname_p);
	            document.add(lastname_p);
	            document.add(username_p);
	            document.add(emailAddress_p);
	            document.add(password_p);
	            document.add(gender_p);
	            document.add(country_p);
	            document.add(dateOfBirth_p);
			} else {
				Font err_f = new Font();
				err_f.setSize(24);
				err_f.setStyle(Font.BOLD);
				err_f.setColor(255, 0, 0);
				
				Paragraph err_p = new Paragraph("Error occured", err_f);
				err_p.setAlignment(Element.ALIGN_CENTER);
				
				document.add(err_p);
			}
			
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        } catch(Exception ex) {
        	System.out.println("Exception thrown : " + ex.toString());
        }
	}
}