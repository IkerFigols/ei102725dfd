package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class controller {

    @Autowired
    PersonDao personDao;


    @RequestMapping(value="/si", method=RequestMethod.POST)
    public String registrarPersonas(@ModelAttribute("person") Person person,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "si";
        }

        personDao.encryptPersons();
        System.out.println("se ha realizado el cambio");
        return "redirect:/";
    }

    @RequestMapping(value="/si")
    public String loginPage(Model model) {
        model.addAttribute("person", new Person());
        return "si";
    }
}