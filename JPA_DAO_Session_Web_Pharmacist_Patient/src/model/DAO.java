package model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {
	public static SessionFactory initialize(SessionFactory factory) {
		try {
			if(factory==null||factory.isClosed()) {
				factory = new Configuration().addAnnotatedClass(Patient.class).addAnnotatedClass(Consultation.class).addAnnotatedClass(HealthRecord.class).addAnnotatedClass(Pharmacist.class).configure("hibernate.cfg.xml").buildSessionFactory();
//				truncateAllTables(factory);
				return factory;
			}
			return factory;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
		}
	}
	public static boolean terminate(SessionFactory factory) {
		try {
			if(factory!=null&&factory.isOpen()) {
				factory.close();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
		}
	}
	public static boolean isInitialized(SessionFactory factory) {
		return (factory!=null&&factory.isOpen());
	}
	public static Long insertPatient(Patient mockPatient, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Long patientId = (Long)session.save(mockPatient);
			session.getTransaction().commit();
			return patientId;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Patient findPatient(Long patientId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			session.getTransaction().commit();
			return patient;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static List<Consultation> findAllConsultationsPerPatient(Long patientId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			List<Consultation> consultationList = new ArrayList<>();
			if(patient.getHealthRecord()!=null&&patient.getHealthRecord().getConsultationList()!=null&&!patient.getHealthRecord().getConsultationList().isEmpty()) {
				patient.getHealthRecord().getConsultationList().forEach(x->consultationList.add(x));
				session.getTransaction().commit();
			}
			return consultationList;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static List<Consultation> findAllConsultationsPerPharmacist(Long pharmacistId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			List<Consultation> consultationList = new ArrayList<>();
			if(pharmacist!=null&&pharmacist.getConsultationList()!=null&&!pharmacist.getConsultationList().isEmpty()) {
				pharmacist.getConsultationList().forEach(x->consultationList.add(x));
				session.getTransaction().commit();
			}
			return consultationList;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static List<Patient> findAllPatients(SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			List<Patient> allPatientsList = session.createQuery("from Patient",Patient.class).getResultList();
			session.getTransaction().commit();
			return allPatientsList;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deletePatient(Long patientId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			session.delete(patient);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean updatePatient(Long oldPatientId, Patient mockNewPatient, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, oldPatientId);
			if(patient!=null) {
				patient.setName(mockNewPatient.getName());
				patient.setAddress(mockNewPatient.getAddress());
				patient.setContactList(mockNewPatient.getContactList());
				session.getTransaction().commit();
				return true;
			} else {
				session.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Long insertHealthRecord(HealthRecord mockHealthRecord, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Long healthRecordId = (Long)session.save(mockHealthRecord);
			session.getTransaction().commit();
			return healthRecordId;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static HealthRecord findHealthRecord(Long healthRecordId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
			session.getTransaction().commit();
			return healthRecord;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Long insertConsultation(Consultation mockConsultation, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Long consultationId = (Long)session.save(mockConsultation);
			session.getTransaction().commit();
			return consultationId;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Consultation findConsultation(Long ConsultationId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Consultation consultation = session.get(Consultation.class, ConsultationId); //EAGER fetch with ManyToMany or OneToMany relationships will bring multiple same instances of the object type contained at the list (Pharmacist in this case) due to the form hibernate will internally do the sql search. So, either a LAZY fetch is chosen (and adaptations are done at the DAO for that) or EAGER fetch can be used but the multiple entity instances returned should be filtered in the returning method. In this code, the filtering is done at the DAO.findConsultation() method.
			session.getTransaction().commit();
			consultation.setPharmacistList(consultation.getPharmacistList().stream().distinct().collect(Collectors.toList())); //a filtering must be done here for eliminating repeated Pharmacist instances returned when the eager join fetch is done at the Consultation get(), right above. An Hibernate issue, because of the internal sql join implementations when dealing with OneToMany or ManyToMany in the Eager mode type. If Lazy fetch type is used, though, as in the inverse way of this relationship (each Pharmacist getting its Consultation list), then no multiple and wrongly repeated Entity instances (Consultation instances) should be returned at the internal ResultSet, because the sql join then will internally have a different implementation. 
			return consultation;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean addPharmacistToConsultation(Long pharmacistId, Long consultationId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Consultation consultation = session.get(Consultation.class, consultationId);
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			consultation.addPharmacist(pharmacist);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deletePharmacistFromConsultation(Long pharmacistId, Long consultationId, SessionFactory factory) { 
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Consultation consultation = session.get(Consultation.class, consultationId);
			consultation.deletePharmacist(pharmacistId);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean addConsultationToHealthRecord(Long consultationId, Long healthRecordId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
			Consultation consultation = session.get(Consultation.class, consultationId);
			healthRecord.addConsultation(consultation);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deleteConsultationFromHealthRecord(Long consultationId, Long healthRecordId, SessionFactory factory) { //deleted from table because of the "orphanRemoval=true" condition. 
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
			healthRecord.deleteConsultation(consultationId);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean setHealthRecordToPatient(Long healthRecordId, Long patientId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			HealthRecord healthRecord = session.get(HealthRecord.class, healthRecordId);
			patient.setHealthRecord(healthRecord);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deleteHealthRecordFromPatient(Long healthRecordId, Long patientId, SessionFactory factory) { //deleted from table because of the "orphanRemoval=true" condition.
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			patient.setHealthRecord(null);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Long insertPharmacist(Pharmacist mockPharmacist, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Long pharmacistId = (Long)session.save(mockPharmacist);
			session.getTransaction().commit();
			return pharmacistId;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static Pharmacist findPharmacist(Long pharmacistId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			session.getTransaction().commit();
			return pharmacist;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static List<Pharmacist> findAllPharmacists(SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			List<Pharmacist> pharmacistList = session.createQuery("from Pharmacist",Pharmacist.class).getResultList();
			session.getTransaction().commit();
			return pharmacistList;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deletePharmacist(Long pharmacistId, SessionFactory factory) { 
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			session.delete(pharmacist);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean addConsultationToPharmacist(Long consultationId, Long pharmacistId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			Consultation consultation = session.get(Consultation.class, consultationId);
			pharmacist.addConsultation(consultation);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean deleteConsultationFromPharmacist(Long consultationId, Long pharmacistId, SessionFactory factory) { 
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			pharmacist.deleteConsultation(consultationId);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static boolean truncateAllTables(SessionFactory factory) {
		boolean b1 = truncateTable("scheme1.pharmacist_contact_list", factory);
		boolean b2 = truncateTable("scheme1.pharmacist", factory);
		boolean b3 = truncateTable("scheme1.pharmacist_consultation", factory);
		boolean b4 = truncateTable("scheme1.consultation", factory);
		boolean b5 = truncateTable("scheme1.health_record", factory);
		boolean b6 = truncateTable("scheme1.patient_contact_list", factory);
		boolean b7 = truncateTable("scheme1.patient", factory);
		return b1&&b2&&b3&&b4&&b5&&b6&&b7;
	}
	public static boolean truncateTable(String fullQualifiedTableName, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			System.out.println("Truncating table "+fullQualifiedTableName+":");
			session.createNativeQuery("truncate "+fullQualifiedTableName).executeUpdate();
			System.out.println("Successfully truncated!");
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		} finally {
			if(session!=null&&session.isOpen()) {
				session.close();
			}
		}
	}
	public static String getStringifiedPatientWithRelationships(Long patientId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Patient patient = session.get(Patient.class, patientId);
			String result = null;
			if(patient!=null) {
				String r1 = "Patient: "+patient;
				String r2 = "";
				String r3 = "";
				if(patient.getHealthRecord()!=null) {
					r2 = "\nHealthRecord: "+patient.getHealthRecord();
					if(patient.getHealthRecord().getConsultationList()!=null&&!patient.getHealthRecord().getConsultationList().isEmpty()) {
						r3="\nConsultations: ";
						for(Consultation c:patient.getHealthRecord().getConsultationList()) {
							r3+=c;
							if(c.getPharmacistList()!=null&&!c.getPharmacistList().isEmpty()) {
								r3+=" Pharmaceutical Doctors (PharmD): "+c.getPharmacistList();
							} else {
								r3+=" Pharmaceutical Doctors (PharmD): no Pharmacists for that consultation! ";
							}
						}
					} else {
						System.out.println("No Consultations for that HealthRecord!");
					}
				} else {
					System.out.println("No HealthRecord or Consultations for that Patient!");
				}
				result = r1+r2+r3;
			} else {
				result = "No Patient with that Id (and of course no HealthRecord or Consultations)!";
			}
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
	public static String getStringifiedPharmacistWithRelationships(Long pharmacistId, SessionFactory factory) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.getTransaction().begin();
			Pharmacist pharmacist = session.get(Pharmacist.class, pharmacistId);
			String result = null;
			if(pharmacist!=null) {
				String r1 = "Pharmacist: "+pharmacist;
				String r2 = "";
				if(pharmacist.getConsultationList()!=null&&!pharmacist.getConsultationList().isEmpty()) {
					r2+="\nConsultations: "+pharmacist.getConsultationList();
				} else {
					r2+="\nNo Consults for that Pharmacist Id!";
				}
				result = r1+r2;
			} else {
				result = "No Pharmacist with that Id !";
			}
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return null;
		} finally {
			if(session.isOpen()) {
				session.close();
			}
		}
	}
}
