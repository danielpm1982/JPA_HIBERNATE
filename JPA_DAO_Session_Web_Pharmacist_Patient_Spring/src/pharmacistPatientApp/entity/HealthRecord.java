package pharmacistPatientApp.entity;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="scheme1.health_record")
public class HealthRecord {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="health_record_id")
	private Long id;
	@OneToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name="patient_id_FK")
	private Patient patient;
	@OneToMany(fetch=FetchType.LAZY, orphanRemoval=true, cascade=CascadeType.ALL, mappedBy="healthRecordId")
	private List<Consultation> consultationList;
	public HealthRecord() {
	}
	public HealthRecord(Patient patient) {
		this.patient = patient;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public List<Consultation> getConsultationList() {
		return consultationList;
	}
	public void setConsultationList(List<Consultation> consultationList) {
		this.consultationList = consultationList;
	}
	public void addConsultation(Consultation consultation) {
		if(this.consultationList==null) {
			consultationList = new ArrayList<>();
		}
		consultation.setHealthRecordId(this.id); //This set operation is required, even with "mapped by" being used. Differently from OneToOne relationships, in which there is only single set operations to define the relationship between both entities (and the setting of the other is done automatically, if mappedBy is used), and differently also from ManyToMany relationship, in which there is an external table for both entity's data persistence (and each table reuses the same FKs set by the other), in a OneToMany/ManyToOne bidirectional relationship it is needed to set the current instance to the instance of the other entity being added. That is because, even with CascadeType.PERSIST and mappedBybeing used, the setting of the N side entity won't be set automatically as it is at the OneToOne relationship (when there are no Lists on the way, differently from here). For each N side entity instance being added at this current entity list, first an instance of this entity must be set to the one being added and, only then, the adding is done at the list. If it is not done, either using the mappedBy or not, the other table won't persist the FK value that maps to this current instance. If unidirectional, the two independent relationships will be inconsistent. If bidirectional, no relationship at all will exist, as this side of the relationship (OneToMany) would be mappedBy the other side (ManyToOne), whose FK would be null, and the relationship, in both ways, would not exist. There is no reason, on the other hand, to add an instance of Consultation to the list of an instance of HealthRecord being set to the relationship attribute of the Consultation if mappedBy is used, 'cause all the relationship will be mapped by the other side of the relationship anyway, and the attribute at this side of the relationship is not even persisted anywhere. But the other side's attribute must be. If two unidirectional relationships would be used (no mappedBy), this intermediate setting (or adding) would have to be done at both sides, and one should care about not inducing infinite loops (a stop condition should be used to avoid one side keeping trying to set or add another and vice-versa forever). But, in this case, the other side should always be updated when one side is... avoiding inconsistences between the independent FKs of the two table. In short, the intermediate setting of this line (and the respective one at the delete method below) should only be applied in one case (considering the use of mappedBy): OneToMany. If mappedBy is not used, it should be done for all cardinality cases except for ManyToMany and for identifying relationships with weak entities (as in the case of Patient or Pharmacist x Contact). 
		consultationList.add(consultation);
	}
	public void deleteConsultation(Long consultationId) {
		if(this.consultationList!=null&&!this.consultationList.isEmpty()) {
			Consultation consultation = consultationList.stream().filter(x->x.getId()==consultationId).findFirst().orElse(null);
			consultation.setHealthRecordId(null);
			consultationList.remove(consultation);
		}
	}
	@Override
	public String toString() {
		return "id: "+id+" patient: "+patient;
	}
}
