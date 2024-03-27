package hr_head;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditUsers extends HttpServlet {

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

			pst = con.prepareStatement("select * from users_personal_details where user_per_id=" + id + "");
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");

			out.println("<html><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.print("Welcome " + name+"...");
			out.println("<h1>User Details</h1>");
			out.print("<form name='edit-item' action='updateuser' method='post'>");
			while (rs.next()) {
				out.print("<input type='hidden' name='userId' value='" + rs.getInt(1) + "'><br><br>");
				out.print("Name:<br><input type='text' name='userName' value='" + rs.getString(2) + "'><br><br>");
				out.print("Contact:<br><input type='text' name='userContact' value='" + rs.getString(3) + "'><br><br>");
				out.print("Email:<br><input type='text' name='userEmail' value='" + rs.getString(4) + "'><br><br>");
				out.print("UserName:<br><input type='text' name='userUserName' value='" + rs.getString(5) + "'><br><br>");
				out.print("Password:<br><input type='text' name='userPassword' value='" + rs.getString(6) + "'><br><br>");
				out.print("Role:<br><input type='text' name='userRole' value='" + rs.getString(7) + "'><br><br>");
				
				
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
