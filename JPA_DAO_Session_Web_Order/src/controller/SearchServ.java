package controller;
import java.io.IOException;
import java.util.List;
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

@WebServlet("/search.do")
public class SearchServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerIdString = request.getParameter("customerId").trim();
				String customerName = request.getParameter("customerName").trim().toLowerCase();
				Long customerId = null;
				Customer customer = null;
				List<Customer> customerList = null;
				if(customerIdString!=null&&!customerIdString.equals("")) {
					customerId = Long.valueOf(request.getParameter("customerId"));
					customer = DAO.findCustomerById(customerId, factory);
				} else if(customerName!=null&&!customerName.equals("")){
					customerList = DAO.findCustomerByName(customerName, factory);
				}
				if(customer!=null) {
					System.out.print("Customer found for the Id!");
					System.out.print(customer+" "+customer.getOrder());
					request.setAttribute("lastCustomer", customer);
					request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
				} else if(customerList!=null&&!customerList.isEmpty()){
					System.out.print("Customer found for the name!");
					customerList.forEach(x->System.out.println(x+" "+x.getOrder()));
					request.setAttribute("customerList", customerList);
					request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
				} else {
					System.out.print("No Customer found, either for the Id or Customer name!");
					request.getRequestDispatcher("/searchFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Id value. Id must be a valid long type number! Check the Id and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/searchFailed.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/searchFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Search failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
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
