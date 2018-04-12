package model;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DAO {
	public static long insertCustomer(String customerName, Address customerAddress, SessionFactory factory) {
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		Customer customer = new Customer(customerName, customerAddress);
		System.out.println("Inserting "+customer);
		session.getTransaction().begin();
		long customerId = (long)session.save(customer);
		session.getTransaction().commit();
		checkResources(session, factory);
		return customerId;
	}
	public static void addOrderToCustomer(long customerId, Order order, SessionFactory factory) {
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		session.getTransaction().begin();
		checkResources(session, factory);
		Customer customer = session.get(Customer.class, customerId);
		if(customer!=null) {
			System.out.println("Inserting "+order+" to "+customer+"...");
			customer.addOrder(order);
			session.getTransaction().commit();
			System.out.println("Order sucessfully inserted!");
			System.out.println("Orders for "+customer+": "+customer.getOrder());
		} else {
			session.close();
			System.out.println("Customer not found for that Id!");
		}
		checkResources(session, factory);
	}
	public static void deleteOrderFromCustomer(long customerId, String orderName, SessionFactory factory) {
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		session.getTransaction().begin();
		checkResources(session, factory);
		Customer customer = session.get(Customer.class, customerId);
		if(customer!=null) {
			Order orderToDelete = customer.getOrder().stream().filter(x->x.getName().equals(orderName)).findFirst().orElse(null);
			if(orderToDelete!=null) {
				System.out.println("Deleting "+orderToDelete+" from "+customer+"...");
				customer.removeOrder(orderToDelete); //internally removes the order from the Customer order list and updates and sets Order customer to null, as the order (list) of the Customer class is mapped by the customer attribute of the Order class. At the program, both are updated. At the table, the last one is. The first is not persisted, but mapped by the other. So, the Customer order (list) will be inconsistent if the Order customer attribute is not updated. 
//				session.delete(orderToDelete); //optional, deletes the null-valued order at the table. Cascading delete does not apply here 'cause what has been deleted at the Customer class has been a list item (which is not cascaded), not a Customer instance. No need if orphanRemoval=true is used at the Customer entity @oneToMany relationship attribute ('order'), in which case the removal of each order, with null fk, from the table (after being removed from the Customer orderList) would be done automatically, which is preferred.
				session.getTransaction().commit();
				session = factory.getCurrentSession();
				session.getTransaction().begin();
				customer = session.get(Customer.class, customer.getId()); //just checking at the DB directly to see if the order really has been deleted.
				session.getTransaction().commit();
				orderToDelete = customer.getOrder().stream().filter(x->x.getName().equals(orderName)).findFirst().orElse(null); //trying to filter (find) the deleted order...
				System.out.println(orderToDelete==null?"Order sucessfully deleted!":"Order not deleted!");
				System.out.println("Orders for "+customer+": "+customer.getOrder());
			} else {
				System.out.println("Order not found for that name!");
			}
		} else {
			session.close();
			System.out.println("Customer not found for that Id!");
		}
		checkResources(session, factory);
	}
	public static Customer findCustomerById(long customerId, SessionFactory factory) {
		System.out.println("Getting new Session and activating Transaction...");
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		System.out.println("Retrieving entity at id="+customerId+"...");
		session.getTransaction().begin();
		Customer customer = session.get(Customer.class, customerId);
		session.getTransaction().commit();
		if(customer!=null) {
			System.out.println("Customer found!");
			System.out.println(customer);
			System.out.println("Orders:");
			System.out.println(!customer.getOrder().isEmpty()?customer.getOrder():"No order found for this customer!");
		} else {
			System.out.println("Customer not found for that Id!");
		}
		checkResources(session, factory);
		return customer;
	}
	public static List<Customer> findCustomerByName(String customerName, SessionFactory factory) {
		System.out.println("Getting new Session and activating Transaction...");
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		System.out.println("Retrieving Customer(s) with name="+customerName+"...");
		session.getTransaction().begin();
		List<Customer> customerList = session.createQuery("from Customer c where c.name like '%"+customerName+"%'", Customer.class).getResultList();
		session.getTransaction().commit();
		if(customerList!=null&&customerList.size()>0){
			customerList.forEach(x->System.out.println(x+" "+x.getOrder()));
		} else {
			System.out.println("No customer found that would match, partially or totally, that name!");
		}
		checkResources(session, factory);
		return customerList;
	}
	public static List<Customer> findAllCustomers(SessionFactory factory) {
		System.out.println("Getting new Session and activating Transaction...");
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		System.out.println("Retrieving all Customers...");
		session.getTransaction().begin();
		List<Customer> customerList = session.createQuery("from Customer", Customer.class).getResultList();
		session.getTransaction().commit();
		customerList.forEach(x->System.out.println(x+" "+x.getOrder()));
		checkResources(session, factory);
		return customerList;
	}
	public static boolean deleteCustomer(long customerId, SessionFactory factory) { //cascading deleting all Orders from this Customer
		System.out.println("Getting new Session and activating Transaction...");
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		System.out.println("Deleting Customer at id="+customerId+"...");
		session.getTransaction().begin();
		Customer customer = session.get(Customer.class, customerId);
		session.getTransaction().commit();
//		int result = JOptionPane.showConfirmDialog(null, "Delete: "+customer+" ?", "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//		if(result==JOptionPane.YES_OPTION) {
			session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
			session.getTransaction().begin();
			session.delete(customer);
			session.getTransaction().commit();
//		}
		session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		session.getTransaction().begin();
		customer = session.get(Customer.class, customerId);
		session.getTransaction().commit();
		System.out.println(customer==null?"Successfully deleted from the database!":"Not deleted from the database!");
		checkResources(session, factory);
		return (customer==null?true:false);
	}
	public static boolean updateCustomer(long customerId, Customer mockCustomer, SessionFactory factory) { //updates only name and address... id doesn't change and Orders must be added individually, if needed.
		System.out.println("Getting new Session and activating Transaction...");
		Session session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		System.out.println("Updating Customer at id="+customerId+" with "+mockCustomer+"...");
		session.getTransaction().begin();
		Customer customer = session.get(Customer.class, customerId);
		if(customer!=null) {
			customer.setName(mockCustomer.getName());
			customer.setAddress(mockCustomer.getAddress());
		}
		session.getTransaction().commit();
		// checking result:
		session = factory.getCurrentSession(); //must get a new session after each commit(), which closes the transaction and the session (differently from when using EntityManager and EntityManagerFactory, when only the transaction is closed).
		checkResources(session, factory);
		boolean result=false;
		session.getTransaction().begin();
		customer = session.get(Customer.class, customerId);
		session.getTransaction().commit();
		if(customer!=null) {
			if(customer.getName().equals(mockCustomer.getName())&&customer.getAddress().toString().equals(mockCustomer.getAddress().toString())) {
				result=true;
				System.out.println("Successfully updated at the database!");
			} else {
				System.out.println("Not updated at the database!");
			}
		} else {
			System.out.println("Not updated at the database!");
		}
		checkResources(session, factory);
		return result;
	}
	public static boolean truncateTable(SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			System.out.println("Truncating table 'scheme1.order':");
			session.createNativeQuery("truncate scheme1.order").executeUpdate();
			System.out.println("Successfully truncated!");
			session.getTransaction().commit();
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			System.out.println("Truncating table 'scheme1.customer':");
			session.createNativeQuery("truncate scheme1.customer").executeUpdate();
			System.out.println("Successfully truncated!");
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session!=null&&session.isOpen()) {
				session.close();
			}
		}
	}
	public static void checkResources(Session session, SessionFactory factory) {
//		System.out.println();
//		System.out.println("Checking Resources:");
//		System.out.println(session==null?"Session null!":"Session instantiated!");
//		System.out.println(session!=null&&session.isOpen()?"Session is open!":"Session closed!");
//		System.out.println(session==null?"SessionFactory null!":"SessionFactory instantiated!");
//		System.out.println(factory!=null&&factory.isOpen()?"SessionFactory is open!":"SessionFactory closed!");
//		System.out.println();
	}
}
