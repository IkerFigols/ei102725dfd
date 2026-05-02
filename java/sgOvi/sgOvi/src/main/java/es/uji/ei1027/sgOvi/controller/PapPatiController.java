package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
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

import java.util.List;

@Controller
@RequestMapping("/Pap_Pati")
public class PapPatiController {

    @Autowired
    private PapPatiDao papPatiDao;

    @Autowired
    private SelectionDao selectionDao;

    @Autowired
    private ResourcesByDni resourcesByDni;

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    public void setPapPatiDao(PapPatiDao papPatiDao) {
        this.papPatiDao = papPatiDao;
    }

    @RequestMapping("/menuPapPati")
    public String menu(HttpSession session) {
        return "Pap_Pati/menuPapPati";
    }

    @RequestMapping(value="/updatePreference", method = RequestMethod.GET)
    public String editPreferences(Model model, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        model.addAttribute("obj", papPatiDao.getPapPati(user.getDni()));
        return "changePreferences";
    }

    @RequestMapping(value="/updatePreference", method = RequestMethod.POST)
    public String processUpdatePreference(@ModelAttribute("obj") PapPati papPati,
                                          BindingResult bindingResult,
                                          HttpSession session) {

        PapPatiPreferencesValidator prefValidator = new PapPatiPreferencesValidator();
        prefValidator.validate(papPati, bindingResult);

        if (bindingResult.hasErrors()) {
            return "changePreferences";
        }

        //Para asegurarnos de que el dni no se ha modificado.
        Person user = (Person) session.getAttribute("user");
        papPati.setDni(user.getDni());

        papPatiDao.updatePreferences(papPati);

        return "redirect:/PapPati/menu";
    }

    @RequestMapping("/contracts")
    public String listContracts(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");
        model.addAttribute("contracts", resourcesByDni.getContractsByDni(user.getDni()));

        model.addAttribute("userType", RolUser.PAP_PATI.name());
        return "Contracts/list";
    }
    @RequestMapping("/APList")
    public String listAPs(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");
        model.addAttribute("selections", selectionDao.getSelectionsByPapPati(user.getDni()));
        model.addAttribute("userType", RolUser.PAP_PATI.name());
        return "Pap_Pati/APList";
    }

    @RequestMapping("/activityList")
    public String listActivities(Model model) {
        return "Pap_Pati/activityList";
    }

    @RequestMapping("/apRequestList")
    public String listRequests(Model model, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        List<AssistanceRequestSelectionDTO> requests =
                assistanceRequestService.getRequestsByPapPati(user.getDni());

        model.addAttribute("requests", requests);
        return "Pap_Pati/APList";
    }


    @RequestMapping("/messages")
    public String listMessages(jakarta.servlet.http.HttpServletRequest request, Model model) {
        String idSelection = request.getParameter("idSelection");

        model.addAttribute("communications", assistanceRequestService.getComunicationsSelection(idSelection));
        model.addAttribute("idSelection", idSelection);

        return "Pap_Pati/messages";
    }

}