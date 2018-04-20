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
import model.Customer;
import model.DAO;

@WebServlet("/searchWithItem.do")
public class SearchWithItemServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerIdString = request.getParameter("customerId").trim();
				Long customerId = null;
				Customer customer = null;
				if(customerIdString!=null&&!customerIdString.equals("")) {
					customerId = Long.valueOf(request.getParameter("customerId"));
					customer = DAO.findCustomerByIdWithAllItems(customerId, factory); //already returns the fully loaded customer object (with items and totalPrice), which can, therefore, be accessed regardless of any connection to the DB, including at the view.
				}
				if(customer!=null) {
					request.setAttribute("lastCustomer", customer);
					request.getRequestDispatcher("/searchWithItemResult.jsp").forward(request, response);
				} else {
					System.out.print("No Customer found, either for the Id");
					request.getRequestDispatcher("/searchWithItemFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Id value. Id must be a valid long type number! Check the Id and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/searchWithItemFailed.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/searchWithItemFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Search With Item failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
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
