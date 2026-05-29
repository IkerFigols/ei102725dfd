package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.service.DTOs.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.Services.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.Services.ContractService;
import es.uji.ei1027.sgOvi.service.Services.PapPatiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Comparator;

import java.util.List;

@Controller
@RequestMapping("/Pap_Pati")
public class PapPatiController {



    @Autowired
    private PapPatiService papPatiService;

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    private ContractService contractService;

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
        model.addAttribute("contracts", contractService.listContractPerson(user.getDni()));
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
    @RequestMapping("/papPatiAssistances/{idAsReq}")
    public String viewAssistanceDetails(@PathVariable String idAsReq, @RequestParam("idSelection") String idSelection, Model model, HttpSession session) {
        Selection selection = assistanceRequestService.getSelection(idSelection);
        if(selection == null)
            throw new OviException("La selección no existe","Selección no encontrada");
        Assistance_Request assistance = assistanceRequestService.getAssistanceRequest(idAsReq);
        if(assistance == null)
            throw new OviException("La solicitud de asistencia personal no existe", "Solicitud de Asistencia personal no encontrada");
        Person oviUser = papPatiService.getPerson(assistance.getIdOviUser());
        Person user = (Person) session.getAttribute("user");
        if(!selection.getIdPapPati().equals(user.getDni()))
            throw new OviException("No estas asociado a esta solicitud", "Acceso no autorizado") ;
        model.addAttribute("assistance", assistance);
        model.addAttribute("selection", selection);
        model.addAttribute("oviUserName", (oviUser != null) ? oviUser.getName() : "--------------");
        model.addAttribute("userType", RolUser.PAP_PATI.name());

        return "Pap_Pati/PapPatiAssistances";
    }
}