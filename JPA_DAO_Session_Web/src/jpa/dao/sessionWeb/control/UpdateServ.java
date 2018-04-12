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
import jpa.dao.sessionWeb.model.DAO2;
import jpa.dao.sessionWeb.model.Student;

//DAO uses SessionFactory and DAO2 uses EntityManagerFactory. Both work pretty fine. The xml for both when used at JEE projects is the src/hibernate.cfg.xml. For using persistence.xml for DAO2 here, more complex structures like jars and war files would have to be used with this .xml file within. Instead, the hibernate.cfg.xml conf file of DAO (Session) also works for DAO2 (EntityManager), and can be more easily found. For Console apps, on the other hand, the API can find persistence.xml if put inside src/META-INF folder, and that's whats used for DAO. The API also finds the src/hibernate.cfg.xml as well, when using DAO2.

@WebServlet("/update.do")
public class UpdateServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		try {
			String oldName = request.getParameter("oldName");
			String newName = request.getParameter("newName");
//			request.setAttribute("affectedStudentsList", DAO.searchStudents(oldName, factory));
//			int affectedRowsNumber = DAO.updateStudentBulk(oldName, newName, factory);
//			request.setAttribute("updatedStudentsList", DAO.searchStudents(newName, factory));
//			request.setAttribute("allStudentList", DAO.searchAllStudents(factory));
			request.setAttribute("affectedStudentsList", DAO2.searchStudents(oldName, factory));
			int affectedRowsNumber = DAO2.updateStudentBulk(oldName, newName, factory);
			request.setAttribute("updatedStudentsList", DAO2.searchStudents(newName, factory));
			request.setAttribute("allStudentList", DAO2.searchAllStudents(factory));
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
