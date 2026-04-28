package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Person;
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

    private AssistanceReqDao assistanceReqDao;

    @Autowired
    public void setAssistanceReqDao(AssistanceReqDao assistanceReqDao) {
        this.assistanceReqDao = assistanceReqDao;
    }
    @RequestMapping("/apRequestList")
    public String listAssistanceRequests(Model model, HttpSession session) {
        if (session == null){;
            return "redirect:/login";
        }
        Person person = (Person) session.getAttribute("user");
        model.addAttribute("assistanceRequests", assistanceReqDao.getOviAssistanceRequest(person.getDni()));
        return "Assistance_Request/apRequestList";
    }
    @RequestMapping(value="/requestAssistance")
    public String addAssistanceRequest(Model model, HttpSession session) {
        if (session == null){;
            return "redirect:/login";
        }
        Person person = (Person) session.getAttribute("user");
        Assistance_Request ap = new Assistance_Request();
        ap.setIdOviUser(person.getDni());
        model.addAttribute("assistanceRequest", ap);
        return "Assistance_Request/requestAssistance";
    }
    @RequestMapping(value="/requestAssistance", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                   BindingResult bindingResult) {
        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/requestAssistance";

        request.setData(LocalDate.now());
        request.setIdAsReq(generateARCode());
        request.setState("PENDING");
        request.setReason(null);

        assistanceReqDao.addAssistanceRequest(request);
        return "redirect:request_confirmation";
    }

    @RequestMapping(value="/request_confirmation")
    public String showConfirmationPage() {
        return "Assistance_Request/request_confirmation";
    }

    private String generateARCode(){
        ArrayList<Assistance_Request> ARList = (ArrayList<Assistance_Request>) assistanceReqDao.getAssistanceRequests();
        String last = ARList.getLast().getIdAsReq();
        int n = Integer.parseInt(last.trim().substring(3)) + 1;
        int a = Integer.toString(n).length();
        String base = "ASR";
        while(base.length()<=9){
            if(base.length() + a == 9) {
                base = base + n;
                break;
            }
            base = base + "0";
        }
        return base;
    }

}




