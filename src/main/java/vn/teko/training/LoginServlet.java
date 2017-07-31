package vn.teko.training;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("login.jsp").include(request, response);

		String email=request.getParameter("email");
		String password=request.getParameter("password");

		if (email.equals("admin@teko.vn") && password.equals("admin123")) {
            HttpSession session=request.getSession();
            session.setAttribute("email",email);
            response.sendRedirect("/welcome");
		} else {
            request.setAttribute("error","Email or password is invalid");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
