package controller;
import java.io.IOException;
import java.math.BigDecimal;
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
import model.Item;
import model.Order;

@WebServlet("/insertItem.do")
public class InsertItemServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = (SessionFactory)request.getServletContext().getAttribute("factory");
		if(factory!=null&&factory.isOpen()) {
			request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
			try {
				String customerIdString = request.getParameter("insertingItemCustomerId");
				Long customerId = -1L;
				if(customerIdString!=null&&!customerIdString.equals("")) {
					customerId=Long.valueOf(customerIdString);
				};
				String orderName = request.getParameter("insertingItemOrderName");
				String itemName = request.getParameter("insertingItemName");
				String itemLabel = request.getParameter("insertingItemLabel");
				String itemDescription = request.getParameter("insertingItemDescription");
				String itemPriceString = request.getParameter("insertingItemPrice");
				BigDecimal itemPrice = BigDecimal.ZERO;
				if(itemPriceString!=null&&!itemPriceString.equals("")) {
					itemPrice = new BigDecimal(itemPriceString);
				}
				Item item = new Item(itemName, itemLabel, itemDescription, itemPrice);
				Customer customer = DAO.findCustomerById(customerId, factory);
				Order order = customer.getOrder().stream().filter(x->x.getName().equals(orderName)).findFirst().orElse(null);
				if(customer!=null&&order!=null) {
					DAO.addItemToOrder(customerId, orderName, item, factory);
					customer = DAO.findCustomerById(customerId, factory);
					request.setAttribute("lastCustomer", customer);
					request.setAttribute("lastOrder", order);
					request.setAttribute("lastItem", item);
					request.setAttribute("allCustomersList", DAO.findAllCustomers(factory));
					request.getRequestDispatcher("/insertItemResult.jsp").forward(request, response);
				} else {
					System.out.print("Item Insert failed! Customer or Order not found!");
					JOptionPane.showMessageDialog(null, "Customer or Order does not exist!");
					request.getRequestDispatcher("/insertItemFailed.jsp").forward(request, response);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Id value. Id must be a valid long type number! Check the Id and try again!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertItemFailed.jsp").forward(request, response);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Customer may exist, but item insert failed for other reasons!");
				e.printStackTrace(System.out);
				request.getRequestDispatcher("/insertItemFailed.jsp").forward(request, response);
			} finally {
			}
		} else {
			JOptionPane.showMessageDialog(null, "ItemInsert failed! (SessionFactory is closed or does not exist at the servletContext!) Returning to '/' for initializing a new SessionFactory.");
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
