package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.service.ListByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/Technician")
public class TechnicianController {

    private TechnicianDao technicianDao;
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
    public void setTechnicianDao(TechnicianDao technicianDao) {
        this.technicianDao=technicianDao;
    }
    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao){this.oviUserDao=oviUserDao; }
    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
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
}
