package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Technician")
public class TechnicianController {

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
    @Autowired
    private CodeGenerator cg;
    @Autowired
    private ListPapPatiSelService listPapPatiSelService;
    @Autowired
    private SelectionDao selectionDao;

    private OviUserValidator ouv = new OviUserValidator();
    private PapPatiValidator papPatiValidator = new PapPatiValidator();
    private InstructorValidator instructorValidator = new InstructorValidator();
    private PersonDtoValidator personDtoValidator = new PersonDtoValidator();
    private AssistanceRequestValidator assistanceRequestValidator = new AssistanceRequestValidator();
    private ActivityValidator activityValidator = new ActivityValidator();
    private RequestPersonUserDTO requestPersonUserDTO = new RequestPersonUserDTO();

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

        model.addAttribute("user", user);
        return "Technician/userManagement";
    }

    @RequestMapping(value="/userManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitUser(@ModelAttribute ("user") OviUser user,
                                          BindingResult bindingResult
    ) {
        if(personDao.getPerson(user.getDni()).getBirthdayDate().isAfter(LocalDate.now().minusYears(18)) && (user.getLegalGuardian() == null || user.getLegalGuardian().isBlank())){
            bindingResult.rejectValue("legalGuardian", "required", "Los menores de 18 años deben tener un tutor legal");
        }
        ouv.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            return "Technician/userManagement";
        }
        if (user.getReason().isBlank())
            user.setReason(null);
        if (user.getLegalGuardian().isBlank())
            user.setLegalGuardian(null);

        oviUserDao.updateOviUser(user);

        return "redirect:/Technician/userList";
    }

    @RequestMapping(value="/papPatiManagement/{dni}", method = RequestMethod.GET)
    public String editPapPati(Model model, @PathVariable String dni) {
        PapPati papPati = papPatiDao.getPapPati(dni);

        model.addAttribute("papPati", papPati);
        return "Technician/papPatiManagement";
    }

    @RequestMapping(value="/papPatiManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitPapPati( @ModelAttribute ("papPati") PapPati papPati,
                                              BindingResult bindingResult
    ) {

        papPatiValidator.validate(papPati,bindingResult);
        if(bindingResult.hasErrors()){
            return "Technician/papPatiManagement";
        }
        if(papPati.getReason().isBlank())
            papPati.setReason(null);
        papPatiDao.updatePapPati(papPati);

        return "redirect:/Technician/papPatiList";
    }

    @RequestMapping(value="/instructorManagement/{dni}", method = RequestMethod.GET)
    public String editInstructor(Model model, @PathVariable String dni) {
        Instructor instructor = instructorDao.getInstructor(dni);

        model.addAttribute("instructor", instructor);
        return "Technician/instructorManagement";
    }

    @RequestMapping(value="/instructorManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitInstructor(@ModelAttribute ("instructor") Instructor instructor,
                                                BindingResult bindingResult
    ) {
        instructorValidator.validate(instructor, bindingResult);
        if(bindingResult.hasErrors()){
            return "Technician/instructorManagement";
        }
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

        personDtoValidator.validate(pidto, bindingResult);
        if(personDao.getPerson(pidto.getPerson().getDni()) != null){
            bindingResult.rejectValue("person.dni", "duplicated", "Ya hay una persona con ese dni en la base de datos");
        }
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors().toString());
            return "Technician/addInstructor";
        }

        Person person = pidto.getPerson();
        personDao.addPerson(person);
        Instructor instructor = pidto.getInstructor();
        instructor.setDni(person.getDni());
        instructorDao.addInstructor(instructor);

        return "redirect:instructorList";
    }

    @RequestMapping(value="/apManagement/{idAsReq}", method = RequestMethod.GET)
    public String editAssistanceRequest(Model model, @PathVariable String idAsReq) {
        Assistance_Request apReq = assistanceReqDao.getAssistanceRequest(idAsReq);

        model.addAttribute("assistanceRequest", apReq);
        return "Technician/apManagement";
    }

    @RequestMapping(value="/apManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitAssistanceRequest( @ModelAttribute ("assistanceRequest") Assistance_Request assistanceRequest,
                                                        BindingResult bindingResult
    ) {
        if(assistanceRequest.getReason().isBlank())
            assistanceRequest.setReason(null);
        assistanceReqDao.updateAssistanceRequest(assistanceRequest);

        return "redirect:/Technician/assistanceRequestList";
    }
    @PostMapping("/apManagement/accept")
    public String acceptRequest(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                BindingResult bindingResult) {

        ArrayList<PapPati> listaCoincidencias = listPapPatiSelService.listCompatiblePapPati(request.getIdAsReq());
        request.setState(State.APPROVED.name());
        assistanceRequestValidator.validate(request, bindingResult);
        if(bindingResult.hasErrors()){
            request.setState(State.PENDING.name());
            request.setReason(null);
            return "Technician/apManagement";
        }
        if(request.getReason().isBlank())
            request.setReason(null);
        assistanceReqDao.updateAssistanceRequest(request);
        return "redirect:/Technician/selectPapPati/" + request.getIdAsReq();
    }
    @RequestMapping(value="/selectPapPati/{idAsReq}", method = RequestMethod.GET)
    public String selectPapPati(Model model, @PathVariable String idAsReq, HttpSession session) {

        List<String> seleccionados = (List<String>) session.getAttribute("seleccionados");
        if (seleccionados == null) {
            seleccionados = new ArrayList<>();
            session.setAttribute("seleccionados", seleccionados);
        }

        List<PapPati> listaFinalSeleccionados = new ArrayList<>();
        for (String dni : seleccionados) {
            listaFinalSeleccionados.add(papPatiDao.getPapPati(dni));
        }

        requestPersonUserDTO.setAssistanceRequest(assistanceReqDao.getAssistanceRequest(idAsReq));
        requestPersonUserDTO.setOviUser(oviUserDao.getOviUser(assistanceReqDao.getAssistanceRequest(idAsReq).getIdOviUser()));
        requestPersonUserDTO.setPerson(personDao.getPerson(assistanceReqDao.getAssistanceRequest(idAsReq).getIdOviUser()));


        model.addAttribute("request", requestPersonUserDTO);
        model.addAttribute("candidates", listPapPatiSelService.listCompatiblePapPati(idAsReq));
        model.addAttribute("allPapPatis", papPatiDao.getPapPatis());
        model.addAttribute("seleccionados", listaFinalSeleccionados); // Los que ya hemos elegido

        return "Technician/selectPapPati";
    }

    @RequestMapping("/selectPapPati/add/{idAsReq}/{dni}")
    public String addCandidate(@PathVariable String idAsReq, @PathVariable String dni, HttpSession session) {
        List<String> seleccionados = (List<String>) session.getAttribute("seleccionados");
        if (seleccionados != null && !seleccionados.contains(dni)) {
            seleccionados.add(dni);
        }
        return "redirect:/Technician/selectPapPati/" + idAsReq;
    }

    @RequestMapping("/selectPapPati/remove/{idAsReq}/{dni}")
    public String removeCandidate(@PathVariable String idAsReq, @PathVariable String dni, HttpSession session) {
        List<String> seleccionados = (List<String>) session.getAttribute("seleccionados");
        if (seleccionados != null) {
            seleccionados.remove(dni);
        }
        return "redirect:/Technician/selectPapPati/" + idAsReq;
    }

    @PostMapping("/processSelection")
    public String processSelection(@ModelAttribute("candidates") ArrayList<PapPati> seleccion,
                                   @ModelAttribute("request") RequestPersonUserDTO request){
        //Validator ------~~~~~~    El validator tiene que hacer aquí algo de heavy lifting pues
        //                          es quien verifica que no se deje la lista de selecciones vacia, si se deja explota D:


        for(PapPati papPati : seleccion){
            Selection selection = new Selection();
            selection.setIdSelection(cg.generateCode("SEL"));
            selection.setDate(LocalDate.now());
            selection.setState(State.PENDING.name());
            selection.setIdPapPati(papPati.getDni());
            selection.setIdAsReq(request.getAssistanceRequest().getIdAsReq());

            selectionDao.addSelection(selection);
        }
        return "redirect:/Technician/assistanceRequestList"; //aqúi probablmente redirigir al html de confirmación
    }

    @PostMapping("/apManagement/reject")
    public String rejectRequest(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                BindingResult bindingResult) {

        request.setState(State.REJECTED.name());
        assistanceRequestValidator.validate(request, bindingResult);
        if(bindingResult.hasErrors()) {
            request.setState(State.PENDING.name());
            return "Technician/apManagement";
        }
        assistanceReqDao.updateAssistanceRequest(request);
        return "redirect:/Technician/assistanceRequestList";
    }


    @RequestMapping(value="/addActivity")
    public String addActivity(Model model) {
        model.addAttribute("activity", new Activity());
        return "Technician/addActivity";
    }
    @RequestMapping(value="/addActivity", method=RequestMethod.POST)
    public String processAddActivity(@ModelAttribute("activity") Activity activity,
                                     BindingResult bindingResult) {

        if(instructorDao.getInstructor(activity.getIdInstructor()) == null){
            bindingResult.rejectValue("idInstructor", "nonExistent", "El instructor especificado no existe en la base de datos");
        }
        activityValidator.validate(activity, bindingResult);
        if(bindingResult.hasErrors())
            return "Technician/addActivity";

        activity.setIdActivity(cg.generateCode("ACT"));
        activityDao.addActivity(activity);

        return "redirect:activityList";
    }

    @RequestMapping(value="/activityManagement/{idActivity}", method = RequestMethod.GET)
    public String editActivity(Model model, @PathVariable String idActivity) {
        Activity activity = activityDao.getActivity(idActivity);

        model.addAttribute("activity", activity);
        return "Technician/activityManagement";
    }

    @RequestMapping(value="/activityManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitActivity(  @ModelAttribute ("activity") Activity activity,
                                                BindingResult bindingResult
    ) {
        if(instructorDao.getInstructor(activity.getIdInstructor()) == null){
            bindingResult.rejectValue("idInstructor", "nonExistent", "El instructor especificado no existe en la base de datos");
        }
        activityValidator.validate(activity, bindingResult);
        if(bindingResult.hasErrors())
            return "Technician/activityManagement";
        activityDao.updateActivity(activity);

        return "redirect:/Technician/activityList";
    }

    @RequestMapping(value="/activityList/delete/{idActivity}")
    public String processDeleteActivity(@PathVariable String idActivity) {
        activityDao.deleteActivity(idActivity);
        return "redirect:/Technician/activityList";
    }
}