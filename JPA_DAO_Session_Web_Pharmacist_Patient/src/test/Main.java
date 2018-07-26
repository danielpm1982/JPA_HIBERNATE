package test;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.hibernate.SessionFactory;
import model.Address;
import model.Consultation;
import model.DAO;
import model.HealthRecord;
import model.Patient;
import model.Pharmacist;
import model.SpecialtyType;

public class Main {
	private static SessionFactory factory=null;
	public static void main(String[] args) {
		try {
			factory = DAO.initialize(factory);
			testPatientInsertAndDisplayWithAllRelashionships();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			DAO.terminate(factory);
		}
	}
	private static void testPatientInsertAndDisplayWithAllRelashionships() {
		Long patient1Id = DAO.insertPatient(new Patient("patient1", new Address("street1", 1001, "city1", "state1", "country1", "mail1@mail.com"), Arrays.asList("555-55-55","333-33-33")), factory);
		Patient patient1 = DAO.findPatient(patient1Id, factory);
		System.out.println(patient1);
		
		Long healthRecordPatient1Id = DAO.insertHealthRecord(new HealthRecord(patient1), factory);
		HealthRecord healthRecordPatient1 = DAO.findHealthRecord(healthRecordPatient1Id, factory);
		System.out.println(healthRecordPatient1);
		
		Long consultation1Id = DAO.insertConsultation(new Consultation(LocalDateTime.now(), "no description"), factory);
		Consultation consultation1 = DAO.findConsultation(consultation1Id, factory);
		System.out.println(consultation1);
		
		DAO.addConsultationToHealthRecord(consultation1Id, healthRecordPatient1Id, factory);
		DAO.setHealthRecordToPatient(healthRecordPatient1Id, patient1Id, factory);
		
		String patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);
		
		Long consultation2Id = DAO.insertConsultation(new Consultation(LocalDateTime.now(), "no description"), factory);
		Consultation consultation2 = DAO.findConsultation(consultation2Id, factory);
		System.out.println(consultation2);
		
		DAO.addConsultationToHealthRecord(consultation2Id, healthRecordPatient1Id, factory);
		
		patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);
		
		Long pharmacistId1 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm1", new Address("street1", 1001, "city1", "state1", "country1", "email1@mail.com"), Arrays.asList("555-5555","333-3333"), SpecialtyType.GeneralPractice), factory);
		Long pharmacistId2 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm2", new Address("street2", 1002, "city2", "state2", "country2", "email2@mail.com"), Arrays.asList("888-8888","444-4444"), SpecialtyType.Cardiology), factory);
		Long pharmacistId3 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm3", new Address("street3", 1003, "city3", "state3", "country3", "email3@mail.com"), Arrays.asList("111-1111","222-2222"), SpecialtyType.ClinicalPathology), factory);
		Long pharmacistId4 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm4", new Address("street4", 1004, "city4", "state4", "country4", "email4@mail.com"), Arrays.asList("777-7777","999-9999"), SpecialtyType.IntensiveCare), factory);
		Long pharmacistId5 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm5", new Address("street5", 1005, "city5", "state5", "country5", "email5@mail.com"), Arrays.asList("121-1212","131-1313"), SpecialtyType.Emergency), factory);
		Long pharmacistId6 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm6", new Address("street6", 1006, "city6", "state6", "country6", "email6@mail.com"), Arrays.asList("151-1515","212-2121"), SpecialtyType.Neonatology), factory);
		Long pharmacistId7 = DAO.insertPharmacist(new Pharmacist("Dr.Pharm7", new Address("street7", 1007, "city7", "state7", "country7", "email7@mail.com"), Arrays.asList("101-1010","511-5151"), SpecialtyType.Pediatrics), factory);

		DAO.deletePharmacist(pharmacistId7, factory);
		
		Arrays.asList(pharmacistId1, pharmacistId2, pharmacistId3, pharmacistId4, pharmacistId5, pharmacistId6, pharmacistId7).forEach(x->System.out.println(DAO.getStringifiedPharmacistWithRelationships(x, factory)));
		
		DAO.addPharmacistToConsultation(pharmacistId1, consultation1Id, factory); DAO.addPharmacistToConsultation(pharmacistId2, consultation1Id, factory); DAO.addPharmacistToConsultation(pharmacistId3, consultation1Id, factory);
		DAO.addConsultationToPharmacist(consultation2Id, pharmacistId4, factory); DAO.addConsultationToPharmacist(consultation2Id, pharmacistId5, factory); DAO.addConsultationToPharmacist(consultation2Id, pharmacistId6, factory); 

		System.out.println(DAO.getStringifiedPatientWithRelationships(patient1Id, factory));
		Arrays.asList(pharmacistId1, pharmacistId2, pharmacistId3, pharmacistId4, pharmacistId5, pharmacistId6, pharmacistId7).forEach(x->System.out.println(DAO.getStringifiedPharmacistWithRelationships(x, factory)));
		
		DAO.deleteConsultationFromPharmacist(consultation1Id, pharmacistId3, factory);
		DAO.deletePharmacistFromConsultation(pharmacistId6, consultation2Id, factory);
		
		System.out.println(DAO.getStringifiedPatientWithRelationships(patient1Id, factory));
		Arrays.asList(pharmacistId1, pharmacistId2, pharmacistId3, pharmacistId4, pharmacistId5, pharmacistId6, pharmacistId7).forEach(x->System.out.println(DAO.getStringifiedPharmacistWithRelationships(x, factory)));

		DAO.deleteConsultationFromHealthRecord(consultation2Id, healthRecordPatient1Id, factory);
		
		patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);

		DAO.deleteConsultationFromHealthRecord(consultation1Id, healthRecordPatient1Id, factory);
		
		patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);
		
		DAO.deleteHealthRecordFromPatient(healthRecordPatient1Id, patient1Id, factory);
		
		patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);
		
		DAO.deletePatient(patient1Id, factory);
		
		patient1StringifiedPatientWithRelationships = DAO.getStringifiedPatientWithRelationships(patient1Id, factory);
		System.out.println(patient1StringifiedPatientWithRelationships);
		
		Arrays.asList(pharmacistId1, pharmacistId2, pharmacistId3, pharmacistId4, pharmacistId5, pharmacistId6, pharmacistId7).forEach(x->System.out.println(DAO.getStringifiedPharmacistWithRelationships(x, factory)));
	}
}

//Script for checking at the DBMS:

//select * from patient p left join 
//
//(select * from health_record h left join consultation c on h.health_record_id=c.health_record_Id_FK union
//select * from health_record h right join consultation c on h.health_record_id=c.health_record_Id_FK) hc
//
//on p.patient_id=hc.patient_id_FK
//
//union
//
//select * from patient p right join 
//
//(select * from health_record h left join consultation c on h.health_record_id=c.health_record_Id_FK union
//select * from health_record h right join consultation c on h.health_record_id=c.health_record_Id_FK) hc
//
//on p.patient_id=hc.patient_id_FK

