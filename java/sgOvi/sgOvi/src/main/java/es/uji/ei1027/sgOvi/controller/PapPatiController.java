package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.UserDetails;
import es.uji.ei1027.sgOvi.service.ResourcesByDni;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/PapPati")
public class PapPatiController {

    private PapPatiDao papPatiDao;

    @Autowired
    public void setPapPatiDao(PapPatiDao papPatiDao) {
        this.papPatiDao = papPatiDao;
    }

    @RequestMapping("/menu")
    public String menu(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "Pap_Pati/menuPapPati";
    }

    @RequestMapping(value="/updatePreference/{dni}", method = RequestMethod.GET)
    public String editPreferences(Model model, @PathVariable String dni, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        if (user == null || !user.getDni().equals(dni)) {
            return "redirect:/login";
        }

        model.addAttribute("obj", user.getDni());
        model.addAttribute("userType", "papPati");
        return "changePreferences"; //
    }

    @RequestMapping(value="/updatePreference", method = RequestMethod.POST)
    public String processUpdatePreference(@ModelAttribute("obj") PapPati papPati,
                                          BindingResult bindingResult, Model model) {

        PapPatiPreferencesValidator prefValidator = new PapPatiPreferencesValidator();
        prefValidator.validate(papPati, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userType", "papPati");
            return "changePreferences";
        }

        papPatiDao.updatePreferencias(papPati.getDni(), papPati.getPapPatiPreferences());

        return "redirect:/PapPati/menu";
    }
    @Autowired
    private ResourcesByDni resourcesByDni;

    @RequestMapping("/contracts")
    public String listContracts(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("contracts", resourcesByDni.getContractsByDni(user.getDni()));

        model.addAttribute("userType", "papPati");
        return "Contracts/list";
    }
}