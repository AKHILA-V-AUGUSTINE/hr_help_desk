package hr_head;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/add_usr")
public class AddUser extends HttpServlet {

    private static final long serialVersionUID = 1L;
    final String driver = "com.mysql.cj.jdbc.Driver";
    final String url = "jdbc:mysql://localhost:3306/hrhelpdesk";
    final String user = "root";
    final String password = "1234";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
           

            String reg_name = request.getParameter("reg_name");
            String reg_phno = request.getParameter("reg_phno");
            
           
            String reg_email = request.getParameter("reg_email");
            String reg_user_name = request.getParameter("reg_user_name");
            String reg_user_pwd = request.getParameter("reg_user_pwd");
            String category = request.getParameter("category");

            PreparedStatement pst = con.prepareStatement("INSERT INTO users_personal_details (user_per_name, user_per_contact,user_per_email,user_per_username,user_per_password,user_per_role) VALUES (?, ?,?,?,?,?)");
            pst.setString(1, reg_name);
            pst.setString(2, reg_phno);
            pst.setString(3, reg_email);
            pst.setString(4, reg_user_name);
            pst.setString(5, reg_user_pwd);
            pst.setString(6, category);
           
            pst.executeUpdate();
            
            pst.close();
            con.close();
            
            response.sendRedirect("index.html"); // Redirect to index.html after insertion

        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle the error properly, e.g., display an error message to the user
            response.getWriter().println("Error occurred: " + ex.getMessage());
        }
    }

}