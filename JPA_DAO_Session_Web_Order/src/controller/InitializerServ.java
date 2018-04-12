package controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.Customer;
import model.DAO;
import model.Order;
@WebServlet("/initializer.do")
public class InitializerServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Order.class).addAnnotatedClass(Customer.class).buildSessionFactory();
		request.getServletContext().setAttribute("factory", factory);
//		DAO.truncateTable(factory);
		JOptionPane.showMessageDialog(null, "Scheme1 tables created (or previously existent). SessionFactory created and ready to produce Sessions to the DAO. Waiting user requests.");
		request.getRequestDispatcher("/index.html").forward(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
}

/*
For not having to initialize a Session factory, using the hibernate config xml, at each request, it has been
initialized only once at this initializerServlet class and set to the servletContext, to be kept alive
until the application is alive or until the TerminatorServlet is called.
*/
