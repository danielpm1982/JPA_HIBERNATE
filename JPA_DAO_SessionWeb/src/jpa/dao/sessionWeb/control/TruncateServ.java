package jpa.dao.sessionWeb.control;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import jpa.dao.sessionWeb.model.DAO;
import jpa.dao.sessionWeb.model.Student;

@WebServlet("/truncate.do")
public class TruncateServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		try {
			String studentTableName = "scheme1.student"; 
			DAO.truncateDBTable(studentTableName, factory);
			request.setAttribute("allStudentList", DAO.searchAllStudents(factory));
			if(DAO.searchAllStudents(factory).size()==0) {
				System.out.print("Truncation successful!");
				RequestDispatcher rd = request.getRequestDispatcher("truncateResult.jsp");
				rd.forward(request, response);
			} else {
				System.out.print("Truncation failed!");
				RequestDispatcher rd = request.getRequestDispatcher("truncationFailed.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			factory.close();
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
}
