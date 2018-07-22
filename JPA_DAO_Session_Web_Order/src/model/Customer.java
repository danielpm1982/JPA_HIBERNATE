package model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="scheme1.customer")
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private long id=-1;
	@Column(name="customer_name")
	private String name;
	@Embedded
	private Address address;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="customer", orphanRemoval=true) //orphanRemoval deletes each order instance when no more customer attribute value (fk) exists on it, that is, no relationship to the Customer Entity anymore (when an order is removed from this list attribute, for example). If false, each order, after being removed at the removeOrder() below, would still need to be removed manually from the table, or would stay there with a null fk value.  
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL) //no need, if mappedBy is used.
//	@JoinTable(name="customer_order", joinColumns=@JoinColumn(name="customer_id"), inverseJoinColumns=@JoinColumn(name="order_id")) //no need, if mappedBy is used.
	private List<Order> order;
	public Customer() {
	}
	public Customer(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	public void addOrder(Order order) {
		if(this.order==null) {
			this.order = new ArrayList<>();
		}
		order.setCustomer(this); //must update both entity instances' attributes (the order list of the customer AND the customer of the order)! Specially because the mapping of the order list will be done by the order customer attribute. If the customer order list changes, but the order customer attribute is not updated, the order list turns out to be inconsistent.
		this.order.add(order);
	}
	public void removeOrder(Order order) {
		if(this.order!=null&&this.order.contains(order)) {
			order.setCustomer(null); //must update both entity instances' attributes (the order list of the customer AND the customer of the order)! Specially because the mapping of the order list will be done by the order customer attribute. If the customer order list changes, but the order customer attribute is not updated, the order list turns out to be inconsistent.
			this.order.remove(order);
		}
	}
	@Override
	public String toString() {
		return "id: "+id+" name: "+name+" address: "+address;
	}
}
