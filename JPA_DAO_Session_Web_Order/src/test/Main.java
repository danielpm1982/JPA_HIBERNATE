package test;
import java.math.BigDecimal;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.Address;
import model.Customer;
import model.DAO;
import model.Item;
import model.Order;

public class Main {
	private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Order.class).addAnnotatedClass(Customer.class).addAnnotatedClass(Item.class).buildSessionFactory();
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
			DAO.addItemToOrder(1, "ordercustomer1No1", new Item("itemABC", "labelABC", "a labelABC itemABC product", new BigDecimal(1500.00)), factory);
			DAO.addItemToOrder(1, "ordercustomer1No1", new Item("itemDEF", "labelDEF", "a labelDEF itemDEF product", new BigDecimal(1300.50)), factory);
			DAO.addItemToOrder(1, "ordercustomer1No1", new Item("itemGHI", "labelGHI", "a labelGHI itemGHI product", new BigDecimal(1250.70)), factory);
			DAO.findCustomerByIdWithAllItems(1, factory);
			DAO.deleteItemFromOrder(1, "ordercustomer1No1", "itemGHI", factory);
			DAO.deleteItemFromOrder(1, "ordercustomer1No1", "itemDEF", factory);
			DAO.findCustomerByIdWithAllItems(1, factory);
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

/*
 This is basically a console and Web app that makes use of JPA, along with a SessionFactory (and Session), managed by a DAO and,
 in the case of the Web perspective, also by the Controller servlets and jsp views. The config file for the JPA is hibernate.cfg.xml
 (persistence.xml wouldn't work for the Web perspective, only for the console one). And, from the 4 main business model objects,
 Address data is embedded in the Customer table; Customer has a OneToMany/ManyToOne bidirectional relationship with Order (mapped by
 the FK at Order); and Order has a OneToMany unidirectional relationship with Item (mapped by the FK at Item). Both relationships
 also use Fetch, Cascade and OrphanRemoval annotations. The business logic (main scenario) is that a Customer would create
 an Order and add Items to each Order. The system would persist that at 3 tables (see /src/other/EER.png), would show that in a 
 friendly searchable view for the System user, and would also calculate the sum - totalPrice - of all items at each Order, also
 showing that to the client in the view. The purpose here is merely to show the implementation of different cardinality relationships,
 as well as fetch, cascade and orphanRemoval annotations. No great view or formulary usability techniques have been used. No inversion
 of control or dependency injection, no formulary bean data binding and no (customized or standard) validation annotations have been 
 used, which would have turned out the code, specially the Controller servlet code, much more cohesive, concise and simple. That can 
 be done using Spring MVC or EJB. Also, some more refactoring and reuse of the Servlets code and JSP pages could have been done,
 as well as declarative Security, Filters and Exception treatment, but that was not the goal here.
 */

//Backup data (as an example) is available at /src/other/dataBackup20180413.sql. Or the tables could be populated from scratch.

//SQL script for testing at the DBMS:
//SELECT * FROM scheme1.customer a left join (select * from scheme1.`order` b inner join scheme1.`item` c on b.order_id=c.order_fk) d on a.customer_id=d.customer_fk union
//SELECT * FROM scheme1.customer a right join (select * from scheme1.`order` b inner join scheme1.`item` c on b.order_id=c.order_fk) d on a.customer_id=d.customer_fk;

