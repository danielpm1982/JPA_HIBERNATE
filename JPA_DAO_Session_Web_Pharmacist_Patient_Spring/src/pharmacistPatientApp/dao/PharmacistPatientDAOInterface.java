package pharmacistPatientApp.dao;
import java.util.List;
import pharmacistPatientApp.entity.Consultation;
import pharmacistPatientApp.entity.HealthRecord;
import pharmacistPatientApp.entity.Patient;
import pharmacistPatientApp.entity.Pharmacist;

public interface PharmacistPatientDAOInterface {
	public Patient findPatient(Long patientId);
	public Long insertPatient(Patient patient);
	public List<Consultation> findAllConsultationsPerPatient(Long patientId);
	public List<Consultation> findAllConsultationsPerPharmacist(Long pharmacistId);
	public List<Patient> findAllPatients();
	public boolean deletePatient(Long patientId);
	public boolean updatePatient(Long oldPatientId, Patient mockNewPatient);
	public Long insertHealthRecord(HealthRecord mockHealthRecord);
	public HealthRecord findHealthRecord(Long healthRecordId);
	public Long insertConsultation(Consultation mockConsultation);
	public Consultation findConsultation(Long ConsultationId);
	public boolean addPharmacistToConsultation(Long pharmacistId, Long consultationId);
	public boolean deletePharmacistFromConsultation(Long pharmacistId, Long consultationId);
	public boolean addConsultationToHealthRecord(Long consultationId, Long healthRecordId);
	public boolean deleteConsultationFromHealthRecord(Long consultationId, Long healthRecordId);
	public boolean setHealthRecordToPatient(Long healthRecordId, Long patientId);
	public boolean deleteHealthRecordFromPatient(Long healthRecordId, Long patientId);
	public Long insertPharmacist(Pharmacist mockPharmacist);
	public Pharmacist findPharmacist(Long pharmacistId);
	public List<Pharmacist> findAllPharmacists();
	public boolean deletePharmacist(Long pharmacistId);
	public boolean addConsultationToPharmacist(Long consultationId, Long pharmacistId);
	public boolean deleteConsultationFromPharmacist(Long consultationId, Long pharmacistId);
}
