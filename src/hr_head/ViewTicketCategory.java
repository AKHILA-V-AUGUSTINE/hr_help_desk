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

public class ViewTicketCategory extends HttpServlet {
	

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
			String sql = "select * from ticket_category";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");

			

			out.println("<html><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.print("Welcome " + name+"...");
			out.println("<h1>View Ticket Category</h1>");
			out.println("<table border='1' style='margin: auto;'>");

			out.println("<tr>");
			
			
			out.println("<th>Id</th>");
			out.println("<th>Category Name</th>");
			
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			
			int i=1;

			while (rs.next()) {

				out.println("<tr>");
				out.println("<td>" + i + "</td>");
				
				out.println("<td>" + rs.getString(2) + "</td>");
				
				
				out.print("<td><a href='editticketcategory?id=" + rs.getInt(1) + "'>Edit</a></td>");
				out.print("<td><a href='deleteticketcategory?id=" + rs.getInt(1) + "'>Delete</a></td>");
				out.println("</tr>");
				i++;
			}

			out.println("</table>");
			out.println("</div></body></html>");

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

}
