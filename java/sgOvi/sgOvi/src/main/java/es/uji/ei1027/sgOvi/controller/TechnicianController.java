package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.service.ListByName;
import es.uji.ei1027.sgOvi.service.PersonInstructorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/Technician")
public class TechnicianController {
    @Autowired
    private TechnicianDao technicianDao;
    @Autowired
    private OviUserDao oviUserDao;
    @Autowired
    private ListByName lbn;
    @Autowired
    private AssistanceReqDao assistanceReqDao;
    @Autowired
    private PapPatiDao papPatiDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private InstructorDao instructorDao;
    @Autowired
    private PersonDao personDao;


    @RequestMapping("/menuTechnician")
    public String menuTechnician(Model model) {
        return "Technician/menuTechnician";
    }

    @RequestMapping("/userList")
    public String listOviUsers(Model model) {
        model.addAttribute("users", lbn.personUserList());
        return "Technician/userList";
    }

    @RequestMapping("/papPatiList")
    public String listPapPati(Model model) {
        model.addAttribute("papPatis", lbn.personPapPatiList());
        return "Technician/papPatiList";
    }

    @RequestMapping("/instructorList")
    public String listInstructor(Model model) {
        model.addAttribute("instructors", lbn.personInstructorList());
        return "Technician/instructorList";
    }

    @RequestMapping("/assistanceRequestList")
    public String listAssistanceRequest(Model model) {
        model.addAttribute("requests", assistanceReqDao.getAssistanceRequests());
        return "Technician/assistanceRequestList";
    }

    @RequestMapping("/activityList")
    public String listActivity(Model model) {
        model.addAttribute("activities", activityDao.getActivities());
        return "Technician/activityList";
    }

    @RequestMapping(value="/userManagement/{dni}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable String dni) {
        OviUser user = oviUserDao.getOviUser(dni);

        model.addAttribute("dni", user.getDni());
        model.addAttribute("legalGuardian", user.getLegalGuardian());
        model.addAttribute("reason", user.getReason());
        model.addAttribute("address", user.getAddress());
        model.addAttribute("userPreferences", user.getUserPreferences());
        model.addAttribute("state", user.getState());
        model.addAttribute("birthdayDate", user.getBirthdayDate());
        return "Technician/userManagement";
    }

    @RequestMapping(value="/userManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitUser(@RequestParam String dni,
                                      @RequestParam String legalGuardian,
                                      @RequestParam (required = false) String reason,
                                      @RequestParam String address,
                                      @RequestParam String userPreferences,
                                      @RequestParam String state,
                                      @RequestParam LocalDate birthdayDate
    ) {

        OviUser user = new OviUser();
        user.setDni(dni);
        user.setState(state);
        user.setLegalGuardian(legalGuardian);
        user.setUserPreferences(userPreferences);
        user.setAddress(address);
        user.setBirthdayDate(birthdayDate);

        if(state.equals("PENDING") || state.equals("APPROVED")){
            user.setReason(null);
        }else if (reason == null ||reason.trim().isBlank()){
            user.setReason(null);
        }else{
            user.setReason(reason);
        }

        oviUserDao.updateOviUser(user);

        return "redirect:/Technician/userList";
    }

    @RequestMapping(value="/papPatiManagement/{dni}", method = RequestMethod.GET)
    public String editPapPati(Model model, @PathVariable String dni) {
        PapPati papPati = papPatiDao.getPapPati(dni);

        model.addAttribute("dni", papPati.getDni());
        model.addAttribute("address", papPati.getAddress());
        model.addAttribute("reason", papPati.getReason());
        model.addAttribute("type", papPati.getType());
        model.addAttribute("available", papPati.isAvailable());
        model.addAttribute("state", papPati.getState());
        model.addAttribute("document", papPati.getDocument());
        model.addAttribute("training", papPati.getTraining());
        model.addAttribute("papPatiPreferences", papPati.getPapPatiPreferences());
        return "Technician/papPatiManagement";
    }

    @RequestMapping(value="/papPatiManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitPapPati( @RequestParam String dni,
                                              @RequestParam String address,
                                              @RequestParam String type,
                                              @RequestParam (value = "available", defaultValue = "false") boolean available,
                                              @RequestParam String document,
                                              @RequestParam (required = false) String reason,
                                              @RequestParam String training,
                                              @RequestParam String state,
                                              @RequestParam String papPatiPreferences
    ) {

        PapPati papPati = new PapPati();
        papPati.setDni(dni);
        papPati.setTraining(training);
        papPati.setType(type);
        papPati.setAddress(address);
        papPati.setAvailable(available);
        papPati.setDocument(document);
        papPati.setPapPatiPreferences(papPatiPreferences);
        papPati.setState(state);

        if(state.equals("PENDING") || state.equals("APPROVED")){
            papPati.setReason(null);
        }else if (reason == null || reason.trim().isBlank()){
            papPati.setReason(null);
        }else{
            papPati.setReason(reason);
        }
        papPatiDao.updatePapPati(papPati);

        return "redirect:/Technician/papPatiList";
    }

    @RequestMapping(value="/instructorManagement/{dni}", method = RequestMethod.GET)
    public String editInstructor(Model model, @PathVariable String dni) {
        Instructor instructor = instructorDao.getInstructor(dni);

        model.addAttribute("dni", instructor.getDni());
        model.addAttribute("expertise", instructor.getExpertise());
        return "Technician/instructorManagement";
    }

    @RequestMapping(value="/instructorManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitInstructor(@RequestParam String dni,
                                                @RequestParam String expertise
    ) {

        Instructor instructor = new Instructor();
        instructor.setDni(dni);
        instructor.setExpertise(expertise);

        instructorDao.updateInstructor(instructor);

        return "redirect:/Technician/instructorList";
    }
    @RequestMapping(value="/addInstructor")
    public String addInstructor(Model model) {
        model.addAttribute("personInstructor", new PersonInstructorDTO());
        return "Technician/addInstructor";
    }
    @RequestMapping(value="/addInstructor", method=RequestMethod.POST)
    public String processAddInstructor(@ModelAttribute("personInstructor") PersonInstructorDTO pidto,
                                   BindingResult bindingResult) {
       Person person = new Person();
       person.setDni(pidto.getPerson().getDni());
       person.setName(pidto.getPerson().getName());
       person.setSurname(pidto.getPerson().getSurname());
       person.setPhoneNumber(pidto.getPerson().getPhoneNumber());
       person.setEmail(pidto.getPerson().getEmail());
       person.setGender(pidto.getPerson().getGender());
       person.setPassword(pidto.getPerson().getPassword());
       person.setCity(pidto.getPerson().getCity());
       person.setProvince(pidto.getPerson().getProvince());

       personDao.addPerson(person);

       Instructor instructor = new Instructor();
       instructor.setDni(pidto.getPerson().getDni());
       instructor.setExpertise(pidto.getInstructor().getExpertise());

       instructorDao.addInstructor(instructor);
        return "redirect:instructorList";
    }

    @RequestMapping(value="/apManagement/{idAsReq}", method = RequestMethod.GET)
    public String editAssistanceRequest(Model model, @PathVariable String idAsReq) {
        Assistance_Request apReq = assistanceReqDao.getAssistanceRequest(idAsReq);

        model.addAttribute("idAsReq", apReq.getIdAsReq());
        model.addAttribute("idOviUser", apReq.getIdOviUser());
        model.addAttribute("date", apReq.getData());
        model.addAttribute("description", apReq.getDescription());
        model.addAttribute("state", apReq.getState());
        model.addAttribute("reason", apReq.getReason());
        return "Technician/apManagement";
    }

    @RequestMapping(value="/apManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitAssistanceRequest( @RequestParam String idAsReq,
                                                        @RequestParam String idOviUser,
                                                        @RequestParam LocalDate date,
                                                        @RequestParam String description,
                                                        @RequestParam String state,
                                                        @RequestParam String reason
    ) {

        Assistance_Request assistanceRequest = new Assistance_Request();
        assistanceRequest.setIdOviUser(idOviUser);
        assistanceRequest.setIdAsReq(idAsReq);
        assistanceRequest.setReason(reason);
        assistanceRequest.setData(date);
        assistanceRequest.setState(state);
        assistanceRequest.setDescription(description);

        assistanceReqDao.updateAssistanceRequest(assistanceRequest);

        return "redirect:/Technician/assistanceRequestList";
    }
}