package vn.teko.training;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class LoginServlet extends HttpServlet {

    /**
     * Login page
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    /**
     * Login
     * Validate email and password from request
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getContextPath();

		request.getRequestDispatcher("login.jsp").include(request, response);

		String email=request.getParameter("email");
		String password=request.getParameter("password");

		if (isValidEmail(email) && password.equals("QC123456")) {
            HttpSession session=request.getSession();
            session.setAttribute("email",email);
            response.sendRedirect(contextPath + "/welcome");
		} else {
            request.setAttribute("error","Email hoặc mật khẩu không hợp lệ");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

    /**
     * Validate email
     * Validate email from @teko.vn only
     */
    private boolean isValidEmail(String email)
    {
        if (email != null)
        {
            Pattern p = Pattern.compile("[a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9]@teko\\.vn");
            Matcher m = p.matcher(email);
            return m.find();
        }
        return false;

    }
}
