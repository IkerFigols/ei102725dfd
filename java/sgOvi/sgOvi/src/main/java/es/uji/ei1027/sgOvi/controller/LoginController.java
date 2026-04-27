package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.UserDetails;
import es.uji.ei1027.sgOvi.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PersonDao personDao;

    @RequestMapping(value="/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String validateLogin(@ModelAttribute("user") UserDetails userDetails,
                                BindingResult bindingResult,
                                HttpSession session) {

        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(userDetails, bindingResult);

        if (bindingResult.hasErrors())
            return "login";

        Person person = personDao.getPerson(userDetails.getDni());
        if (person == null) {
            bindingResult.rejectValue("dni", "notFound", "El DNI introducido no existe");
            return "login";
        }

        String etiqueta = loginService.userValidator(userDetails.getDni(), userDetails.getPassword());
        if (etiqueta == null) {
            bindingResult.rejectValue("password", "badpw", "Contraseña incorrecta");
            return "login";
        }


        session.setAttribute("user", person);


        if (session.getAttribute("nextURL") != null ) {
            String nextURL = (String) session.getAttribute("nextURL");
            session.removeAttribute("nextURL");
            return "redirect:" + nextURL;
        }


        return switch (etiqueta) {
            case "Ovi_User"  -> "redirect:/Ovi_User/menuOviUser";
            case "Pap_Pati"  -> "redirect:/Pap_Pati/menuPapPati";
            case "Technician" -> "redirect:/Technician/menuTechnician";
            case "Instructor"  -> "redirect:/Instructor/menuInstructor";
            default     -> "redirect:/";
        };
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }
}