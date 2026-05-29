package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.service.Services.OviUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


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
