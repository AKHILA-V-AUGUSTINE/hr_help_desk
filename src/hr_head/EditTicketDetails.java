package hr_head;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/editticketdetails")
public class EditTicketDetails extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "1234";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			/*
			 * String id=request.getParameterValues("eid"); int
			 * itemId=Integer.parseInt(id); OR
			 */

			int id = Integer.parseInt(request.getParameter("id"));

			pst = con.prepareStatement("select * from ticket_create where ticket_id="+id+"");
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");


			out.println("<html><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.print("Welcome " + name+"...");
			out.println("<h1>User Details</h1>");
			out.print("<form name='edit-item' action='updateticketcategory' method='post'>");
			while (rs.next()) {
				out.print("<input type='hidden' name='catId' value='" + rs.getInt(1) + "'><br><br>");
				out.print("Name:<br><input type='text' name='catName' value='" + rs.getString(2) + "'><br><br>");
				
				
				
			}
			out.print("<input type='submit' value='UPDATE'>");

			out.print("</form>");
			out.print("</div></body>");
			out.print("</html>");
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
