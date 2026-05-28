package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.Services.ResourcesByDni;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/Ovi_User")
public class OviUserController {

    private OviUserDao oviUserDao;


    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao) {
        this.oviUserDao=oviUserDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listOviUsers(Model model) {
        model.addAttribute("oviUsers", oviUserDao.getOviUsers());
        return "Ovi_User/list";
    }


    @RequestMapping("/menuOviUser")
    public String menu(HttpSession session, Model model) {
        return "Ovi_User/menuOviUser";
    }



    @RequestMapping("/activities")
    public String listActivities(HttpSession session) {
        return "redirect:/activity/list";
    }

    @RequestMapping("/requests")
    public String listRequests(HttpSession session) {
        return "redirect:/Assistance_Request/apRequestList";
    }

    @RequestMapping(value="/updatePreference/{dni}", method = RequestMethod.GET)
    public String editPreferences(Model model, @PathVariable String dni, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        model.addAttribute("obj", user.getDni());
        model.addAttribute("userType", "oviUser");

        return "changePreferences";
    }

    @RequestMapping(value="/updatePreference", method = RequestMethod.POST)
    public String processUpdatePreference(@ModelAttribute("obj") OviUser oviUser,
                                          BindingResult bindingResult, Model model) {

        OviUserPreferencesValidator prefValidator = new OviUserPreferencesValidator();
        prefValidator.validate(oviUser, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("userType", "oviUser");
            return "changePreferences";
        }

        return "redirect:/Ovi_User/menu";
    }
    @Autowired
    private ResourcesByDni resourcesByDni;

    @RequestMapping("/contracts")
    public String listContracts(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");
        List<Map<String, Object>> contracts = resourcesByDni.getContractsByDni(user.getDni());

        model.addAttribute("contracts", contracts);

        model.addAttribute("userType", (session.getAttribute("user") instanceof OviUser) ? "oviUser" : "papPati");

        return "Contracts/list";
    }
}
