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

@WebServlet("/update.do")
public class updateServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		try {
			String oldName = request.getParameter("oldName");
			String newName = request.getParameter("newName");
			request.setAttribute("affectedStudentsList", DAO.searchStudents(oldName, factory));
			int affectedRowsNumber = DAO.updateStudentBulk(oldName, newName, factory);
			request.setAttribute("updatedStudentsList", DAO.searchStudents(newName, factory));
			request.setAttribute("allStudentList", DAO.searchAllStudents(factory));
			request.setAttribute("affectedRowsNumber", affectedRowsNumber);
			if(affectedRowsNumber>0) {
				System.out.print("Update successful ("+affectedRowsNumber+")!");
				RequestDispatcher rd = request.getRequestDispatcher("updateResult.jsp");
				rd.forward(request, response);
			} else {
				System.out.print("Update failed!");
				RequestDispatcher rd = request.getRequestDispatcher("updateFailed.jsp");
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
