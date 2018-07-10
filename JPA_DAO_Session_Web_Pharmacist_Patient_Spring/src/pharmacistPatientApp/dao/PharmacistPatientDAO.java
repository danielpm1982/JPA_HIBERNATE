package pharmacistPatientApp.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pharmacistPatientApp.entity.Consultation;
import pharmacistPatientApp.entity.HealthRecord;
import pharmacistPatientApp.entity.Patient;
import pharmacistPatientApp.entity.Pharmacist;

@Repository
public class PharmacistPatientDAO implements PharmacistPatientDAOInterface{
	
	@Autowired
	SessionFactory sessionFactory;
	public Long insertPatient(Patient mockPatient) {
		Session session = sessionFactory.getCurrentSession();
		Long patientId = (Long)session.save(mockPatient);
		return patientId;
	}
	public Patient findPatient(Long patientId) {
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, patientId);
		return patient;
	}
	public List<Consultation> findAllConsultationsPerPatient(Long patientId) {
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, patientId);
		List<Consultation> consultationList = new ArrayList<>();
		if(patient.getHealthRecord()!=null&&patient.getHealthRecord().getConsultationList()!=null&&!patient.getHealthRecord().getConsultationList().isEmpty()) {
			patient.getHealthRecord().getConsultationList().forEach(x->consultationList.add(x));
		}
		return consultationList;
	}
	public List<Consultation> findAllConsultationsPerPharmacist(Long pharmacistId) {
		Session session = sessionFactory.getCurrentSession();
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		List<Consultation> consultationList = new ArrayList<>();
		if(pharmacist!=null&&pharmacist.getConsultationList()!=null&&!pharmacist.getConsultationList().isEmpty()) {
			pharmacist.getConsultationList().forEach(x->consultationList.add(x));
		}
		return consultationList;
	}
	public List<Patient> findAllPatients() {
		Session session = sessionFactory.getCurrentSession();
		List<Patient> allPatientsList = session.createQuery("from Patient",Patient.class).getResultList();
		return allPatientsList;
	}
	public boolean deletePatient(Long patientId) {
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, patientId);
		session.delete(patient);
		return true;
	}
	public boolean updatePatient(Long oldPatientId, Patient mockNewPatient) {
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, oldPatientId);
		if(patient!=null) {
			patient.setName(mockNewPatient.getName());
			patient.setAddress(mockNewPatient.getAddress());
			patient.setContactList(mockNewPatient.getContactList());
			return true;
		} return false;
	}
	public Long insertHealthRecord(HealthRecord mockHealthRecord) {
		Session session = sessionFactory.getCurrentSession();
		Long healthRecordId = (Long)session.save(mockHealthRecord);
		return healthRecordId;
	}
	public HealthRecord findHealthRecord(Long healthRecordId) {
		Session session = sessionFactory.getCurrentSession();
		HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
		return healthRecord;
	}
	public Long insertConsultation(Consultation mockConsultation) {
		Session session = sessionFactory.getCurrentSession();
		session.getTransaction().begin();
		Long consultationId = (Long)session.save(mockConsultation);
		return consultationId;
	}
	public Consultation findConsultation(Long ConsultationId) {
		Session session = sessionFactory.getCurrentSession();
		Consultation consultation = session.get(Consultation.class, ConsultationId); //EAGER fetch with ManyToMany or OneToMany relationships will bring multiple same instances of the object type contained at the list (Pharmacist in this case) due to the form hibernate will internally do the sql search. So, either a LAZY fetch is chosen (and adaptations are done at the DAO for that) or EAGER fetch can be used but the multiple entity instances returned should be filtered in the returning method. In this code, the filtering is done at the DAO.findConsultation() method.
		consultation.setPharmacistList(consultation.getPharmacistList().stream().distinct().collect(Collectors.toList())); //a filtering must be done here for eliminating repeated Pharmacist instances returned when the eager join fetch is done at the Consultation get(), right above. An Hibernate issue, because of the internal sql join implementations when dealing with OneToMany or ManyToMany in the Eager mode type. If Lazy fetch type is used, though, as in the inverse way of this relationship (each Pharmacist getting its Consultation list), then no multiple and wrongly repeated Entity instances (Consultation instances) should be returned at the internal ResultSet, because the sql join then will internally have a different implementation. 
		return consultation;
	}
	public boolean addPharmacistToConsultation(Long pharmacistId, Long consultationId) {
		Session session = sessionFactory.getCurrentSession();
		Consultation consultation = session.get(Consultation.class, consultationId);
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		consultation.addPharmacist(pharmacist);
		return true;
	}
	public boolean deletePharmacistFromConsultation(Long pharmacistId, Long consultationId) { 
		Session session = sessionFactory.getCurrentSession();
		Consultation consultation = session.get(Consultation.class, consultationId);
		consultation.deletePharmacist(pharmacistId);
		return true;
	}
	public boolean addConsultationToHealthRecord(Long consultationId, Long healthRecordId) {
		Session session = sessionFactory.getCurrentSession();
		HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
		Consultation consultation = session.get(Consultation.class, consultationId);
		healthRecord.addConsultation(consultation);
		return true;
	}
	public boolean deleteConsultationFromHealthRecord(Long consultationId, Long healthRecordId) { //deleted from table because of the "orphanRemoval=true" condition. 
		Session session = sessionFactory.getCurrentSession();
		HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
		healthRecord.deleteConsultation(consultationId);
		return true;
	}
	public boolean setHealthRecordToPatient(Long healthRecordId, Long patientId) {
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, patientId);
		HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
		patient.setHealthRecord(healthRecord);
		return true;
	}
	public boolean deleteHealthRecordFromPatient(Long healthRecordId, Long patientId) { //deleted from table because of the "orphanRemoval=true" condition.
		Session session = sessionFactory.getCurrentSession();
		Patient patient = session.get(Patient.class, patientId);
		patient.setHealthRecord(null);
		return true;
	}
	public Long insertPharmacist(Pharmacist mockPharmacist) {
		Session session = sessionFactory.getCurrentSession();
		Long pharmacistId = (Long)session.save(mockPharmacist);
		return pharmacistId;
	}
	public Pharmacist findPharmacist(Long pharmacistId) {
		Session session = sessionFactory.getCurrentSession();
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		return pharmacist;
	}
	public List<Pharmacist> findAllPharmacists() {
		Session session = sessionFactory.getCurrentSession();
		List<Pharmacist> pharmacistList = session.createQuery("from Pharmacist",Pharmacist.class).getResultList();
		return pharmacistList;
	}
	public boolean deletePharmacist(Long pharmacistId) { 
		Session session = sessionFactory.getCurrentSession();
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		session.delete(pharmacist);
		return true;
	}
	public boolean addConsultationToPharmacist(Long consultationId, Long pharmacistId) {
		Session session = sessionFactory.getCurrentSession();
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		Consultation consultation = session.get(Consultation.class, consultationId);
		pharmacist.addConsultation(consultation);
		return true;
	}
	public boolean deleteConsultationFromPharmacist(Long consultationId, Long pharmacistId) { 
		Session session = sessionFactory.getCurrentSession();
		Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
		pharmacist.deleteConsultation(consultationId);
		return true;
	}
}
