package model;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="scheme1.item")
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id=-1;
	@Column(name="name")
	private String name;
	@Column(name="label")
	private String label;
	@Column(name="description")
	private String description;
	@Column(name="price")
	private BigDecimal price;
	@Column(name="order_fk")
	private Long orderId;
	public Item() {
	}
	public Item(String name, String label, String description, BigDecimal price) {
		this.name = name;
		this.label = label;
		this.description = description;
		this.price = price;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "id: "+id+" name: "+name+" label: "+label+" description: "+description+" price: "+price;
	}
}
