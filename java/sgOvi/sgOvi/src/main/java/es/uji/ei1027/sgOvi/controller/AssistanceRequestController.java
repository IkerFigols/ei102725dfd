package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/Assistance_Request")
public class AssistanceRequestController {
    @Autowired
    private AssistanceReqDao assistanceReqDao;

    @Autowired
    private AssistanceRequestService assistanceRequestService;



    @Autowired
    private ListPapPatiSelService listPapPatiSelService;

    @RequestMapping("/apRequestList")
    public String listAssistanceRequests(Model model, HttpSession session) {
        Person person = (Person) session.getAttribute("user");
        model.addAttribute("assistanceRequests", assistanceReqDao.getOviAssistanceRequest(person.getDni()));
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
        CodeGenerator cg = new CodeGenerator();

        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/requestAssistance";

        request.setDate(LocalDate.now());
        request.setIdAsReq(cg.generateCode("ASR"));
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
    @RequestMapping(value = "/approveSelection/{id}", method = RequestMethod.POST)
    public String approveSelection(@PathVariable("id") String idSelection) {
        // Aquí llamarías a tu servicio o DAO
        assistanceRequestService.updateState(idSelection, State.APPROVED.name());

        // Redirigimos de vuelta a la lista para ver el cambio
        return "redirect:/Assistance_Request/papPatiSelection";
    }

    @RequestMapping(value="/rejectSelection/{id}", method = RequestMethod.POST)
    public String rejectSelection(@PathVariable("id") String idSelection) {
        assistanceRequestService.updateState(idSelection, State.REJECTED.name());
        return "redirect:/Assistance_Request/papPatiSelection";
    }

    @RequestMapping(value="/papPatiInfo/{idPapPati}")
    public String getPapPatiInfo(Model model, @PathVariable("idPapPati") String idPapPati, HttpSession session){
        Person person = assistanceRequestService.getPerson(idPapPati);
        PapPati papPati = assistanceRequestService.getPapPati(idPapPati);
        PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
        personPapPatiDTO.setPapPati(papPati);
        personPapPatiDTO.setPerson(person);
        model.addAttribute("pappati",personPapPatiDTO);

        return "Assistance_Request/papPatiInfo";
    }
}




