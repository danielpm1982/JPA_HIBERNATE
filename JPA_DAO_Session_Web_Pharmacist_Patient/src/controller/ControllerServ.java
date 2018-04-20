package controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import model.Address;
import model.Consultation;
import model.DAO;
import model.HealthRecord;
import model.Patient;
import model.Pharmacist;
import model.SpecialtyType;
@WebServlet(loadOnStartup=1, value="/controller.do")
public class ControllerServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final void doMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = null;
		try {
//			JOptionPane.showMessageDialog(null, "obtaining factory from servletContext...");
			factory = (SessionFactory)request.getServletContext().getAttribute("factory");
//			JOptionPane.showMessageDialog(null, "factory obtained and is open ? "+DAO.isInitialized(factory));
			if(DAO.isInitialized(factory)) {
				try {
					String command=request.getParameter("command");
					RequestDispatcher rd = null;
					switch(command) {
						case "initialize":
							if(!DAO.isInitialized(factory)) {
								DAO.initialize(factory);
								request.getServletContext().setAttribute("factory", factory);
							}
							rd = request.getRequestDispatcher("index.html");
							rd.forward(request, response);
							break;
						case "terminate":
							if(DAO.isInitialized(factory)) {
								DAO.terminate(factory);
							}
							rd = request.getRequestDispatcher("index.html");
							rd.forward(request, response);
							break;
						case "searchPatient":
							request.setAttribute("allPatientsList", DAO.findAllPatients(factory));
							String patientIdString = request.getParameter("patientId").trim();
							Patient resultPatient = null;
							if(patientIdString!=null&&!patientIdString.equals("")&&Long.valueOf(patientIdString)>0) {
								resultPatient = DAO.findPatient(Long.valueOf(patientIdString), factory);
								if(resultPatient!=null) {
									request.setAttribute("lastPatient", resultPatient);
								}
							}
							rd = request.getRequestDispatcher("searchResult.jsp");
							rd.forward(request, response);
							break;
						case "insertPatient":
							String tempName = request.getParameter("name").trim();
							Address tempAddress = new Address(request.getParameter("street"), Integer.valueOf(request.getParameter("streetNumber")), request.getParameter("city"), request.getParameter("state"), request.getParameter("country"), request.getParameter("email"));
							List<String> tempContactList = Arrays.asList(request.getParameter("phone1").trim(), request.getParameter("phone2").trim(), request.getParameter("phone3").trim());
							tempContactList = tempContactList.stream().filter(x->x!=null&&!x.equals("")).collect(Collectors.toList());
							Patient mockPatient = new Patient(tempName, tempAddress, tempContactList);
							Long patientId = DAO.insertPatient(mockPatient, factory);
							Patient patient = DAO.findPatient(patientId, factory);
							if(patient!=null) {
								request.setAttribute("lastPatient", patient);
								Long hrId = DAO.insertHealthRecord(new HealthRecord(patient), factory);
								if(hrId!=null&&hrId>0) {
									DAO.setHealthRecordToPatient(hrId, patientId, factory);
									request.setAttribute("hrId", hrId);
								}
							}
							rd = request.getRequestDispatcher("insertResult.jsp");
							rd.forward(request, response);
							break;
						case "updatePatient":
							request.setAttribute("allPatientsList", DAO.findAllPatients(factory));
							String updateIdString = request.getParameter("updateId");
							Long updateId = -1L;
							if(updateIdString!=null&&Long.valueOf(updateIdString)>0) {
								updateId = Long.valueOf(updateIdString);
								Patient oldPatient = DAO.findPatient(updateId, factory);
								if(oldPatient!=null) {
									request.setAttribute("oldPatient", oldPatient);
								}
							}
							tempName = request.getParameter("name").trim();
							tempAddress = new Address(request.getParameter("street"), Integer.valueOf(request.getParameter("streetNumber")), request.getParameter("city"), request.getParameter("state"), request.getParameter("country"), request.getParameter("email"));
							tempContactList = Arrays.asList(request.getParameter("phone1").trim(), request.getParameter("phone2").trim(), request.getParameter("phone3").trim());
							tempContactList = tempContactList.stream().filter(x->x!=null&&!x.equals("")).collect(Collectors.toList());
							Patient mockNewPatient = new Patient(tempName, tempAddress, tempContactList);
							if(updateId!=null&&updateId>0&&mockNewPatient!=null) {
								boolean updatedBoolean = DAO.updatePatient(updateId, mockNewPatient, factory);
								if(updatedBoolean) {
									Patient newPatient = DAO.findPatient(updateId, factory);
									request.setAttribute("newPatient", newPatient);
									request.setAttribute("updatedBoolean", updatedBoolean);
									request.setAttribute("allPatientsList", DAO.findAllPatients(factory));
								}
							}
							rd = request.getRequestDispatcher("updateResult.jsp");
							rd.forward(request, response);
							break;
						case "deletePatient":
							request.setAttribute("allPatientsList", DAO.findAllPatients(factory));
							String toDeleteIdString = request.getParameter("patientId").trim();
							if(toDeleteIdString!=null&&!toDeleteIdString.equals("")&&Long.valueOf(toDeleteIdString)>0) {
								Long toDeleteId = Long.valueOf(toDeleteIdString); 
								Patient deletedPatient = DAO.findPatient(toDeleteId, factory);
								request.setAttribute("deletedPatient", deletedPatient);
								boolean deletedBoolean = DAO.deletePatient(toDeleteId, factory);
								if(deletedBoolean) {
									request.setAttribute("deletedBoolean", deletedBoolean);
									request.setAttribute("allPatientsList", DAO.findAllPatients(factory));
								}
							}
							rd = request.getRequestDispatcher("deleteResult.jsp");
							rd.forward(request, response);
							break;
						case "hr":
							patientIdString = request.getParameter("patientId");
							if(patientIdString!=null&&!patientIdString.equals("")&&Long.valueOf(patientIdString)>0) {
								patientId = Long.valueOf(patientIdString); 
								Patient hrPatient = DAO.findPatient(patientId, factory);
								request.setAttribute("hrPatient", hrPatient);
								List<Consultation> consultationList = DAO.findAllConsultationsPerPatient(patientId, factory);
								if(consultationList!=null&&!consultationList.isEmpty()) {
									request.setAttribute("consultationList", consultationList);
								}
							}
							rd = request.getRequestDispatcher("hrResult.jsp");
							rd.forward(request, response);
							break;
						case "hr2":
							String hrIdString = request.getParameter("hrId");
							if(hrIdString!=null&&!hrIdString.equals("")&&Long.valueOf(hrIdString)>0) {
								Long hrId = Long.valueOf(hrIdString); 
								HealthRecord hr = DAO.findHealthRecord(hrId, factory);
								if(hr!=null) {
									request.setAttribute("hrPatient", hr.getPatient());
									List<Consultation> consultationList = DAO.findAllConsultationsPerPatient(hr.getPatient().getId(), factory);
									if(consultationList!=null&&!consultationList.isEmpty()) {
										request.setAttribute("consultationList", consultationList);
									}
								}
							}
							rd = request.getRequestDispatcher("hrResult.jsp");
							rd.forward(request, response);
							break;
						case "searchPharmacist":
							request.setAttribute("allPharmacistsList", DAO.findAllPharmacists(factory));
							String pharmacistIdString = request.getParameter("pharmacistId");
							if(pharmacistIdString!=null&&!pharmacistIdString.equals("")&&Long.valueOf(pharmacistIdString)>0) {
								Long pharmacistId = Long.valueOf(pharmacistIdString); 
								Pharmacist pharmacist = DAO.findPharmacist(pharmacistId, factory);
								request.setAttribute("pharmacist", pharmacist);
								List<Consultation> consultationList = DAO.findAllConsultationsPerPharmacist(pharmacistId, factory);
								if(consultationList!=null&&!consultationList.isEmpty()) {
									request.setAttribute("consultationList", consultationList);
								}
							}
							rd = request.getRequestDispatcher("pharmacist.jsp");
							rd.forward(request, response);
							break;
						case "insertPharmacist":
							request.setAttribute("allPharmacistsList", DAO.findAllPharmacists(factory));
							tempName = request.getParameter("name").trim();
							tempAddress = new Address(request.getParameter("street"), Integer.valueOf(request.getParameter("streetNumber")), request.getParameter("city"), request.getParameter("state"), request.getParameter("country"), request.getParameter("email"));
							tempContactList = Arrays.asList(request.getParameter("phone1").trim(), request.getParameter("phone2").trim(), request.getParameter("phone3").trim());
							tempContactList = tempContactList.stream().filter(x->x!=null&&!x.equals("")).collect(Collectors.toList());
							String specialtyString = request.getParameter("specialty").trim();
							SpecialtyType specialty = Arrays.asList(SpecialtyType.values()).stream().filter(x->x.toString().equals(specialtyString)).findFirst().orElse(null);
							if(specialty!=null) {
								Pharmacist mockPharmacist = new Pharmacist(tempName, tempAddress, tempContactList, specialty);
								Long pharmacistId = DAO.insertPharmacist(mockPharmacist, factory);
								if(pharmacistId!=null&&pharmacistId>0) {
									request.setAttribute("allPharmacistsList", DAO.findAllPharmacists(factory));
									Pharmacist pharmacist = DAO.findPharmacist(pharmacistId, factory);
									request.setAttribute("pharmacist", pharmacist);
								}
							}
							rd = request.getRequestDispatcher("pharmacist.jsp");
							rd.forward(request, response);
							break;
						case "addConsultation":
							patientIdString = request.getParameter("patientId");
							String description = request.getParameter("description");
							pharmacistIdString = request.getParameter("pharmacistId");
							if(patientIdString!=null&&Long.valueOf(patientIdString)>0&&pharmacistIdString!=null&&!pharmacistIdString.equals("")&&Long.valueOf(pharmacistIdString)>0&&description!=null&&!description.trim().equals("")) {
								patientId = Long.valueOf(patientIdString);
								Long pharmacistId = Long.valueOf(pharmacistIdString); 
								patient = DAO.findPatient(patientId, factory);
								if(patient!=null&&patient.getHealthRecord()!=null) {
									Long consultationId = DAO.insertConsultation(new Consultation(LocalDateTime.now(), description), factory);
									DAO.addPharmacistToConsultation(pharmacistId, consultationId, factory);
									DAO.addConsultationToHealthRecord(consultationId, patient.getHealthRecord().getId(), factory);
									Consultation consultation = DAO.findConsultation(consultationId, factory);
									if(consultation!=null) {
										request.setAttribute("consultation", consultation);
										request.setAttribute("patient", patient);
									}
								}
							}
							rd = request.getRequestDispatcher("consultationResult.jsp");
							rd.forward(request, response);
							break;
					}
				} catch (Exception e) {
					e.printStackTrace(System.out);
//					JOptionPane.showMessageDialog(null, "closing factory...");
					DAO.terminate(factory);
//					JOptionPane.showMessageDialog(null, "factory closed ? "+!DAO.isInitialized(factory));
				} finally {
				}
			} else {
//				JOptionPane.showMessageDialog(null, "initializing factory..."+factory);
				factory = DAO.initialize(factory);
//				JOptionPane.showMessageDialog(null, "factory initialized ? "+DAO.isInitialized(factory));
				if(DAO.isInitialized(factory)) {
//					JOptionPane.showMessageDialog(null, "saving factory at servletContext...");
					request.getServletContext().setAttribute("factory", factory);
//					JOptionPane.showMessageDialog(null, "saved ? "+DAO.isInitialized((SessionFactory)request.getServletContext().getAttribute("factory")));
					doMethod(request, response);
				}
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
//			JOptionPane.showMessageDialog(null, "closing factory, if open...");
			DAO.terminate(factory);
//			JOptionPane.showMessageDialog(null, "factory closed or null ? "+!DAO.isInitialized(factory));
		} finally {
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMethod(request, response);
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		config.getServletContext().setAttribute("specialtyType", SpecialtyType.values());
	}
}
