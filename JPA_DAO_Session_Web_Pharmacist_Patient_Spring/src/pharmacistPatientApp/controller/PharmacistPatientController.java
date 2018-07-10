package pharmacistPatientApp.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pharmacistPatientApp.entity.Address;
import pharmacistPatientApp.entity.Patient;
import pharmacistPatientApp.service.PersistenceServiceInterface;

@Controller
@RequestMapping("/pharmacistPatientController")
public class PharmacistPatientController {
	@Value("#{specialties}")
	private Map<String, String> specialties;
	@Autowired
	@Qualifier("persistenceService")
	private PersistenceServiceInterface persistenceServiceInterface;
	@RequestMapping("/home")
	public String showHomePage(Model model, HttpServletRequest request) {
		Patient patient = (Patient)request.getSession().getAttribute("patient");
		Address address = (Address)request.getSession().getAttribute("address");
		List<String> contactList = (List<String>)request.getSession().getAttribute("contactList");
		if(patient==null) {
			patient = new Patient();
		}
		if(address==null) {
			address = new Address();
		}
		if(contactList==null) {
			contactList = new ArrayList<>();
		}
		model.addAttribute("patient", patient);
		model.addAttribute("address", address);
		model.addAttribute("contactList", contactList);
		return "home";
	}
	@GetMapping("/newPatient")
	public String newPatient(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("patient", null);
		request.getSession().setAttribute("address", null);
		request.getSession().setAttribute("contactList", null);
		return "redirect:/pharmacistPatientController/home";
	}
	@GetMapping("/searchPatientResult")
	public String searchPatientResultPage(@RequestParam("patientId") Long patientId, Model model) {
		model.addAttribute("allPatientsList", persistenceServiceInterface.findAllPatients());
		Patient resultPatient = null;
		if(patientId!=null&&!String.valueOf(patientId).equals("")&&patientId>0) {
			resultPatient = persistenceServiceInterface.findPatient(patientId);
			if(resultPatient!=null) {
				model.addAttribute("lastPatient", resultPatient);
			}
		}
		return "searchResult";
	}
	@PostMapping("/insertPatientResult")
	public String insertPatientResultPage(@RequestParam("name") String name, Model model, HttpServletRequest request) {
		model.addAttribute("allPatientsList", persistenceServiceInterface.findAllPatients());
		Patient patient = new Patient(name, (Address)request.getSession().getAttribute("address"), (List<String>)request.getSession().getAttribute("contactList"));
		if(patient.getName()!=null) {
			request.getSession().setAttribute("patient", patient);
			if(patient.getAddress().getStreet()!=null) {
				Long resultPatientId = persistenceServiceInterface.insertPatient(patient);
				if(resultPatientId!=null) {
					Patient resultPatient = persistenceServiceInterface.findPatient(resultPatientId);
					model.addAttribute("lastPatient", resultPatient);
				}
				model.addAttribute("allPatientsList", persistenceServiceInterface.findAllPatients());
				return "insertResult";
			}
			return "redirect:/pharmacistPatientController/home";
		} else {
			return "redirect:/pharmacistPatientController/home"; 
		}
	}
	@GetMapping("/addressForm")
	public String showAddressFormPage(Model model, HttpServletRequest request) {
		Address address = (Address)request.getSession().getAttribute("address");
		if(address==null) {
			model.addAttribute("address", new Address());
		} else {
			model.addAttribute("address", address);
		}
		return "addressForm";
	}
	@PostMapping("/addressFormResult")
	public String showAddressFormResultPage(@ModelAttribute("address") Address address, HttpServletRequest request) {
		request.getSession().setAttribute("address", address);
		return "redirect:/pharmacistPatientController/home";
	}
	@GetMapping("/contactListForm")
	public String showContactListFormPage(Model model, HttpServletRequest request) {
		List<String> contactList = null;
		if(request.getSession().getAttribute("contactList")!=null) {
			contactList = (List<String>)request.getSession().getAttribute("contactList");
		} else {
			contactList = new ArrayList<>();
		}
		model.addAttribute("contactList", contactList);
		return "contactListForm";
	}
	@PostMapping("/contactListFormResult")
	public String showContactListFormResultPage(@RequestParam("phone1") String phone1, @RequestParam("phone2") String phone2, @RequestParam("phone3") String phone3, HttpServletRequest request) {
		List<String> contactList = new ArrayList<>(Arrays.asList(phone1, phone2, phone3));
		request.getSession().setAttribute("contactList", contactList);
		return "redirect:/pharmacistPatientController/home";
	}
	@GetMapping("/underConstruction")
	public String showUnderConstructionFormPage() {
		return "underConstruction";
	}
	@InitBinder
	public void initBinder(WebDataBinder wdb) {
		StringTrimmerEditor trimmer = new StringTrimmerEditor(true);
		wdb.registerCustomEditor(String.class, trimmer);
	}
}

/*
Similar to JPA_DAO_Session_Web_Pharmacist_Patient project, but added with Spring (Spring forms+dataBinding, 
Spring beans, Spring mappings, etc) and partially implemented, only with Query and Insert of Patients. All
other functionalities could be implemented with the same DAO and Persistence Service, only adding/updating the 
views and @Controller methods. No Spring Validation (default or customized) has been used here, only basic html 
field validation. No JSP error handling. No Filtering. No Security as well.
This project is only a partial example of how to implement the original JPA project (JPA_DAO_Session_Web_Pharmacist_
Patient) using Spring, instead of native servlets/jsp/jstl java technologies. The original project, on the other 
hand, has been fully implemented regarding JPA functionalities, either for console as for web application (without 
Spring).
*/
