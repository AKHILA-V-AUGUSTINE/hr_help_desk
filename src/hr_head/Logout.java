package hr_head;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Get the current session, or create a new one if it doesn't exist
		HttpSession session = request.getSession(false);
		if (session != null) {
			// Invalidate the session
			session.invalidate();
			out.println("<html><head><title>Logout Page</title></head><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.println("<h1>You are successfully logged out!</h1>");
			out.println("</div></body></html>");
		} else {
			out.println("<html><head><title>Error</title></head><body bgcolor='#F6DDD8'><div style='text-align: center;'>");
			out.println("<h1>Error: No active session found!</h1>");
			out.println("</div></body></html>");
		}
		out.close();
	}
}
