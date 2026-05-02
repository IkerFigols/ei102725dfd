package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.CodeGenerator;
import es.uji.ei1027.sgOvi.service.ListPapPatiSelService;
import es.uji.ei1027.sgOvi.service.PersonPapPatiDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/Assistance_Request")
public class AssistanceRequestController {
    @Autowired
    private AssistanceReqDao assistanceReqDao;

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private ListPapPatiSelService listPapPatiSelService;

    @RequestMapping("/apRequestList") // Usamos la ruta principal
    public String listAssistanceRequests(HttpSession session, Model model,
                                         @RequestParam(required = false) String state) {
        Person user = (Person) session.getAttribute("user");
        List<Assistance_Request> requests;
        // Si hay un estado, filtramos. Si no, traemos todos.
        if (state != null && !state.isEmpty()) {
            requests = assistanceReqDao.getAssistanceRequestsByIdOviUserAndState(user.getDni(), state);
        } else {
            requests = assistanceReqDao.getOviAssistanceRequest(user.getDni());
        }

        model.addAttribute("assistanceRequests", requests);
        model.addAttribute("selectedState", state);

        // Elige UNA sola ruta para tu HTML (la que tenga el diseño nuevo)
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
        if(!person.getDni().equals(assistanceReqDao.getAssistanceRequest(idAsReq).getIdOviUser()))
            return "redirect:/Assistance_Request/apRequestList";
        model.addAttribute("assistanceRequest",assistanceReqDao.getAssistanceRequest(idAsReq));
        return "Assistance_Request/details";
    }
    @RequestMapping(value="/requestAssistance", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                   BindingResult bindingResult) {

        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/requestAssistance";

        request.setDate(LocalDate.now());
        request.setIdAsReq(codeGenerator.generateCode("ASR"));
        request.setState("PENDING");
        request.setReason(null);

        assistanceReqDao.addAssistanceRequest(request);
        return "redirect:request_confirmation";
    }

    @RequestMapping(value="/update/{idAsReq}")
    public String getUpdateAp(Model model, @PathVariable("idAsReq") String idAsReq, HttpSession session){
        Assistance_Request ap = assistanceReqDao.getAssistanceRequest(idAsReq);
        Person person = (Person) session.getAttribute("user");
        if(!ap.getIdOviUser().equals(person.getDni()))
            return "redirect:/";
        model.addAttribute("assistanceRequest",ap);
        return "Assistance_Request/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String getUpdateAp(@ModelAttribute("assistanceRequest") Assistance_Request assistanceRequest, BindingResult bindingResult){
        AssistanceRequestValidator assistanceRequestValidator = new AssistanceRequestValidator();
        assistanceRequestValidator.validate(assistanceRequest, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/update";

        assistanceReqDao.updateAssistanceRequest(assistanceRequest);
        return "redirect:/Assistance_Request/apRequestList";
    }

    @RequestMapping(value="/request_confirmation")
    public String showConfirmationPage() {
        return "Assistance_Request/request_confirmation";
    }


    @RequestMapping("/papPatiSelection/{idAsReq}")
    public String getSelections(Model model, @PathVariable("idAsReq") String idAsReq, HttpSession session){
        Assistance_Request assistanceRequest = assistanceReqDao.getAssistanceRequest(idAsReq);
        Person person = (Person) session.getAttribute("user");
        if(!assistanceRequest.getIdOviUser().equals(person.getDni()))
            return "redirect:/Ovi_User/menuOviUser";

        model.addAttribute("selections",assistanceRequestService.getSelectionsAP(idAsReq));
        return "Assistance_Request/papPatiSelection";
    }
    @RequestMapping(value = "/approveSelection/{idSelection}", method = RequestMethod.POST)
    public String approveSelection(@PathVariable("idSelection") String idSelection, @RequestParam("idAsReq") String idAsReq, @RequestParam("idPapPati") String idPapPati) {
        // Aquí llamarías a tu servicio o DAO
        assistanceRequestService.updateStateSelection(idSelection, State.APPROVED.name());
        Assistance_Request ap = assistanceReqDao.getAssistanceRequest(idAsReq);
        ap.setState("CLOSED_WITH_CONTRACT");
        assistanceReqDao.updateAssistanceRequest(ap);
        assistanceRequestService.generateContract(idSelection,ap);
        assistanceRequestService.rejectOtherCandidates(idAsReq,idPapPati);
        // Redirigimos de vuelta a la lista para ver el cambio
        return "redirect:/Assistance_Request/apRequestList";
    }

    @RequestMapping(value="/rejectSelection/{idSelection}", method = RequestMethod.POST)
    public String rejectSelection(@PathVariable("idSelection") String idSelection,  @RequestParam("idAsReq") String idAsReq) {
        assistanceRequestService.updateStateSelection(idSelection, State.REJECTED.name());
        return "redirect:/Assistance_Request/papPatiSelection/"+idAsReq;
    }

    @RequestMapping(value="/papPatiInfo/{idPapPati}")
    public String getPapPatiInfo(Model model, @PathVariable("idPapPati") String idPapPati, @RequestParam("idAsReq") String idAsReq){
        Person person = assistanceRequestService.getPerson(idPapPati);
        PapPati papPati = assistanceRequestService.getPapPati(idPapPati);
        PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
        personPapPatiDTO.setPapPati(papPati);
        personPapPatiDTO.setPerson(person);
        model.addAttribute("idAsReq", idAsReq);
        model.addAttribute("personPapPatiDTO",personPapPatiDTO);

        return "Assistance_Request/papPatiInfo";
    }

    @RequestMapping(value = "/communication/{idSelection}")
    public String getCommunicationSelection(Model model, @PathVariable("idSelection") String idSelection, @RequestParam("idAsReq")  String idAsReq){
        model.addAttribute("communications",assistanceRequestService.getComunicationsSelection(idSelection));
        model.addAttribute("idSelection",idSelection);
        Communication communication = new Communication();
        communication.setIdSelection(idSelection);
        model.addAttribute("idAsReq",idAsReq);
        model.addAttribute("comunication", communication);


        return "Assistance_Request/communication";
    }
    @RequestMapping(value = "/communication/add", method = RequestMethod.POST)
    public String proccessAndSubmitCommunication(@ModelAttribute("comunication") Communication communication ,Model model, BindingResult bindingResult,
                                                 @RequestParam("idAsReq") String idAsReq){
        if(bindingResult.hasErrors())
            return "Assistance_Request/comunication";
        String idSelection = communication.getIdSelection();
        String information = "OviUser: " + communication.getInformation();
        communication.setInformation(information);
        communication.setDate(LocalDate.now());
        communication.setIdSelection(idSelection);
        communication.setIdCommunication(codeGenerator.generateCode("COM"));
        assistanceRequestService.addCommunication(communication);
        return "redirect:/Assistance_Request/communication/" + idSelection + "?idAsReq=" + idAsReq;
    }


}




