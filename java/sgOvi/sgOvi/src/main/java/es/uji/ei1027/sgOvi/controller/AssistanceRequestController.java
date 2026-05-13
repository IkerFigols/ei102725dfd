package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
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
import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping(value="/apRequestList", method = RequestMethod.POST)
    public String listRequestsPOST(@ModelAttribute("filter") FilterState filter,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "apRequestList/ALL";
        }

        return "redirect:/Assistance_Request/apRequestList/" + filter.getStateSel()
                + "?sort=" + filter.getSortSel();
    }
    @RequestMapping("/apRequestList/{state}")
    public String listAssistanceRequests(HttpSession session, Model model,
                                         @PathVariable String state,
                                         @RequestParam(required = false, defaultValue = "dateDesc") String sort) {

        Person person = (Person) session.getAttribute("user");

        String stateForDao = state.equals("ALL") ? null : state;

        List<Assistance_Request> requests = assistanceReqDao.getAssistanceRequestsByOviUser(person.getDni(), stateForDao, sort);
        model.addAttribute("assistanceRequests", requests);

        FilterState filter = new FilterState();
        filter.setStateSel(state);
        filter.setStateList(Arrays.asList(State.values()));
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
        if(!person.getDni().equals(assistanceReqDao.getAssistanceRequest(idAsReq).getIdOviUser()))
            return "redirect:/Assistance_Request/apRequestList";
        model.addAttribute("assistanceRequest",assistanceReqDao.getAssistanceRequest(idAsReq));
        return "Assistance_Request/details";
    }
    @RequestMapping(value="/requestAssistance", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                   BindingResult bindingResult) {

        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();

        request.setDate(LocalDate.now());
        request.setIdAsReq(codeGenerator.generateCode("ASR"));
        request.setState("PENDING");
        request.setReason(null);
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return "Assistance_Request/requestAssistance";
        }

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


    @RequestMapping(value = "/approveSelection/{idSelection}", method = RequestMethod.POST)
    public String approveSelection(@PathVariable("idSelection") String idSelection, @RequestParam("idAsReq") String idAsReq, @RequestParam("idPapPati") String idPapPati) {

        assistanceRequestService.updateStateSelection(idSelection, State.APPROVED.name());
        Assistance_Request ap = assistanceReqDao.getAssistanceRequest(idAsReq);
        ap.setState("CLOSED_WITH_CONTRACT");
        assistanceReqDao.updateAssistanceRequest(ap);
        assistanceRequestService.generateContract(idSelection,ap);
        assistanceRequestService.rejectOtherCandidates(idAsReq,idPapPati);
        // Redirigimos de vuelta a la lista para ver el cambio
        return "redirect:/Assistance_Request/apRequestList";
    }
    @RequestMapping("/papPatiSelection/{idAsReq}")
    public String getSelections(Model model, @PathVariable("idAsReq") String idAsReq, HttpSession session){

        Assistance_Request assistanceRequest = assistanceReqDao.getAssistanceRequest(idAsReq);
        Person person = (Person) session.getAttribute("user");
        if(!assistanceRequest.getIdOviUser().equals(person.getDni()))
            return "redirect:/Ovi_User/menuOviUser";
        List<PapPatiSelectionDTO> papPatiSelectionDTO = assistanceRequestService.getPapPatisSelectionDTO(idAsReq);
        model.addAttribute("dtos", papPatiSelectionDTO);
        return "Assistance_Request/papPatiSelection";
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
                                                 @RequestParam("idAsReq") String idAsReq, HttpSession session){
        if(bindingResult.hasErrors())
            return "Assistance_Request/communication";
        String idSelection = communication.getIdSelection();
        String information="";
        String role = (String) session.getAttribute("rol" +
                "");
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



