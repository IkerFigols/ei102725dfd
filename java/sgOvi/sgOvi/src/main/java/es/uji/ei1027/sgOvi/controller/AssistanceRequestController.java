package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Ovi_User;
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
import java.util.LinkedList;

@Controller
@RequestMapping("/Assistance_Request")
public class AssistanceRequestController {

    private AssistanceReqDao assistanceReqDao;

    @Autowired
    public void setAssistanceReqDao(AssistanceReqDao assistanceReqDao) {
        this.assistanceReqDao = assistanceReqDao;
    }
    @RequestMapping("/list")
    public String listAssistanceRequests(Model model) {
        model.addAttribute("assistanceRequests", assistanceReqDao.getAssistanceRequests());
        return "Assistance_Request/list";
    }
    @RequestMapping(value="/add")
    public String addAssistanceRequest(Model model) {

        model.addAttribute("assistanceRequest", new Assistance_Request());
        return "Assistance_Request/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("assistanceRequest") Assistance_Request request,
                                   BindingResult bindingResult) {
        AssistanceRequestValidator requestValidator = new AssistanceRequestValidator();
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "Assistance_Request/add";

        //request.setIdOviUser(request.getIdOviUser().toUpperCase());
        request.setData(LocalDate.now());
        request.setIdAsReq(generateARCode());
        request.setState("PENDIENTE");
        request.setReason(null);

        assistanceReqDao.addAssistanceRequest(request);
        return "redirect:request_confirmation";
    }

    @RequestMapping(value="/delete/{idAsReq}")
    public String processDelete(@PathVariable String idAsReq) {
        assistanceReqDao.deleteAssistanceRequest(idAsReq);
        return "redirect:../list";
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




