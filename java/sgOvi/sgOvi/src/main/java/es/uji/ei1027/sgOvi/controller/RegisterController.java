package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Register")
public class RegisterController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private OviUserDao oviUserDao;

    @Autowired
    private PapPatiDao papPatiDao;

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String registerPerson(Model model){
        model.addAttribute("person", new Person());
        return "Register/register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String proccesAndSubmit(@ModelAttribute("person") Person person,
                                   BindingResult bindingResult,
                                   Model model){
        RegisterValidator registerValidator = new RegisterValidator();
        registerValidator.validate(person,bindingResult);
        List<Person> persons = personDao.getPersons();
        for( Person persona: persons){
            if(persona.getDni().equals(person.getDni())) {
                //bindingResult.addError(new ObjectError("dni","Este dni ya existe"));
                bindingResult.rejectValue("dni","required","Este dni ya existe");
                return "Register/register";
            }
        }
            if (bindingResult.hasErrors()) {
                return "Register/register";
            }
        personDao.addPerson(person);
        if ("OviUser".equals(person.getPreference())) {
            Ovi_User oviUser = new Ovi_User();
            oviUser.setDni(person.getDni());
            model.addAttribute("oviuser", oviUser);
            return "Register/registerOviUser";
        } else {
            PapPati papPati = new PapPati();
            papPati.setDni(person.getDni());
            model.addAttribute("pappati", papPati);
            return "Register/registerPapPati";
        }
    }

    @RequestMapping(value="/registerOviUser", method = RequestMethod.POST)
    public String processOviSubmit(@ModelAttribute("oviuser") Ovi_User oviUser,
                                   BindingResult bindingResult) {
        RegisterOviValidator registerOviValidator = new RegisterOviValidator();
        registerOviValidator.validate(oviUser,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerOviUser";
        }
        oviUserDao.addOviUser(oviUser);
        return "redirect:/";
    }
    @RequestMapping(value="/registerPapPati", method = RequestMethod.POST)
    public String processOviSubmit(@ModelAttribute("pappati") PapPati papPati,
                                   BindingResult bindingResult) {
        RegisterPapPatiValidator registerPapPatiValidator = new RegisterPapPatiValidator();
        registerPapPatiValidator.validate(papPati,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerPapPati";
        }
        papPatiDao.addPapPati(papPati);
        return "redirect:/";
    }
}
