package hr_head;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUsers extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
	final String user = "root";
	final String password = "1234";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			/*
			 * String id=request.getParameterValues("eid"); int
			 * itemId=Integer.parseInt(id); OR
			 */

			int id = Integer.parseInt(request.getParameter("userId"));
			String userName = request.getParameter("userName");
			String userContact = request.getParameter("userContact");
			String userEmail = request.getParameter("userEmail");
			String userUserName = request.getParameter("userUserName");
			String userPassword = request.getParameter("userPassword");
			String userRole = request.getParameter("userRole");

			/*pst = con.prepareStatement("update table users_personal_details set user_per_name='" + userName
					+ "' user_per_contact='" + userContact + "' user_per_email='" + userEmail + "' user_per_username='"
					+ userUserName + "' user_per_password='" + userPassword + "'user_per_role='" + userRole
					+ "'  where user_per_id=" + id + "");*/
			
			pst = con.prepareStatement("UPDATE users_personal_details SET user_per_name=?, user_per_contact=?, user_per_email=?, user_per_username=?, user_per_password=?, user_per_role=? WHERE user_per_id=?");
	        pst.setString(1, userName);
	        pst.setString(2, userContact);
	        pst.setString(3, userEmail);
	        pst.setString(4, userUserName);
	        pst.setString(5, userPassword);
	        pst.setString(6, userRole);
	        pst.setInt(7, id);

	        pst.executeUpdate();

			dis = request.getRequestDispatcher("index.html");
			dis.include(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
