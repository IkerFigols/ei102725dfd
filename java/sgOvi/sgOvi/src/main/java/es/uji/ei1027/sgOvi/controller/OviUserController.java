package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.model.OviUser;
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
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "Ovi_User/menuOviUser";
    }

    @RequestMapping("/contracts")
    public String listContracts(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        // Redirigimos a la ruta que gestiona el ContractController
        return "redirect:/contract/list";
    }

    @RequestMapping("/activities")
    public String listActivities(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "redirect:/activity/list";
    }

    @RequestMapping("/requests")
    public String listRequests(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "redirect:/Assistance_Request/apRequestList";
    }

    @RequestMapping(value="/updatePreference/{dni}", method = RequestMethod.GET)
    public String editPreferences(Model model, @PathVariable String dni, HttpSession session) {
        OviUser userSesion = (OviUser) session.getAttribute("user");

        if (userSesion == null || !userSesion.getDni().equals(dni)) {
            return "redirect:/login";
        }
        model.addAttribute("obj", oviUserDao.getOviUser(dni));
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

        oviUserDao.updatePreferencias(oviUser.getDni(), oviUser.getUserPreferences());
        return "redirect:/Ovi_User/menu";
    }
}
