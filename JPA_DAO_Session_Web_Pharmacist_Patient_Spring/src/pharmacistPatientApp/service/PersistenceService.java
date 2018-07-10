package pharmacistPatientApp.service;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacistPatientApp.dao.PharmacistPatientDAOInterface;
import pharmacistPatientApp.entity.Consultation;
import pharmacistPatientApp.entity.HealthRecord;
import pharmacistPatientApp.entity.Patient;
import pharmacistPatientApp.entity.Pharmacist;

@Service
public class PersistenceService implements PersistenceServiceInterface {
	@Autowired
	@Qualifier("pharmacistPatientDAO")
	private PharmacistPatientDAOInterface pharmacistPatientDAOInterface;
	@Transactional
	@Override
	public Patient findPatient(Long patientId) {
		return pharmacistPatientDAOInterface.findPatient(patientId);
	}
	@Transactional
	@Override
	public Long insertPatient(Patient patient) {
		return pharmacistPatientDAOInterface.insertPatient(patient);
	}
	@Transactional
	@Override
	public List<Consultation> findAllConsultationsPerPatient(Long patientId) {
		return pharmacistPatientDAOInterface.findAllConsultationsPerPatient(patientId);
	}
	@Transactional
	@Override
	public List<Consultation> findAllConsultationsPerPharmacist(Long pharmacistId) {
		return pharmacistPatientDAOInterface.findAllConsultationsPerPharmacist(pharmacistId);
	}
	@Transactional
	@Override
	public List<Patient> findAllPatients() {
		return pharmacistPatientDAOInterface.findAllPatients();
	}
	@Transactional
	@Override
	public boolean deletePatient(Long patientId) {
		return pharmacistPatientDAOInterface.deletePatient(patientId);
	}
	@Transactional
	@Override
	public boolean updatePatient(Long oldPatientId, Patient mockNewPatient) {
		return pharmacistPatientDAOInterface.updatePatient(oldPatientId, mockNewPatient);
	}
	@Transactional
	@Override
	public Long insertHealthRecord(HealthRecord mockHealthRecord) {
		return pharmacistPatientDAOInterface.insertHealthRecord(mockHealthRecord);
	}
	@Transactional
	@Override
	public HealthRecord findHealthRecord(Long healthRecordId) {
		return pharmacistPatientDAOInterface.findHealthRecord(healthRecordId);
	}
	@Transactional
	@Override
	public Long insertConsultation(Consultation mockConsultation) {
		return pharmacistPatientDAOInterface.insertConsultation(mockConsultation);
	}
	@Transactional
	@Override
	public Consultation findConsultation(Long ConsultationId) {
		return pharmacistPatientDAOInterface.findConsultation(ConsultationId);
	}
	@Transactional
	@Override
	public boolean addPharmacistToConsultation(Long pharmacistId, Long consultationId) {
		return pharmacistPatientDAOInterface.addPharmacistToConsultation(pharmacistId, consultationId);
	}
	@Transactional
	@Override
	public boolean deletePharmacistFromConsultation(Long pharmacistId, Long consultationId) {
		return pharmacistPatientDAOInterface.deletePharmacistFromConsultation(pharmacistId, consultationId);
	}
	@Transactional
	@Override
	public boolean addConsultationToHealthRecord(Long consultationId, Long healthRecordId) {
		return pharmacistPatientDAOInterface.addConsultationToHealthRecord(consultationId, healthRecordId);
	}
	@Transactional
	@Override
	public boolean deleteConsultationFromHealthRecord(Long consultationId, Long healthRecordId) {
		return pharmacistPatientDAOInterface.deleteConsultationFromHealthRecord(consultationId, healthRecordId);
	}
	@Transactional
	@Override
	public boolean setHealthRecordToPatient(Long healthRecordId, Long patientId) {
		return pharmacistPatientDAOInterface.setHealthRecordToPatient(healthRecordId, patientId);
	}
	@Transactional
	@Override
	public boolean deleteHealthRecordFromPatient(Long healthRecordId, Long patientId) {
		return pharmacistPatientDAOInterface.deleteHealthRecordFromPatient(healthRecordId, patientId);
	}
	@Transactional
	@Override
	public Long insertPharmacist(Pharmacist mockPharmacist) {
		return pharmacistPatientDAOInterface.insertPharmacist(mockPharmacist);
	}
	@Transactional
	@Override
	public Pharmacist findPharmacist(Long pharmacistId) {
		return pharmacistPatientDAOInterface.findPharmacist(pharmacistId);
	}
	@Transactional
	@Override
	public List<Pharmacist> findAllPharmacists() {
		return pharmacistPatientDAOInterface.findAllPharmacists();
	}
	@Transactional
	@Override
	public boolean deletePharmacist(Long pharmacistId) {
		return pharmacistPatientDAOInterface.deletePharmacist(pharmacistId);
	}
	@Transactional
	@Override
	public boolean addConsultationToPharmacist(Long consultationId, Long pharmacistId) {
		return pharmacistPatientDAOInterface.addConsultationToPharmacist(consultationId, pharmacistId);
	}
	@Transactional
	@Override
	public boolean deleteConsultationFromPharmacist(Long consultationId, Long pharmacistId) {
		return pharmacistPatientDAOInterface.deleteConsultationFromPharmacist(consultationId, pharmacistId);
	}
}
