package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.ActivityType;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.*;
import es.uji.ei1027.sgOvi.service.Services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/Technician")
public class TechnicianController {
    //AQUI METER SERVICIOS NUEVOS
    @Autowired
    private PapPatiService papPatiService;
    @Autowired
    private ActivityAttendanceService activityAttendanceService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private OviUserService oviUserService;
    @Autowired
    private AssistanceRequestService assistanceRequestService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private CodeGenerator cg;


    private OviUserValidator ouv = new OviUserValidator();
    private PapPatiValidator papPatiValidator = new PapPatiValidator();
    private InstructorValidator instructorValidator = new InstructorValidator();
    private PersonInstructorDtoValidator personInstructorDtoValidator = new PersonInstructorDtoValidator();
    private AssistanceRequestValidator assistanceRequestValidator = new AssistanceRequestValidator();
    private ActivityValidator activityValidator = new ActivityValidator();

    @RequestMapping("/menuTechnician")
    public String menuTechnician(Model model) {
        return "Technician/menuTechnician";
    }


    @RequestMapping(value="/userList", method = RequestMethod.POST)
    public String listOviUsersPOST(@ModelAttribute("filter") FilterState filter) {

        return "redirect:/Technician/userList/" + filter.getStateSel()
                + "?sort=" + filter.getSortSel();
    }

    @RequestMapping({"/userList", "/userList/{state}"})
    public String listOviUsers(Model model,
                               @PathVariable(required = false) String state,
                               @RequestParam(required = false, defaultValue = "nameAsc") String sort) {

        // Si no hay estado en la URL, por defecto es "ALL"
        String stateUrl = (state == null) ? "ALL" : state;


        List<PersonOviUserDTO> users = oviUserService.getPersonUserList(stateUrl, sort);
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

        List<PersonPapPatiDTO> papPatis = papPatiService.listByName(stateUrl, sort);
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

        List<PersonInstructorDTO> listaInstructors = instructorService.listByName(sort);
        model.addAttribute("instructors", listaInstructors);

        FilterState filter = new FilterState();
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);
        return "Technician/instructorList";
    }

    private List<Assistance_Request> filterAndSortAssistanceRequests(List<Assistance_Request> requests, String state, String sort) {
        // Filtrado por Estado
        if (state != null && !state.equals("ANY") && !state.equals("ALL")) {
            requests.removeIf(r -> !r.getState().name().equals(state));
        }

        // Ordenación
        if (sort != null) {
            switch (sort) {
                case "fechaAsc":
                    requests.sort(Comparator.comparing(Assistance_Request::getDate));
                    break;
                case "fecDesc":
                    requests.sort(Comparator.comparing(Assistance_Request::getDate).reversed());
                    break;
                case "id":
                    requests.sort(Comparator.comparing(Assistance_Request::getIdAsReq));
                    break;
            }
        }
        return requests;
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

        List<Assistance_Request> requests = assistanceRequestService.getAssistanceRequests();

        requests = filterAndSortAssistanceRequests(requests, stateUrl, sort);

        model.addAttribute("assistanceRequests", requests);

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

    private List<Activity> filterAndSortActivities(List<Activity> activities, String type, String sort) {
        if (type != null && !type.equals("ALL") && !type.equals("ANY")) {
            activities.removeIf(a -> !a.getType().name().equals(type));
        }
        if (sort != null) {
            switch (sort) {
                case "dataDesc":
                    activities.sort(Comparator.comparing(Activity::getDate));
                    break;
                case "dataAsc":
                    activities.sort(Comparator.comparing(Activity::getDate).reversed());
                    break;
                case "nombreAsc":
                    activities.sort(Comparator.comparing(Activity::getTittle, String.CASE_INSENSITIVE_ORDER));
                    break;
                case "id":
                default:
                    activities.sort(Comparator.comparing(Activity::getIdActivity));
                    break;
            }
        }
        return activities;
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

        List<Activity> activities = activityAttendanceService.getActivities();

        activities = filterAndSortActivities(activities, typeUrl, sort);

        model.addAttribute("activities", activities);

        FilterActivity filter = new FilterActivity();
        filter.setTypeSel(typeUrl);
        filter.setTypeList(Arrays.asList(ActivityType.values()));
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);

        return "Technician/activityList";
    }

    @RequestMapping(value="/userManagement/{dni}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable String dni) {
        OviUser user = oviUserService.getOviUser(dni);
        if(user == null)
            throw new OviException("El usuario OVI con dni: "+dni+" no existe", "Usuario no encontrado");
        model.addAttribute("user", user);
        return "Technician/userManagement";
    }

    @RequestMapping(value="/userManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitUser(@ModelAttribute ("user") OviUser user,
                                          BindingResult bindingResult, RedirectAttributes flash
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

        flash.addFlashAttribute("lista","/Technician/userList");
        if(oviUserService.getOviUser(user.getDni()).getState().name().equals(user.getState().name())){
            flash.addFlashAttribute("mensaje", "El usuario se ha actualizado correctamente");
        }
        else{
            if(user.getState().name().equals("APPROVED"))
                flash.addFlashAttribute("mensaje", "El usuario ha sido aceptado. Se ha enviado un correo a la dirección: "+ personDao.getPerson(user.getDni()).getEmail()+ " para notificar al usuario de la resolución de su solicitud");
            else
                flash.addFlashAttribute("mensaje", "El usuario ha sido rechazado. Se ha enviado un correo a la dirección: "+ personDao.getPerson(user.getDni()).getEmail()+ " para notificar al usuario de la resolución de su solicitud");

        }
        oviUserService.updateOviUser(user);
        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value="/papPatiManagement/{dni}", method = RequestMethod.GET)
    public String editPapPati(Model model, @PathVariable String dni) {

        PapPati papPati = papPatiService.getPapPati(dni);
        if(papPati == null)
            throw new OviException("El asistente personal con dni: " +dni+" no existe","Asistente Personal no encontrado");
        model.addAttribute("papPati", papPati);
        return "Technician/papPatiManagement";
    }

    @RequestMapping(value="/papPatiManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitPapPati( @ModelAttribute ("papPati") PapPati papPati,
                                              BindingResult bindingResult, RedirectAttributes flash
    ) {

        papPatiValidator.validate(papPati,bindingResult);
        if(bindingResult.hasErrors()){
            return "Technician/papPatiManagement";
        }
        if(papPati.getReason().isBlank())
            papPati.setReason(null);

        flash.addFlashAttribute("lista","/Technician/papPatiList");
        if(papPatiService.getPapPati(papPati.getDni()).getState().name().equals(papPati.getState().name())){
            flash.addFlashAttribute("mensaje", "El asistente personal se ha actualizado correctamente");
        }
        else{
            if(papPati.getState().name().equals("APPROVED"))
                flash.addFlashAttribute("mensaje", "El asistente ha sido aceptado. Se ha enviado un correo a la dirección: "+ personDao.getPerson(papPati.getDni()).getEmail()+ " para notificar al usuario de la resolución de su solicitud");
            else
                flash.addFlashAttribute("mensaje", "El asistente ha sido rechazado. Se ha enviado un correo a la dirección: "+ personDao.getPerson(papPati.getDni()).getEmail()+ " para notificar al usuario de la resolución de su solicitud");

        }
        papPatiService.updatePapPati(papPati);

        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value="/instructorManagement/{dni}", method = RequestMethod.GET)
    public String editInstructor(Model model, @PathVariable String dni) {
        Instructor instructor = instructorService.getInstructor(dni);
        if(instructor == null)
            throw new OviException("El instructor con dni: " +dni+" no existe","Instructor no encontrado");
        Person person = personDao.getPerson(dni);
        person.setPreference("INSTRUCTOR");
        person.setDataProtection(true);
        PersonInstructorDTO dto = new PersonInstructorDTO();
        dto.setInstructor(instructor);
        dto.setPerson(person);
        model.addAttribute("dto", dto);
        return "Technician/instructorManagement";
    }

    @RequestMapping(value="/instructorList/delete/{idInstructor}")
    public String deleteInstructor(@PathVariable String idInstructor){
        if(personDao.getPerson(idInstructor) != null){
            if(activityAttendanceService.getInstructorActivities(idInstructor).isEmpty()) {
                instructorService.deleteInstructor(idInstructor);
                personDao.deletePerson(idInstructor);
            }
            else
                throw new OviException("No se ha podido eliminar al instructor ya que tiene actividades pendientes","Acción no completada");
        }
        else
            throw new OviException("No se ha encontrado al instructor", "Usuario no encontrado");
        return "redirect:/Technician/instructorList";
    }

    @RequestMapping(value="/instructorManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitInstructor(@ModelAttribute ("dto") PersonInstructorDTO dto,
                                                BindingResult bindingResult, RedirectAttributes flash) {
        personInstructorDtoValidator.validate(dto,bindingResult);
        if(bindingResult.hasErrors()){
            return "Technician/instructorManagement";
        }
        personDao.updatePerson(dto.getPerson());
        instructorService.updateInstructor(dto.getInstructor());

        flash.addFlashAttribute("lista","/Technician/instructorList");
        flash.addFlashAttribute("mensaje","El instructor ha sido actualizado correctamente");
        return "redirect:/actionConfirmation";
    }
    @RequestMapping(value="/addInstructor")
    public String addInstructor(Model model) {
        model.addAttribute("personInstructor", new PersonInstructorDTO());
        return "Technician/addInstructor";
    }
    @RequestMapping(value="/addInstructor", method=RequestMethod.POST)
    public String processAddInstructor(@ModelAttribute("personInstructor") PersonInstructorDTO pidto,
                                       BindingResult bindingResult, RedirectAttributes flash) {

        personInstructorDtoValidator.validate(pidto, bindingResult);
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
        instructorService.addInstructor(instructor);

        flash.addFlashAttribute("lista","/Technician/instructorList");
        flash.addFlashAttribute("mensaje","El instructor ha sido creado correctamente");
        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value="/apManagement/{idAsReq}", method = RequestMethod.GET)
    public String editAssistanceRequest(Model model, @PathVariable String idAsReq) {
        Assistance_Request apReq = assistanceRequestService.getAssistanceRequest(idAsReq);
        if (apReq == null)
            throw new OviException("La solicitud de asistencia personal no existe", "Solicitud no encontrada");
        if(apReq.getState().equals(State.CLOSED_WITH_CONTRACT)){
            model.addAttribute("dto",contractService.getContractByAp(idAsReq));
        }
        model.addAttribute("assistanceRequest", apReq);
        return "Technician/apManagement";
    }

    @RequestMapping(value="/apManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitAssistanceRequest( @ModelAttribute ("assistanceRequest") Assistance_Request assistanceRequest,
                                                        BindingResult bindingResult
    ) {
        if(assistanceRequest.getReason().isBlank())
            assistanceRequest.setReason(null);
        assistanceRequestService.updateAssistanceRequest(assistanceRequest);

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
        assistanceRequestService.updateAssistanceRequest(request);
        return "redirect:/Technician/selectPapPati/" + request.getIdAsReq();
    }
    @RequestMapping(value="/selectPapPati/{idAsReq}", method = RequestMethod.GET)
    public String selectPapPati(Model model, @PathVariable String idAsReq, HttpSession session) {

        ArrayList<PersonPapPatiDTO> recomendados = assistanceRequestService.listCompatiblePapPati(idAsReq);
        List<PersonPapPatiDTO> todosLosPapPatis = personDao.getPapPatiPersons();
        List<String> seleccionadosDni = (List<String>) session.getAttribute("seleccionados");

        if (seleccionadosDni == null) {
            seleccionadosDni = new ArrayList<>();
            session.setAttribute("seleccionados", seleccionadosDni);
        }

        List<PersonPapPatiDTO> listaFinalSeleccionados = new ArrayList<>();
        for (String dni : seleccionadosDni) {
            PersonPapPatiDTO ppdto = new PersonPapPatiDTO();
            ppdto.setPapPati(papPatiService.getPapPati(dni));
            ppdto.setPerson(personDao.getPerson(dni));
            listaFinalSeleccionados.add(ppdto);
        }

        RequestPersonUserDTO requestDTO = new RequestPersonUserDTO();
        Assistance_Request ar = assistanceRequestService.getAssistanceRequest(idAsReq);
        requestDTO.setAssistanceRequest(ar);
        requestDTO.setOviUser(oviUserService.getOviUser(ar.getIdOviUser()));
        requestDTO.setPerson(personDao.getPerson(ar.getIdOviUser()));

        model.addAttribute("request", requestDTO);
        model.addAttribute("seleccionados", listaFinalSeleccionados);       // Lista definitiva
        model.addAttribute("recomendados", recomendados);                   // Sugerencias
        model.addAttribute("allPapPatis", todosLosPapPatis);                // El resto

        return "Technician/selectPapPati";
    }
    @RequestMapping("/selectPapPati/add/{idAsReq}/{dni}")
    public String addCandidate(@PathVariable String idAsReq, @PathVariable String dni, HttpSession session) {
        List<String> seleccionados = (List<String>) session.getAttribute("seleccionados");
        List<String> recomendados = (List<String>) session.getAttribute("recomendados");
        List<String> allPapPatis = (List<String>) session.getAttribute("allPapPatis");
         if (seleccionados != null && !seleccionados.contains(dni)) {
            seleccionados.add(dni);

            if(recomendados != null && allPapPatis != null) {
                if (recomendados.contains(dni))
                    recomendados.remove(dni);
                if (allPapPatis.contains(dni))
                    allPapPatis.remove(dni);
            }
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
    public String processSelection(@RequestParam("idAsReq") String idAsReq, HttpSession session, RedirectAttributes flash) {

        List<String> seleccionadosDni = (List<String>) session.getAttribute("seleccionados");

        if (seleccionadosDni == null || seleccionadosDni.isEmpty()) {
            return "redirect:/Technician/selectPapPati/" + idAsReq;
        }

        for (String dni : seleccionadosDni) {
            Selection selection = new Selection();
            selection.setIdSelection(cg.generateCode("SEL"));
            selection.setDate(LocalDate.now());
            selection.setState(State.PENDING.name());
            selection.setIdPapPati(dni);
            selection.setIdAsReq(idAsReq);

            assistanceRequestService.addSelection(selection);
        }

        session.removeAttribute("seleccionados");

        flash.addFlashAttribute("lista","/Technician/assistanceRequestList");
        flash.addFlashAttribute("mensaje","Se han enviado los candidatos correctamente");
        return "redirect:/actionConfirmation";
    }
    @PostMapping("/apManagement/reject")
    public String rejectRequest(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                BindingResult bindingResult, RedirectAttributes flash) {

        request.setState(State.REJECTED.name());
        assistanceRequestValidator.validate(request, bindingResult);
        if(bindingResult.hasErrors()) {
            request.setState(State.PENDING.name());
            return "Technician/apManagement";
        }
        assistanceRequestService.updateAssistanceRequest(request);
        flash.addFlashAttribute("lista","/Technician/assistanceRequestList");
        flash.addFlashAttribute("mensaje","Se ha rechazado la asistencia correctamente");
        return "redirect:/actionConfirmation";
    }


    @RequestMapping(value="/addActivity")
    public String addActivity(Model model) {
        model.addAttribute("activity", new Activity());
        return "Technician/addActivity";
    }
    @RequestMapping(value="/addActivity", method=RequestMethod.POST)
    public String processAddActivity(@ModelAttribute("activity") Activity activity,
                                     BindingResult bindingResult, RedirectAttributes flash) {

        if(instructorService.getInstructor(activity.getIdInstructor()) == null){
            bindingResult.rejectValue("idInstructor", "nonExistent", "El instructor especificado no existe en la base de datos");
        }
        activityValidator.validate(activity, bindingResult);
        if(bindingResult.hasErrors())
            return "Technician/addActivity";

        activity.setIdActivity(cg.generateCode("ACT"));
        activityAttendanceService.addActivity(activity);
        flash.addFlashAttribute("lista","/Technician/activityList");
        flash.addFlashAttribute("mensaje","La actividad ha sido creada correctamente");
        return "redirect:/actionConfirmation";

    }

    @RequestMapping(value="/activityManagement/{idActivity}", method = RequestMethod.GET)
    public String editActivity(Model model, @PathVariable String idActivity) {
        Activity activity = activityAttendanceService.getActivity(idActivity);
        if(activity == null)
            throw new OviException("La acitivdad con id: " +idActivity+" no existe","Actividad no encontrado");
        model.addAttribute("activity", activity);
        return "Technician/activityManagement";
    }

    @RequestMapping(value="/activityManagement/update", method = RequestMethod.POST)
    public String processUpdateSubmitActivity(  @ModelAttribute ("activity") Activity activity,
                                                BindingResult bindingResult, RedirectAttributes flash
    ) {
        if(instructorService.getInstructor(activity.getIdInstructor()) == null){
            bindingResult.rejectValue("idInstructor", "nonExistent", "El instructor especificado no existe en la base de datos");
        }
        activityValidator.validate(activity, bindingResult);
        if(bindingResult.hasErrors())
            return "Technician/activityManagement";
        activityAttendanceService.updateActivity(activity);
        flash.addFlashAttribute("lista","/Technician/activityList");
        flash.addFlashAttribute("mensaje","La actividad ha sido actualizado correctamente");
        return "redirect:/actionConfirmation";

    }

    @RequestMapping(value="/activityList/participants/{idActivity}")
    public String getParticipants(@PathVariable String idActivity, Model model,
                                  @RequestParam(value = "sortSel", required = false, defaultValue = "nameAsc") String sortSel){
        if(activityAttendanceService.getActivity(idActivity) != null){
            FilterState filterState = new FilterState();
            filterState.setSortSel(sortSel);
            System.out.println(papPatiService.getPapPatiTrainingActivities(idActivity));
            model.addAttribute("participantes",papPatiService.orderList(papPatiService.getPapPatiTrainingActivities(idActivity),sortSel));
            model.addAttribute("idActivity", idActivity);
            model.addAttribute("filter",filterState);
        }
        else{
            throw new OviException("La actividad "+ idActivity+" no fue encontrada", "Actividad no encontrad");
        }
        return "/Technician/participants";
    }
    @RequestMapping(value="/activityList/delete/{idActivity}")
    public String processDeleteActivity(@PathVariable String idActivity) {
        activityAttendanceService.deleteActivity(idActivity);
        return "redirect:/Technician/activityList";
    }
    @RequestMapping("/contractPersonList/{dni}")
    public String getContracts(@PathVariable String dni, Model model,
                               @RequestParam(value="sort",required = false, defaultValue = "dateDesc") String sort,
                               @RequestParam(value="urlPast") String urlPast) {
        if (personDao.getPerson(dni) != null){
            model.addAttribute("contracts",contractService.sortContracts(contractService.listContractPerson(dni),sort));
            model.addAttribute("currentSort", sort);
            model.addAttribute("urlPast",urlPast);
            model.addAttribute("dni",dni);
            if(oviUserService.getOviUser(dni) != null)
                model.addAttribute("rol", "OVI_USER");
            else
                model.addAttribute("rol","PAP_PATI");
            model.addAttribute("name",personDao.getPerson(dni).getName());
        }
        else
            throw new OviException("No existe el usuario con dni: "+dni, "Usuario no enontrado");
        return "/Technician/contractPersonList";
    }
}