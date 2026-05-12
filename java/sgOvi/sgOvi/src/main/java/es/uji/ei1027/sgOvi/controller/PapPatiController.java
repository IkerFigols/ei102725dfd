package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.Selection;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.service.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.ResourcesByDni;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;

import java.util.List;

@Controller
@RequestMapping("/Pap_Pati")
public class PapPatiController {

    @Autowired
    private PapPatiDao papPatiDao;

    @Autowired
    private SelectionDao selectionDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ResourcesByDni resourcesByDni;

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    private AssistanceReqDao assistanceReqDao;

    @Autowired
    public void setPapPatiDao(PapPatiDao papPatiDao) {
        this.papPatiDao = papPatiDao;
    }

    @RequestMapping("/menuPapPati")
    public String menu(HttpSession session) {
        return "Pap_Pati/menuPapPati";
    }


    @RequestMapping("/contracts")
    public String listContracts(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");
        model.addAttribute("contracts", resourcesByDni.getContractsByDni(user.getDni()));

        model.addAttribute("userType", RolUser.PAP_PATI.name());
        return "Contracts/list";
    }

    @RequestMapping("/APList")
    public String listRequests(Model model, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        List<AssistanceRequestSelectionDTO> requests =
                assistanceRequestService.getRequestsByPapPati(user.getDni());

        model.addAttribute("requests", requests);
        model.addAttribute("userType", RolUser.PAP_PATI.name());
        return "Pap_Pati/APList";
    }

    @RequestMapping("/messages")
    public String listMessages(jakarta.servlet.http.HttpServletRequest request, Model model) {
        String idSelection = request.getParameter("idSelection");

        model.addAttribute("communications", assistanceRequestService.getComunicationsSelection(idSelection));
        model.addAttribute("idSelection", idSelection);

        return "Pap_Pati/messages";
    }
    @RequestMapping(value = "/papPatiAssistances", method = RequestMethod.POST)
    public String viewAssistanceDetails(String idSelection, Model model) {

        Selection selection = selectionDao.getSelection(idSelection);

        Assistance_Request assistance = assistanceReqDao.getAssistanceRequest(selection.getIdAsReq());


        Person oviUser = personDao.getPerson(assistance.getIdOviUser());


        model.addAttribute("assistance", assistance);
        model.addAttribute("selection", selection);
        model.addAttribute("oviUserName", (oviUser != null) ? oviUser.getName() : "--------------");
        model.addAttribute("userType", RolUser.PAP_PATI.name());

        return "Pap_Pati/PapPatiAssistances";
    }
}