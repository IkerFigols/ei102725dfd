package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.ActivityType;
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
import java.util.Arrays;
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

    // 1. Método POST para procesar el formulario de filtros
    @RequestMapping(value="/userList", method = RequestMethod.POST)
    public String listOviUsersPOST(@ModelAttribute("filter") FilterState filter) {
        // Redirigimos a la URL con el estado y el parámetro de ordenación
        return "redirect:/Technician/userList/" + filter.getStateSel()
                + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/userList", "/userList/{state}"})
    public String listOviUsers(Model model,
                               @PathVariable(required = false) String state,
                               @RequestParam(required = false, defaultValue = "nameAsc") String sort) {

        // Si no hay estado en la URL, por defecto es "ALL"
        String stateUrl = (state == null) ? "ALL" : state;

        // Llamamos a tu servicio LBN (asegúrate de que acepte estos dos Strings)
        List<PersonOviUserDTO> users = lbn.personUserList(stateUrl, sort);
        model.addAttribute("users", users);

        // Creamos el objeto FilterState para que los selectores mantengan el valor marcado
        FilterState filter = new FilterState();
        filter.setStateSel(stateUrl);
        ArrayList<State> lista = new ArrayList<>();
        lista.add(State.PENDING);
        lista.add(State.APPROVED);  //Solo estos tres porque para personas no hay cerrado
        lista.add(State.REJECTED);
        filter.setStateList(lista);
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);

        return "Technician/userList";
    }
    @RequestMapping(value = "/papPatiList", method = RequestMethod.POST)
    public String listPapPatiPOST(@ModelAttribute("filter") FilterState filter) {
        return "redirect:/Technician/papPatiList/" + filter.getStateSel()
                + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/papPatiList", "/papPatiList/{state}"})
    public String listPapPatis(Model model,
                               @PathVariable(required = false) String state,
                               @RequestParam(required = false, defaultValue = "nameAsc") String sort) {

        String stateUrl = (state == null) ? "ALL" : state;

        List<PersonPapPatiDTO> papPatis = lbn.personPapPatiList(stateUrl, sort);
        model.addAttribute("papPatis", papPatis);

        FilterState filter = new FilterState();
        filter.setStateSel(stateUrl);
        ArrayList<State> lista = new ArrayList<>();
        lista.add(State.PENDING);
        lista.add(State.APPROVED);
        lista.add(State.REJECTED);
        filter.setStateList(lista);
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);

        return "Technician/papPatiList";
    }

    @RequestMapping(value= "/instructorList", method = RequestMethod.POST)
    public String listInstructorPOST(@ModelAttribute("filter") FilterState filter) {
        return "redirect:/Technician/instructorList"
                + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/instructorList"})
    public String listInstructors(Model model,
                               @RequestParam(required = false, defaultValue = "nameAsc") String sort) {

        List<PersonInstructorDTO> listaInstructors = lbn.personInstructorList(sort);
        model.addAttribute("instructors", listaInstructors);

        FilterState filter = new FilterState();
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);
        return "Technician/instructorList";
    }

    @RequestMapping(value = "/assistanceRequestList", method = RequestMethod.POST)
    public String listAssistanceRequestPOST(@ModelAttribute("filter") FilterState filter){
        return "redirect:/Technician/assistanceRequestList/" + filter.getStateSel()
                + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/assistanceRequestList", "/assistanceRequestList/{state}"})
    public String assistanceRequestList(Model model,
                               @PathVariable(required = false) String state,
                               @RequestParam(required = false, defaultValue = "fechaAsc") String sort) {
        String stateUrl = (state == null) ? "ANY" : state;

        List<Assistance_Request> assistanceRequests = assistanceReqDao.getAssistanceRequests(stateUrl, sort);
        model.addAttribute("assistanceRequests", assistanceRequests);

        FilterState filter = new FilterState();
        filter.setStateSel(stateUrl);
        ArrayList<State> lista = new ArrayList<>();
        lista.add(State.PENDING);
        lista.add(State.APPROVED);
        lista.add(State.REJECTED);
        lista.add(State.CLOSED_WITH_CONTRACT_DONE);
        lista.add(State.CLOSED_WITH_CONTRACT);
        filter.setStateList(lista);
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);

        return "Technician/assistanceRequestList";
    }

    @RequestMapping(value="/activityList", method = RequestMethod.POST)
    public String listActivitiesPOST(@ModelAttribute("filter") FilterActivity filter) {
        return "redirect:/Technician/activityList/" + filter.getTypeSel() + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/activityList", "/activityList/{type}"})
    public String listActivities(Model model,
                                 @PathVariable(required = false) String type,
                                 @RequestParam(required = false, defaultValue = "id") String sort) {

        String typeUrl = (type == null) ? "ALL" : type;

        // Llamada al DAO con los nuevos parámetros
        model.addAttribute("activities", activityDao.getActivities(typeUrl, sort));

        FilterActivity filter = new FilterActivity();
        filter.setTypeSel(typeUrl);
        // Cargamos los tipos del Enum ActivityType
        filter.setTypeList(Arrays.asList(ActivityType.values()));
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);

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

        ArrayList<PersonPapPatiDTO> recomendados = listPapPatiSelService.listCompatiblePapPati(idAsReq);
        List<PersonPapPatiDTO> todosLosPapPatis = personDao.getPapPatiPersons();
        List<String> seleccionadosDni = (List<String>) session.getAttribute("seleccionados");

        if (seleccionadosDni == null) {
            seleccionadosDni = new ArrayList<>();
            session.setAttribute("seleccionados", seleccionadosDni);
        }

        List<PersonPapPatiDTO> listaFinalSeleccionados = new ArrayList<>();
        for (String dni : seleccionadosDni) {
            PersonPapPatiDTO ppdto = new PersonPapPatiDTO();
            ppdto.setPapPati(papPatiDao.getPapPati(dni));
            ppdto.setPerson(personDao.getPerson(dni));
            listaFinalSeleccionados.add(ppdto);
        }

        RequestPersonUserDTO requestDTO = new RequestPersonUserDTO();
        Assistance_Request ar = assistanceReqDao.getAssistanceRequest(idAsReq);
        requestDTO.setAssistanceRequest(ar);
        requestDTO.setOviUser(oviUserDao.getOviUser(ar.getIdOviUser()));
        requestDTO.setPerson(personDao.getPerson(ar.getIdOviUser()));

        model.addAttribute("request", requestDTO);
        model.addAttribute("seleccionados", listaFinalSeleccionados); // Lista definitiva
        model.addAttribute("recomendados", recomendados);           // Sugerencias
        model.addAttribute("allPapPatis", todosLosPapPatis);               // El resto

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
    public String processSelection(@RequestParam("idAsReq") String idAsReq, HttpSession session) {

        List<String> seleccionadosDni = (List<String>) session.getAttribute("seleccionados");

        if (seleccionadosDni == null || seleccionadosDni.isEmpty()) {
            // Aquí podrías redirigir de vuelta con un error: "Debes seleccionar al menos uno"
            return "redirect:/Technician/selectPapPati/" + idAsReq;
        }

        for (String dni : seleccionadosDni) {
            Selection selection = new Selection();
            selection.setIdSelection(cg.generateCode("SEL"));
            selection.setDate(LocalDate.now());
            selection.setState(State.PENDING.name());
            selection.setIdPapPati(dni);
            selection.setIdAsReq(idAsReq);

            selectionDao.addSelection(selection);
        }

        // Limpiamos la sesión para que la próxima solicitud empiece de cero
        session.removeAttribute("seleccionados");

        return "redirect:/Technician/assistanceRequestList";
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