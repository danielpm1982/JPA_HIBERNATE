package pharmacistPatientApp.entity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="scheme1.patient")
public class Patient {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_id")
	private Long id;
	@Column(name="name")
	private String name;
	@Embedded
	@Column(name="address")
	private Address address;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="patient_contact_list", joinColumns=@JoinColumn(name="patient_id_FK"))
	@Column(name="contact")
	private List<String> contactList; //this will be the weak entity of the OneToMany identifying relationship between patient table and patient_contact_list table. Similar for pharmacist table and pharmacist_contact_list table at the Pharmacist class. There will not be an independent PK at this contact table. The PK of thec contact table will be the concatenation of the PK of the strong entity (FK here) and the partial key, which is the contact itself. The combination of both should be unique, not the contact alone. Which means that two Patients or two Pharmacists could have the same contact number, but there should be no duplicity when taken the Patient or Pharmacist Id together with the contact - for one same Patient or Pharmacist, there shouldn't be two tuples with the same contact number.
	@OneToOne(fetch=FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL, mappedBy="patient")
	private HealthRecord healthRecord;
	public Patient() {
	}
	public Patient(String name, Address address, List<String> contactList) {
		this.name = name;
		this.address = address;
		this.contactList = contactList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public List<String> getContactList() {
		return contactList;
	}
	public void setContactList(List<String> contactList) {
		this.contactList = contactList;
	}
	public HealthRecord getHealthRecord() {
		return healthRecord;
	}
	public void setHealthRecord(HealthRecord healthRecord) {
//		healthRecord.setPatient(this); //optional if mapped by is used. The mapping, at the other entity table will be used both for fetching data as when persisting the data. So, as the persistence of this entity is CascacadeType.PERSIST with the entity HealthRecord, every time a Patient is Persisted the correspondent HealthRecord is also persisted, and, in the case of the HealthRecord instance, with the relationship FK that serves both ways of the bidirectional relationship. If mapped by (both for persisting as for fetching data) wouldn't be used, there would be two independent FK at each table and every time one instance were to be set to a second, this "second" would have first to be set to the "one", and then the "one" finally be set to the "second". In that case, if this intermediate procedure is not done, there would clearly be inconsistences between the two independent relationships. But this is not the case of a bidirectional relationship that uses mapped by, as already mentioned.
		this.healthRecord = healthRecord;
	}
	@Override
	public String toString() {
		return "id: "+id+" name: "+name+" address: "+address+" contact: "+contactList.stream().reduce("", (x,y)->x+" "+y);
	}
}
