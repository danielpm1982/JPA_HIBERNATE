package test;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.Address;
import model.Customer;
import model.DAO;
import model.Item;
import model.Order;

public class Main2 {
	private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Order.class).addAnnotatedClass(Customer.class).addAnnotatedClass(Item.class).buildSessionFactory();
	public static void main(String[] args) {
		try {
			DAO.truncateTable(factory);
			DAO.insertCustomer("customer1", new Address("Dom Manuel", 1001, "Fortaleza", "Ceara", "Brasil"), factory);
			DAO.addOrderToCustomer(1, new Order("order1"), factory);
			for (int i=1; i<901; i++) {
				DAO.addItemToOrder(1, "order1", new Item("item"+i, "label"+i, "no description", new BigDecimal(55.5)), factory);
			}
			System.out.println("\n***********************************************************");
			System.out.println("Getting only the customer with the orders... with no items:");
			Instant i1 = Instant.now();
			DAO.findCustomerById(1, factory);
			Instant i2 = Instant.now();
			long durationInNanos1 = Duration.between(i1, i2).get(ChronoUnit.NANOS);
			System.out.println("Took: "+durationInNanos1+" ns.");
			System.out.println("***********************************************************");
			
			System.out.println("\n***********************************************************");
			System.out.println("Getting the customer with the orders... and ALL items:");
			i1 = Instant.now();
			DAO.findCustomerByIdWithAllItems(1, factory);
			i2 = Instant.now();
			long durationInNanos2 = Duration.between(i1, i2).get(ChronoUnit.NANOS);
			System.out.println("Took: "+durationInNanos2+" ns.");
			System.out.println("***********************************************************");
			
			String durationRelation = durationInNanos2/durationInNanos1*100+"%";
			System.out.println("\nDuration 2 / Duration 1 = "+durationRelation+"\n");
			System.out.println("It would take "+durationRelation+" more time to get a customer and orders along with the items than only the customer and the orders, without the items. And that for this only case - 1 order with 900 items associated to it.");
			System.out.println("For apps that make use of a @OneToMany or @ManyToMany relationship it is critical to keep the default FetchType.LAZY way of getting base objects, whenever possible, without all dependencies being got at once. The dependencies should be get from individual base objects and on demand, as they're needed... specially when the N side tends to have a lot of registries.");
			
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
