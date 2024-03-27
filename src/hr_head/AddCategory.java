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

@WebServlet("/addcategory")
public class AddCategory extends HttpServlet {

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

            String catg_name= request.getParameter("catg_name");
           

            PreparedStatement pst = con.prepareStatement("INSERT INTO ticket_category (cat_name) VALUES (?)");
            pst.setString(1, catg_name);
                       
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
