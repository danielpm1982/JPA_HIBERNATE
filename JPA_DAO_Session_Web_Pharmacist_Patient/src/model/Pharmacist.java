package model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import model.Address;
import model.Consultation;
import model.SpecialtyType;

@Entity
@Table(name="pharmacist")
public class Pharmacist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pharmacist_id")
	private Long id;
	@Column(name="name")
	private String name;
	@Embedded
	@Column(name="address")
	private Address address;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="pharmacist_contact_list", joinColumns=@JoinColumn(name="pharmacist_id_FK"))
	@Column(name="contact")
	private List<String> contactList;
	@Enumerated(EnumType.STRING)
	@Column(name="specialty")
	private SpecialtyType specialty;
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
	@JoinTable(name="pharmacist_consultation", joinColumns=@JoinColumn(name="pharmacist_id_FK"), inverseJoinColumns=@JoinColumn(name="consultation_id_FK"))
	private List<Consultation> consultationList;
	public Pharmacist() {
	}
	public Pharmacist(String name, Address address, List<String> contactList, SpecialtyType specialty) {
		this.name = name;
		this.address = address;
		this.contactList = contactList;
		this.specialty = specialty;
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
	public SpecialtyType getSpecialty() {
		return specialty;
	}
	public void setSpecialty(SpecialtyType specialty) {
		this.specialty = specialty;
	}
	public List<Consultation> getConsultationList() {
		return consultationList;
	}
	public void setConsultationList(List<Consultation> consultationList) {
		this.consultationList = consultationList;
	}
	public void addConsultation(Consultation consultation) {
		if(consultationList==null) {
			consultationList = new ArrayList<>();
		}
//		if(!consultation.getPharmacistList().stream().filter(x->x.getId()==this.getId()).findFirst().isPresent()){ // Differently from other cardinality cases, there's no need to use this code (nor the respective one at the delete method below) for a ManyToMany relationship, which would actually lead into a loop and fatal stack overflow error. In a ManyToMany relationship, as the persistence Table is the same for both entities, any persistence made by adding one instance of an entity into anothers' relationship attribute's list, will already set the relation at the single table for both (will set the two FK that will be mapped for both entities when mounting or saving each entity objects). If a Consultation will be added to a relationship list attribute of a Pharmacist, there is no need to also add the instance of that Pharmacist to the pharmacistList of that Consultation being added. The entity objects will use exactly the same table for saving and loading, in an open session, and the two FK of the relationship, both, will be set by one way or another (either adding a Consultation to the list attribute of the Pharmacist OR adding a Pharmacist to the list attribute of the Consultation).  For deletion, the equivalent also applies, you just have to delete one side of the relation for the whole relationship be destroyed (both ways). Beside that, considering the entities themselves, there will be no orphan entity when a relationship is destroyed, 'cause there will be no relationship column (FK) at any of the entity tables (only at the third external table - and the respective tuples of this are deleted when one side of the relationship is destroyed). Furthermore, if CascadeType.REMOVE is NOT used, both entity table instances (rows) will be preserved, although with no relationship between them (no tuple at the third table). In this case, the Pharmacist must keep "alive", as well as the Consultation, even if no Pharmacist is part of it and vice-versa (some other clinical professional could be, whithin his legal competences). For other cardinality cases (OneToOne, OneToMany and ManyToOne, specially when mappedBy is not used), and if no joinTable is used (as expected), when adding an instance of an entity to the list (or setting attribute) of the other, the instance of this "other" should be first set or added to the respective attribute of the instance being added, and then the adding be concluded (specially considering the adding of an instance of the side from which the other side will be mapped by - and which will, alone, concretize the persistence at the table for both!)... CascadingType.PERSIST is critical for that. This can be viewed at the other cardinality cases of this app (OneToOne and OneToMany). See comments at the other entity classes for details.
//			consultation.addPharmacist(this);
//		}
		consultationList.add(consultation);
	}
	public void deleteConsultation(Long consultationId) {
		if(consultationList!=null&&!consultationList.isEmpty()) {
			Consultation consultation = consultationList.stream().filter(x->x.getId()==consultationId).findFirst().orElse(null);
			if(consultation!=null) {
//				if(consultation.getPharmacistList().stream().filter(x->x.getId()==this.getId()).findFirst().isPresent()) { 
//					consultation.deletePharmacist(this.id);
//				}
				consultationList.remove(consultation);
			}
		}
	}
	@Override
	public String toString() {
		return "id: "+id+" name: "+name+" address: "+address+" contactList: "+contactList+" specialty: "+specialty;
	}
}
