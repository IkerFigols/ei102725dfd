package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.service.DTOs.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.Services.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.Services.ResourcesByDni;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Comparator;

import java.util.List;

@Controller
@RequestMapping("/Pap_Pati")
public class PapPatiController {



    @Autowired
    private PapPatiDao papPatiService;

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

    //Método para la ordenación
    private List<AssistanceRequestSelectionDTO> sortRequests(List<AssistanceRequestSelectionDTO> requests, String sort) {
        if (sort == null) return requests;
        switch (sort) {
            case "dateAsc":
                requests.sort(Comparator.comparing(AssistanceRequestSelectionDTO::getSelectionDate,
                        Comparator.nullsLast(Comparator.naturalOrder())));
                break;
            case "dateDesc":
                requests.sort(Comparator.comparing(AssistanceRequestSelectionDTO::getSelectionDate,
                        Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "titleAsc":
                requests.sort(Comparator.comparing(dto -> dto.getAssistanceRequest().getTittle(),
                        String.CASE_INSENSITIVE_ORDER));
                break;
        }
        return requests;
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
    public String listRequests(HttpSession session, Model model,
                               @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
                               @RequestParam(value = "sort", required = false, defaultValue = "dateDesc") String sort) {

        Person user = (Person) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<AssistanceRequestSelectionDTO> requests = assistanceRequestService.getRequestsByPapPatiFiltered(user.getDni(), state);

        requests = sortRequests(requests, sort);

        model.addAttribute("requests", requests);

        FilterState filter = new FilterState();
        filter.setStateSel(state);
        filter.setSortSel(sort);

        model.addAttribute("filter", filter);
        model.addAttribute("userType", "PAP_PATI");

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