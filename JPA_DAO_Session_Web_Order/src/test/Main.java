package test;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.Address;
import model.Customer;
import model.DAO;
import model.Order;

public class Main {
	private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Order.class).addAnnotatedClass(Customer.class).buildSessionFactory();
	public static void main(String[] args) {
		try {
			DAO.truncateTable(factory);
			for(int i=1;i<11;i++) {
				long customerId = DAO.insertCustomer("customer"+i, new Address("street"+i, 1000+i, "city"+i, "state"+i, "country"+i), factory);
				Order order1 = new Order("order"+"customer"+i+"No1");
				Order order2 = new Order("order"+"customer"+i+"No2");
				Order order3 = new Order("order"+"customer"+i+"No3");
				DAO.addOrderToCustomer(customerId, order1, factory);
				DAO.addOrderToCustomer(customerId, order2, factory);
				DAO.addOrderToCustomer(customerId, order3, factory);
				DAO.findCustomerById(customerId, factory);
			}
			DAO.addOrderToCustomer(1L, new Order("orderCustomer1No4"), factory);
			DAO.deleteOrderFromCustomer(1L, "orderCustomer1No4", factory);
			DAO.findAllCustomers(factory);
			for(int i=4;i<11;i++) {
				DAO.deleteCustomer(i, factory);
			}
			DAO.findCustomerByName("c%1", factory);
			DAO.updateCustomer(1L,new Customer("Client1000", new Address("Street1000", 2000, "city1000", "state1000", "country1000")), factory);
			DAO.findAllCustomers(factory);
			System.out.println("\nTest script successfully finished without any errors or exceptions!\n");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if(factory!=null&&factory.isOpen()) {
				factory.close();
			}
			DAO.checkResources(null, factory);
		}
	}
}

//SQL script for testing at the DBMS:
//create view select_full_outer_join as
//select * from scheme1.`customer` c left outer join scheme1.`order` o on c.customer_id=o.customer union
//select * from scheme1.`customer` c right outer join scheme1.`order` o on c.customer_id=o.customer;
//select * from scheme1.full_outer_join_view;
