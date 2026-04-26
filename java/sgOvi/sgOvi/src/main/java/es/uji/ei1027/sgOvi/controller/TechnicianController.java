package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.TechnicianDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Technician")
public class TechnicianController {

    private TechnicianDao technicianDao;
    private OviUserDao oviUserDao;


    @Autowired
    public void setTechnicianDao(TechnicianDao technicianDao) {
        this.technicianDao=technicianDao;
    }
    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao){this.oviUserDao=oviUserDao; }
    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/menuTechnician")
    public String menuTechnician(Model model) {
        return "Technician/menuTechnician";
    }
    @RequestMapping("/userList")
    public String listOviUsers(Model model) {
        model.addAttribute("users", oviUserDao.getOviUsers());
        return "Technician/userList";
    }
    @RequestMapping(value="/add")
    public String addOviUser(Model model) {
        model.addAttribute("oviUser", new OviUser());
        return "Ovi_User/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviUser") OviUser oviUser,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Ovi_User/add";
        oviUserDao.addOviUser(oviUser);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable String dni) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(dni));
        return "Ovi_User/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oviUser") OviUser oviUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Ovi_User/update";
        oviUserDao.updateOviUser(oviUser);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        oviUserDao.deleteOviUser(dni);
        return "redirect:../list";
    }

}
