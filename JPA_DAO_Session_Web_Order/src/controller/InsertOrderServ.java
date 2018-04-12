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

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import model.Customer;
import model.DAO;
import model.Order;

@WebServlet("/insertOrder.do")
public class InsertOrderServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerIdString = request.getParameter("insertingOrderCustomerId");
				Long customerId = -1L;
				if(customerIdString!=null&&!customerIdString.equals("")) {
					customerId=Long.valueOf(customerIdString);
				};
				String orderName = request.getParameter("orderName");
				Order order = new Order(orderName);
				Customer customer = DAO.findCustomerById(customerId, factory);
				if(customer!=null) {
					DAO.addOrderToCustomer(customerId, order, factory);
					customer = DAO.findCustomerById(customerId, factory);
					Order orderCheck = customer.getOrder().stream().filter(x->x.getName().equals(order.getName())).findFirst().orElse(null);
					if(orderCheck!=null) {
						request.setAttribute("lastCustomer", customer);
						request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
						request.getRequestDispatcher("/insertOrderResult.jsp").forward(request, response);
					} else {
						System.out.print("Order Insert failed! Custom found but insertion failed anyway!");
						JOptionPane.showMessageDialog(null, "Order Insertion failed!");
						request.getRequestDispatcher("/insertOrderFailed.jsp").forward(request, response);
					}
				} else {
					System.out.print("Order Insert failed! Customer not found!");
					JOptionPane.showMessageDialog(null, "Customer does not exist!");
					request.getRequestDispatcher("/insertOrderFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Id value. Id must be a valid long type number! Check the Id and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertOrderFailed.jsp").forward(request, response);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Customer may exist, but order insert failed for other reasons! Check also for Order name duplicity - Order name must be unique.");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertOrderFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "OrderInsert failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
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
