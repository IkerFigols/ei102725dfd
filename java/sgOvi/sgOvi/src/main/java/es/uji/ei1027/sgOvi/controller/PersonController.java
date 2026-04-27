package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/Person")
public class PersonController {

    private PersonDao personDao;


    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao=personDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar
    // ...
    @RequestMapping("/list")
    public String listPersons(Model model) {
        model.addAttribute("persons", personDao.getPersons());
        return "Person/list";
    }
    @RequestMapping(value="/add")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "Person/add";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("person") Person person,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Person/add";
        personDao.addPerson(person);
        return "redirect:list";
    }
    @RequestMapping(value="/update/{dni}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable String dni) {
        model.addAttribute("person", personDao.getPerson(dni));
        return "Person/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Person/update";
        personDao.updatePerson(person);
        return "redirect:/Person/list";
    }
    @RequestMapping(value="/delete/{dni}")
    public String processDelete(@PathVariable String dni) {
        personDao.deletePerson(dni);
        return "redirect:../list";
    }

}
