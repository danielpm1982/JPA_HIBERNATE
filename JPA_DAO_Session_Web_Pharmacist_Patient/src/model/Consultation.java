package model;
import java.time.LocalDateTime;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

@Entity
@Table(name="scheme1.consultation")
public class Consultation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="consultation_id")
	private Long id;
	@Column(name="dateTime")
	private LocalDateTime dateTime;
	@Transient
	private String dateTimeString; 
	@Lob
	@Column(name="description")
	private String description;
	@Column(name="health_record_Id_FK")
	private Long healthRecordId; //there is no ManyToOne relationship here. Only a simple attribute type to serve as the FK being mapped by the HealthRecord (which should be persisted at the N side table). There is only the OneToMany reltionship from HealthRecord to Consultation, but not the inverse. Otherwise, the attribute here should be a HealthRecord object, instead. There is no need to get the HealthRecord from a Consultation entity instance. Only the inverse.
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}) //EAGER fetch with ManyToMany or OneToMany relationships will bring multiple same instances of the object type contained at the list (Pharmacist in this case) due to the form hibernate will internally do the sql search. So, either a LAZY fetch is chosen (and adaptations are done at the DAO for that) or EAGER fetch can be used but the multiple entity instances returned should be filtered in the returning method. In this code, the filtering is done at the DAO.findConsultation() method.
	@JoinTable(name="pharmacist_consultation", joinColumns=@JoinColumn(name="consultation_id_FK"), inverseJoinColumns=@JoinColumn(name="pharmacist_id_FK"))
	private List<Pharmacist> pharmacistList;
	public Consultation() {
	}
	public Consultation(LocalDateTime dateTime, String description) {
		this.dateTime = dateTime;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getDateTimeString() {
		String date = dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
		String time = dateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
		return date+" "+time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getHealthRecordId() {
		return healthRecordId;
	}
	public void setHealthRecordId(Long healthRecordId) {
		this.healthRecordId = healthRecordId; //if it were the case of a ManyToOne relationship here, there would be still no need of adding this Consultation instance to the list at the healthRecord being set here, unless mappedBy hadn't been used (see the explanation at the HealthRecord class).
	}
	public List<Pharmacist> getPharmacistList() {
		return pharmacistList;
	}
	public void setPharmacistList(List<Pharmacist> pharmacistList) {
		this.pharmacistList = pharmacistList;
	}
	public void addPharmacist(Pharmacist pharmacist) {
		if(pharmacistList==null) {
			pharmacistList = new ArrayList<>();
		}
//		if(!pharmacist.getConsultationList().stream().filter(x->x.getId()==this.getId()).findFirst().isPresent()) { // Differently from other cardinality cases, there's no need to use this code (nor the respective one at the delete method below) for a ManyToMany relationship, which would actually lead into a loop and fatal stack overflow error. See the explanation at the Pharmacist class.
//			pharmacist.addConsultation(this);
//		}
		if(!pharmacistList.stream().filter(x->x.getId()==pharmacist.getId()).findFirst().isPresent()){ //only add if the pharmacist is not already added to that consult instance.
			pharmacistList.add(pharmacist);
		}
	}
	public void deletePharmacist(Long pharmacistId) {
		if(pharmacistList!=null&&!pharmacistList.isEmpty()) {
			Pharmacist pharmacist = pharmacistList.stream().filter(x->x.getId()==pharmacistId).findFirst().orElse(null);
			if(pharmacist!=null) {
//				if(pharmacist.getConsultationList().stream().filter(x->x.getId()==this.getId()).findFirst().isPresent()) {
//					pharmacist.deleteConsultation(this.id);
//				}
				pharmacistList.remove(pharmacist);
			}
		}
	}
	@Override
	public String toString() {
		return "id: "+id+" dateTime: "+getDateTimeString()+" description: "+description;
	}
}
