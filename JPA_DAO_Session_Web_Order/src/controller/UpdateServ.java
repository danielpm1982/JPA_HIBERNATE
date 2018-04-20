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

@WebServlet("/update.do")
public class UpdateServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerIdString = request.getParameter("updatingCustomerId");
				long customerId=-1;
				if(customerIdString!=null&&!customerIdString.equals("")) {
					customerId=Long.valueOf(customerIdString);
				}
				String customerName = request.getParameter("customerName");
				Address newAddress  = new Address(request.getParameter("street"), Integer.valueOf(request.getParameter("streetNumber")), request.getParameter("city"), request.getParameter("state"), request.getParameter("country"));
				Customer newMockCustomer = new Customer(customerName, newAddress);
				Customer originalCustomer = DAO.findCustomerById(customerId, factory);
				if(originalCustomer!=null) {
					DAO.updateCustomer(customerId, newMockCustomer, factory);
					System.out.print("Update successful!");
					request.setAttribute("oldCustomer", originalCustomer);
					request.setAttribute("newCustomer", DAO.findCustomerById(customerId, factory));
					request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
					request.getRequestDispatcher("/updateResult.jsp").forward(request, response);
				} else {
					System.out.print("Update failed! Customer could not be found!");
					JOptionPane.showMessageDialog(null, "Customer does not exist!");
					request.getRequestDispatcher("/updateFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Customer Id or streetNumber value. Id must be a valid long type number! StreetNumber must be an int valid type number. Check it over and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/updateFailed.jsp").forward(request, response);
			}
			catch (Exception e) {
				e.printStackTrace(System.out);
				JOptionPane.showMessageDialog(null, "Customer may exist, but update failed for other reasons!");
				request.getRequestDispatcher("/updateFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Update failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
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
