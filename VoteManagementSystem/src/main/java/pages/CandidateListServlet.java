package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import dao.CandidateDaoImpl;
import pojos.Candidate;

@WebServlet("/candidate_list")
public class CandidateListServlet extends HttpServlet {

	CandidateDaoImpl candidate;
	
	
	public void init() throws ServletException {
		
		try {
			candidate = new CandidateDaoImpl();
			
		} catch (SQLException e) {
			
			throw new ServletException("In "+getClass(), e);
		}
	}

	public void destroy() {
		
		try {
			candidate = new CandidateDaoImpl();
			candidate.cleanUp();
		} catch (SQLException e) {
			
			throw new RuntimeException("Error in "+getClass(), e);
		}
		

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		try(PrintWriter pw = response.getWriter() ) {
			
			candidate = new CandidateDaoImpl();
			
			List<Candidate> list = candidate.getAllCandidates();
			
			for(Candidate c : list)
			{
				pw.print("<h3>"+c+"</h3>");
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}

	}

}
