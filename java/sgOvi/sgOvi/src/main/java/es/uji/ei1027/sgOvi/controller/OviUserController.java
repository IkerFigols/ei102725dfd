package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/oviUser")
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
        return "oviUser/list";
    }
    @RequestMapping(value="/add")
    public String addOvisUser(Model model) {
        model.addAttribute("oviUser", new Ovi_User());
        return "oviUser/add";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviUser") Ovi_User oviUser,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "oviUser/add";
        oviUserDao.addOviUser(oviUser);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable String dni) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(dni));
        return "oviUser/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oviUser") Ovi_User oviUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "oviUser/update";
        oviUserDao.updateOviUser(oviUser);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        oviUserDao.deleteOviUser(dni);
        return "redirect:../list";
    }

}
