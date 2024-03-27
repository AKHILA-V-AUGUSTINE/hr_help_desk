package hr_head;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "1234";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String userName = "";
	String userRole = "";
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String uname = request.getParameter("uname");
			String upass = request.getParameter("upass");

			pst = con.prepareStatement(

					"select user_per_name,user_per_role from users_personal_details where user_per_name=? and user_per_password=?");

			pst.setString(1, uname);
			pst.setString(2, upass);

			rs = pst.executeQuery();

			while (rs.next()) {
				userName = rs.getString("user_per_name");
				userRole = rs.getString("user_per_role");

			}
			System.out.println("Username: " + userName);
			System.out.println("User Role: " + userRole);

			// session

			HttpSession session = request.getSession();
			session.setAttribute("user", userName);

			if (userRole.equals("HR Head")) {
				dis = request.getRequestDispatcher("index.html");
				dis.include(request, response);
			} else if (userRole.equals("HR")) {
				dis = request.getRequestDispatcher("hr-home.html");
				dis.include(request, response);
			} else {
				response.sendRedirect("employee-home.html");
			}
			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

}
