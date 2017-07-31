package vn.teko.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GreetingServlet extends HttpServlet {

    /**
     * Welcome page
     * if user does not login yet, redirect to login page
     * if user logged in, show the welcome page
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");

        HttpSession session=request.getSession(false);

        if ((session != null) && (session.getAttribute("email") != null)) {
            String email=(String)session.getAttribute("email");
            request.setAttribute("email",email);
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
