package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;

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

    @Autowired
    private OviUserDao oviUserDao;

    @Autowired
    private PapPatiDao papPatiDao;

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

        session.setAttribute("rol",etiqueta);
        session.setAttribute("user", person);

        if (session.getAttribute("nextURL") != null ) {
            String nextURL = (String) session.getAttribute("nextURL");
            session.removeAttribute("nextURL");
            return "redirect:" + nextURL;
        }

        if(etiqueta.equals("OVI_USER")){
            OviUser oviUser = oviUserDao.getOviUser(person.getDni());
            if (oviUser.getState().name().equals("PENDING")) {
                bindingResult.rejectValue("dni","required","Tu solicitud sigue en revisión, el técnico no te ha aceptado todavia");
            }
            if (oviUser.getState().name().equals("REJECTED")) {
                bindingResult.rejectValue("dni","required","Tu solicitud ha sido rechazada, Razon: "+oviUser.getReason());
            }
        }
        //Para asegurarnos que es PAP_PATI
        if (etiqueta.equalsIgnoreCase("PAP") || etiqueta.equalsIgnoreCase("PATI") || etiqueta.equalsIgnoreCase("PAP_PATI")) {
            etiqueta = "PAP_PATI";
        }
        if(etiqueta.equals("PAP_PATI")){
            PapPati papPati = papPatiDao.getPapPati(person.getDni());
            if (papPati.getState().name().equals("PENDING")) {
                bindingResult.rejectValue("dni","required","Tu solicitud sigue en revisión, el técnico no te ha aceptado todavia");
            }
            if (papPati.getState().name().equals("REJECTED")) {
                bindingResult.rejectValue("dni","required","Tu solicitud ha sido rechazada, Razon: "+papPati.getReason());
            }

        }
        if (bindingResult.hasErrors())
            return "login";

        return switch (etiqueta) {
            case "OVI_USER"  -> "redirect:/Ovi_User/menuOviUser";
            case "PAP_PATI"  -> "redirect:/Pap_Pati/menuPapPati";
            case "TECHNICIAN" -> "redirect:/Technician/menuTechnician";
            case "INSTRUCTOR"  -> "redirect:/Instructor/menuInstructor";
            default     -> "redirect:/";
        };
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}