package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;
import pojos.User;

@WebServlet("/register")
public class RegisterVoterServlet extends HttpServlet {

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

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String d = request.getParameter("dob");
		Date dob = Date.valueOf(d);

		User user = new User(fname, lname, email, password, dob);

		try (PrintWriter pw = response.getWriter()) {
			
			userdao = new UserDaoImpl();

			int i = userdao.registerNewVoter(user);

			if (i > 0) {
				request.getRequestDispatcher("registrationForm.html").include(request, response);
				pw.print("<h3>Voter Registered...!<h3>");
			} else {

				request.getRequestDispatcher("registrationForm.html").include(request, response);
				pw.print("<h3>Voter not Registered...!<h3>");

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
