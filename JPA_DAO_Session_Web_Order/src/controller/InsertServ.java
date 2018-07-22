package controller;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import org.hibernate.SessionFactory;
import model.Address;
import model.Customer;
import model.DAO;

@WebServlet("/insert.do")
public class InsertServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerName = request.getParameter("customerName");
				Address address  = new Address(request.getParameter("street"), Integer.valueOf(request.getParameter("streetNumber")), request.getParameter("city"), request.getParameter("state"), request.getParameter("country"));
				long customerId = DAO.insertCustomer(customerName, address, factory);
				Customer customer = DAO.findCustomerById(customerId, factory);
				if(customerId>0) {
					System.out.print("Insertion successfully! Customer now in managed state: ");
					System.out.print(customer);
					request.setAttribute("lastCustomer", customer);
					request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
					request.getRequestDispatcher("/insertResult.jsp").forward(request, response);
				} else {
					System.out.print("Insertion failed!");
					request.getRequestDispatcher("/insertFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid streetNumber value. StreetNumber must be a valid int type number! Check it and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertFailed.jsp").forward(request, response);
			}
			catch (Exception e) {
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Insert failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
			RequestDispatcher rd  = request.getRequestDispatcher("initializer.do");
			rd.forward(request, response);
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
}
