package employee;

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

@WebServlet("/employeeviewticket")
public class EmployeeViewTicket extends HttpServlet {
	

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
			String sql = "SELECT ticket_create.ticket_id, ticket_create.ticket_date, users_personal_details.user_per_username,ticket_create.ticket_category " +
		             "FROM ticket_create " +
		             "JOIN users_personal_details ON ticket_create.user_per_id = users_personal_details.user_per_id";

			
			/*String sql = "SELECT tc.ticket_id, tc.ticket_date, upd.user_per_username, tc.ticket_category" +
		             "FROM ticket_create tc " +
		             "JOIN users_personal_details upd ON tc.user_per_id = upd.user_per_id " +
		             "JOIN ticket_status ts ON tc.ticket_id = ts.ticket_id " +
		             "JOIN allocation_ticket a ON tc.ticket_id = a.ticket_id";*/

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			String name = (String) session.getAttribute("user");

			out.println("<html><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.print("Welcome " + name+"...");
			out.println("<h1>View Ticket Status Details</h1>");
			out.println("<table border='1'style='margin: auto;'>");

			out.println("<tr>");
			
			
			out.println("<th>Id</th>");
			out.println("<th>Ticket Id</th>");
			out.println("<th>Ticket Rised Date</th>");
			out.println("<th>Ticket Rised By</th>");
			out.println("<th>Ticket Category</th>");
			
			out.println("<th>Status View</th>");
			
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			
			int i=1;

			while (rs.next()) {

				out.println("<tr>");
				out.println("<td>" + i + "</td>");
				out.println("<td>" + rs.getInt(1) + "</td>"); 
                out.println("<td>" + rs.getDate(2) + "</td>"); 
                out.println("<td>" + rs.getString(3) + "</td>"); 
                out.println("<td>" + rs.getString(4) + "</td>");
                
				
                out.print("<td><a href='statusview?id=" + rs.getInt(1) + "'>StatusView</a></td>");
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
