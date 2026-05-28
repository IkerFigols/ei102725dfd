package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.Services.OviUserService;
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

    @Autowired
    OviUserService oviUserService;

    @RequestMapping("/menuOviUser")
    public String menu(HttpSession session, Model model) {
        return "Ovi_User/menuOviUser";
    }

}
