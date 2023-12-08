package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;
import pojos.User;

@WebServlet("/loginVoter")
public class LoginVoterServlet extends HttpServlet {

	UserDaoImpl userdao;

	public void init() throws ServletException {

		try {
			userdao = new UserDaoImpl();
		} catch (SQLException e) {

//			ServletException(String , root cause);
			throw new ServletException("Error in init of " + getClass(), e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		String email = request.getParameter("uname");
		String password = request.getParameter("password");

		try (PrintWriter pw = response.getWriter()) {

			userdao = new UserDaoImpl();

			User user = userdao.authenticateUser(email, password);

			if (user != null) {
				if (user.getRole().equals("admin")) {
					response.sendRedirect("admin_page");
				} else {
					if (user.isVotingStatus()) {
						response.sendRedirect("logout");
					} else {
						response.sendRedirect("candidate_list");
					}
				}
			} else {
				pw.print("<h3>Invalid Username or Password <a href='loginForm.html'>Retry</a></h3>");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void destroy() {

		try {
			userdao = new UserDaoImpl();
			userdao.cleanUp();
		} catch (SQLException e) {
			throw new RuntimeException("Error in "+getClass(), e);
		}

	}

}
