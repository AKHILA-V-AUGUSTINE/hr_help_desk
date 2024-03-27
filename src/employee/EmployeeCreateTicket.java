package employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createticket")
public class EmployeeCreateTicket extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "1234";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);

			String ticketdate = request.getParameter("ticketdate");
			String ticketcategory = request.getParameter("ticketcategory");
			String ticketdesc = request.getParameter("ticketdesc");
			int user_per_id = 3;

			PreparedStatement pst = con.prepareStatement(
					"INSERT INTO ticket_create (user_per_id,ticket_date,ticket_category,ticket_desc) VALUES (?,?,?,?)");
			pst.setInt(1, user_per_id);
			pst.setString(2, ticketdate);
			pst.setString(3, ticketcategory);
			pst.setString(4, ticketdesc);

			pst.executeUpdate();

			pst.close();
			con.close();

			response.sendRedirect("index.html"); // Redirect to index.html after
													// insertion

		} catch (Exception ex) {
			ex.printStackTrace();
			// Handle the error properly, e.g., display an error message to the
			// user
			response.getWriter().println("Error occurred: " + ex.getMessage());
		}
	}

}
