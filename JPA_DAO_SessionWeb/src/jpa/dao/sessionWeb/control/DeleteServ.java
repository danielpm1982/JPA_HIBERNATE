package jpa.dao.sessionWeb.control;
import java.io.IOException;
import java.util.List;
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

@WebServlet("/delete.do")
public class DeleteServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		try {
			String name = request.getParameter("name");
			List<Student> deletedStudentList = DAO.searchStudents(name, factory);
			boolean result = DAO.deleteStudentBulk(name, factory);
			request.setAttribute("deletedStudentList", deletedStudentList);
			request.setAttribute("allStudentList", DAO.searchAllStudents(factory));
			if(result) {
				System.out.print("Deletion successful ("+deletedStudentList.size()+")!");
				System.out.println("Deleted: "+deletedStudentList);
				RequestDispatcher rd = request.getRequestDispatcher("deleteResult.jsp");
				rd.forward(request, response);
			} else {
				System.out.print("Insertion failed!");
				RequestDispatcher rd = request.getRequestDispatcher("deleteFailed.jsp");
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
