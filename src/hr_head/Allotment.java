package hr_head;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/allotment")
public class Allotment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "1234";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			// Retrieve session attributes
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");

			// Retrieve parameters from the request
			String ticket_id= request.getParameter("id");
			String allotDate = request.getParameter("allot_date");
			String allotName = request.getParameter("allot_name");

			// Validate if ticketIdParam is null or empty
			/*if (ticket_id == null || ticket_id.isEmpty()) {
				out.println("Ticket ID is missing.");
				return; // Exit the method
			}
*/
			// Convert ticketIdParam to integer
			int ticketid = Integer.parseInt(ticket_id);

			// Establish database connection
			Connection con = DriverManager.getConnection(url, user, password);

			// Prepare SQL query
			String sql = "INSERT INTO allocation_ticket (ticket_id, allot_date, allot_name) VALUES (?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			// Set parameters for the query
			pstmt.setInt(1, ticketid);
			pstmt.setString(2, allotDate);
			pstmt.setString(3, allotName);

			// Execute the query
			pstmt.executeUpdate();

			// Close the PreparedStatement and Connection
			pstmt.close();
			con.close();

			// Display response to user
			out.println("<html><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.println("Welcome " + name + "...");
			out.println("<h1>Allotment Successful</h1>");
			out.println("</div></body></html>");

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			
		}
	}
}
