package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.PapPatiSelectionDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;
import es.uji.ei1027.sgOvi.service.Services.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.Services.CodeGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/Assistance_Request")
public class AssistanceRequestController {

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    private CodeGenerator codeGenerator;

    @RequestMapping("/papPatiSelection/{idAsReq}")
    public String getSelections(Model model, @PathVariable("idAsReq") String idAsReq,
                                @RequestParam(value = "stateSel",required = false, defaultValue = "ALL") String state,
                                @RequestParam(value ="sortSel",required = false, defaultValue = "name") String sort,
                                HttpSession session){

        Assistance_Request assistanceRequest = assistanceRequestService.getAssistanceRequest(idAsReq);
        Person person = (Person) session.getAttribute("user");
        if (assistanceRequest == null)
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq+" no existe", "Solicitud no encontrada");
        if(!assistanceRequest.getIdOviUser().equals(person.getDni()))
            throw new OviException("Esta solicitud de asistencia personal no es tuya", "Acceso no autorizado");

        FilterState filterState = new FilterState();
        filterState.setStateSel(state);
        filterState.setSortSel(sort);
        List<PapPatiSelectionDTO> papPatiSelectionDTO = assistanceRequestService.getPapPatisSelectionDTO(idAsReq, state);

        model.addAttribute("idAsReq",idAsReq);
        model.addAttribute("dtos", assistanceRequestService.sortSelection(papPatiSelectionDTO,sort));
        model.addAttribute("filter",filterState);
        return "Assistance_Request/papPatiSelection";
    }

    @RequestMapping("/apRequestList")
    public String listAssistanceRequests(HttpSession session, Model model,
                                         @RequestParam(value = "stateSel", required = false, defaultValue = "ALL") String state,
                                         @RequestParam( value= "sortSel", required = false, defaultValue = "dateDesc") String sort) {
        Person person = (Person) session.getAttribute("user");
        State stateForDao = State.fromString(state);
        List<Assistance_Request> requests = assistanceRequestService.getAssistanceRequestsByOviUser(person.getDni(), stateForDao);
        model.addAttribute("assistanceRequests", assistanceRequestService.sortAssistance(requests, sort));
        FilterState filter = new FilterState();
        filter.setStateSel(state);
        filter.setSortSel(sort);
        filter.setSortList(Arrays.asList("dateDesc", "dateAsc", "tittle"));
        model.addAttribute("filter", filter);

        return "Assistance_Request/apRequestList";
    }

    @RequestMapping(value="/requestAssistance")
    public String addAssistanceRequest(Model model, HttpSession session) {

        Person person = (Person) session.getAttribute("user");
        Assistance_Request ap = new Assistance_Request();
        ap.setIdOviUser(person.getDni());
        model.addAttribute("assistanceRequest", ap);
        return "Assistance_Request/requestAssistance";
    }


    @RequestMapping(value="/details/{idAsReq}")
    public String getApDetails(@PathVariable("idAsReq") String idAsReq, HttpSession session, Model model){
        Person person =(Person) session.getAttribute("user");
        Assistance_Request assistanceRequest = assistanceRequestService.getAssistanceRequest(idAsReq);
        if(assistanceRequest == null)
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq+" no existe", "Solicitud no encontrada");

        if(!person.getDni().equals(assistanceRequestService.getAssistanceRequest(idAsReq).getIdOviUser()))
            throw new OviException("Esta solicitud de asistencia personal no es tuya", "Acceso no autorizado");

        model.addAttribute("assistanceRequest",assistanceRequestService.getAssistanceRequest(idAsReq));
        return "Assistance_Request/details";
    }
    @RequestMapping(value="/requestAssistance", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                   BindingResult bindingResult, RedirectAttributes flash) {

        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();
        request.setDate(LocalDate.now());
        request.setIdAsReq(codeGenerator.generateCode("ASR"));
        request.setState("PENDING");
        request.setReason(null);
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return "Assistance_Request/requestAssistance";
        }
        flash.addFlashAttribute("lista","/Assistance_Request/apRequestList");
        flash.addFlashAttribute("mensaje","La solicitud se ha registrado correctamente. La resolución de la misma se le enviará a su correo electrónico.");
        assistanceRequestService.addAssistanceRequest(request);
        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value="/delete/{idAsReq}")
    public String deleteAp(@PathVariable("idAsReq") String idAsReq, HttpSession session, RedirectAttributes flash){

        Person person = (Person) session.getAttribute("user");

        if(assistanceRequestService.getAssistanceRequest(idAsReq) == null)
            throw new OviException("No se puede eliminar una solicitud de asistencia que no existe", "Solicitud no encontrada");
        if(assistanceRequestService.getAssistanceRequest(idAsReq).getIdOviUser().equals(person.getDni())){
            if(assistanceRequestService.getAssistanceRequest(idAsReq).getState().equals(State.PENDING))
                assistanceRequestService.deleteAssistanceRequest(idAsReq);
            else
                throw new OviException("No se ha podido eliminar la solicitud de asistencia ya que la solicitud esta en un estado diferente de pendiente","Error al eliminar la solicitud");
        }
        else
            throw new OviException("No puedes eliminar una solicitud de asistencia que no te pertenece","Error al eliminar la solicitud");

        flash.addFlashAttribute("lista","/Assistance_Request/apRequestList");
        flash.addFlashAttribute("mensaje","La solicitud ha sido eliminada correctamente");
        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value="/update/{idAsReq}")
    public String getUpdateAp(Model model, @PathVariable("idAsReq") String idAsReq, HttpSession session){
        Assistance_Request ap = assistanceRequestService.getAssistanceRequest(idAsReq);
        if(ap == null)
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq +" no existe","Solicitud no encontrada");
        Person person = (Person) session.getAttribute("user");
        if(!ap.getIdOviUser().equals(person.getDni()))
            throw new OviException("Esta solicitud de asistencia personal no es tuya", "Acceso no autorizado");
        if(!ap.getState().equals(State.PENDING))
            throw new OviException("No se puede actualizar una asistencia personal que ya no esta en estado pendiente","Acción no completada");
        model.addAttribute("assistanceRequest",ap);
        return "Assistance_Request/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String getUpdateAp(@ModelAttribute("assistanceRequest") Assistance_Request assistanceRequest, BindingResult bindingResult,
                              RedirectAttributes flash){
        AssistanceRequestValidator assistanceRequestValidator = new AssistanceRequestValidator();
        assistanceRequestValidator.validate(assistanceRequest, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/update";
        flash.addFlashAttribute("lista","/Assistance_Request/apRequestList");
        flash.addFlashAttribute("mensaje","La solicitud ha sido actualizada correctamente");
        assistanceRequestService.updateAssistanceRequest(assistanceRequest);
        return "redirect:/actionConfirmation";
    }

    @RequestMapping(value = "/approveSelection/{idSelection}", method = RequestMethod.POST)
    public String approveSelection(@PathVariable("idSelection") String idSelection, @RequestParam("idAsReq") String idAsReq, @RequestParam("idPapPati") String idPapPati) {
        Selection selection = assistanceRequestService.getSelection(idSelection);

        Assistance_Request ap = assistanceRequestService.getAssistanceRequest(idAsReq);
        if (ap == null) {
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq +" no existe","Solicitud no encontrada");
        }
        if(selection == null)
            throw new OviException("La selección no existe","Selección no econtrada");

        assistanceRequestService.updateStateSelection(idSelection, State.APPROVED.name());
        return "redirect:/Assistance_Request/papPatiSelection/"+idAsReq;
    }


    @RequestMapping(value="/rejectSelection/{idSelection}", method = RequestMethod.POST)
    public String rejectSelection(@PathVariable("idSelection") String idSelection,  @RequestParam("idAsReq") String idAsReq) {
        Assistance_Request ap = assistanceRequestService.getAssistanceRequest(idAsReq);
        if (ap == null)
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq +" no existe","Solicitud no encontrada");
        assistanceRequestService.updateStateSelection(idSelection, State.REJECTED.name());
        return "redirect:/Assistance_Request/papPatiSelection/"+idAsReq;
    }

    @RequestMapping(value="/papPatiInfo/{idPapPati}")
    public String getPapPatiInfo(Model model, @PathVariable("idPapPati") String idPapPati, @RequestParam("idAsReq") String idAsReq){
        Person person = assistanceRequestService.getPerson(idPapPati);
        if(person == null)
            throw new OviException("El dni: "+idPapPati +"no existe", "Dni no encontrada");

        PapPati papPati = assistanceRequestService.getPapPati(idPapPati);

        if(papPati == null)
            throw new OviException("El dni: "+ idPapPati +"no existe", "Dni no encontrado");
        PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
        personPapPatiDTO.setPapPati(papPati);
        personPapPatiDTO.setPerson(person);
        model.addAttribute("idAsReq", idAsReq);
        model.addAttribute("personPapPatiDTO",personPapPatiDTO);

        return "Assistance_Request/papPatiInfo";
    }

    @RequestMapping(value = "/communication/{idSelection}")
    public String getCommunicationSelection(Model model, @PathVariable("idSelection") String idSelection, @RequestParam("idAsReq")  String idAsReq, HttpSession session){
        Assistance_Request ap = assistanceRequestService.getAssistanceRequest(idAsReq);
        Selection selection = assistanceRequestService.getSelection(idSelection);
        Person user = (Person) session.getAttribute("user");
        if (ap == null) {
            throw new OviException("La solicitud de asistencia personal con id: "+ idAsReq +" no existe","Solicitud no encontrada");
        }
        if(selection == null)
            throw new OviException("La selección no existe","Selección no econtrada");

        if(!ap.getIdOviUser().equals(user.getDni()) && !selection.getIdPapPati().equals(user.getDni()))
            throw new OviException("No puedes acceder a este chat ya que no es tuya","Acceso no autorizado");

        String rol = (String) session.getAttribute("rol");
        if(rol.equals(RolUser.OVI_USER.name()))
            model.addAttribute("nameP",assistanceRequestService.getPerson(selection.getIdPapPati()).getName());
        else
            model.addAttribute("nameP",assistanceRequestService.getPerson(ap.getIdOviUser()).getName());
        model.addAttribute("communications",assistanceRequestService.getComunicationsSelection(idSelection));
        model.addAttribute("idSelection",idSelection);
        Communication communication = new Communication();
        communication.setIdSelection(idSelection);
        model.addAttribute("idAsReq",idAsReq);
        model.addAttribute("comunication", communication);
        return "Assistance_Request/communication";
    }
    @RequestMapping(value = "/communication/add", method = RequestMethod.POST)
    public String proccessAndSubmitCommunication(@ModelAttribute("comunication") Communication communication ,BindingResult bindingResult,
                                                 @RequestParam("idAsReq") String idAsReq, HttpSession session){
        if(bindingResult.hasErrors())
            return "Assistance_Request/communication";
        String idSelection = communication.getIdSelection();
        String information="";
        String role = (String) session.getAttribute("rol");
        if(role.equals("OVI_USER"))
            information = "OviUser: " + communication.getInformation();
        else
            information = "PapPati: "+ communication.getInformation();

        communication.setInformation(information);
        communication.setDate(LocalDate.now());
        communication.setIdSelection(idSelection);
        communication.setIdCommunication(codeGenerator.generateCode("COM"));
        assistanceRequestService.addCommunication(communication);
        return "redirect:/Assistance_Request/communication/" + idSelection + "?idAsReq=" + idAsReq;
    }


}