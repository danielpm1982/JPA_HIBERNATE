package model;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="scheme1.order") //full name, for avoiding errors if the default table is eventually set to another. With the full-qualified table name, this wouldn't be an issue.
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private long id=-1;
	@Column(name="order_name", unique=true)
	private String name;
	@Column(name="order_date")
	private ZonedDateTime dateTime;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="orderId", orphanRemoval=true)
//	@JoinColumn(name="orderId") //if mappedBy was not used, an implicit mappedBy would be done through @joinColumn which, in a @OneToMany relationship, would use the other class fk attribte (N side) instead of a fk attribute at the current class ('one' side). Or it could used a joinTable with join column and inverseJoinColumn as well (as in a @ManyToMany relationship is mandatory).
	private List<Item> itemList; //must have an open Session e active Transaction to access it (lazy fetch)
	@Transient
	private BigDecimal totalPrice; //must have an open Session e active Transaction to access it - depends on lazy fetch attribute (itemList)
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="customer_fk")
	private Customer customer;
	public Order() {
	}
	public Order(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Order(String name) {
		this.name=name;
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
	public ZonedDateTime getDateTime() {
		return dateTime;
	}
	public void setDate(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public BigDecimal getTotalPrice() {
		if(this.itemList!=null&&!this.itemList.isEmpty()) {
			return getItemList().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, (x,y)->x.add(y)).setScale(2, BigDecimal.ROUND_UP);
		} else {
			return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP);
		}
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void addItem(Item item) {
		if(itemList==null) {
			itemList = new ArrayList<>();
		}
		if(item!=null) {
			item.setOrderId(this.id);
			this.itemList.add(item);
		}
	}
	public void removeItem(Item item) {
		if(item!=null&this.itemList!=null&&this.itemList.contains(item)) {
			item.setOrderId(null);
			this.itemList.remove(item);
		}
	}
	private String getDateString() {
		return this.dateTime!=null?dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)):"";
	}
	@Override
	public String toString() {
		return "Order id: "+getId()+" name: "+getName()+" dateTime: "+getDateString(); //other attributes should be called at appropriate time, only (with a Session opened - lazy fetched!. Manually through DAO.
	}
}
