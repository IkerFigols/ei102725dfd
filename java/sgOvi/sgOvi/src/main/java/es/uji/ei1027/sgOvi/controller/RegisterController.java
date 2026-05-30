package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/Register")
@SessionAttributes("person")
public class RegisterController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private OviUserDao oviUserDao;

    @Autowired
    private PapPatiDao papPatiDao;

    @ModelAttribute("person")
    public Person setUpPerson(){
        return new Person();
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String registerPerson(@ModelAttribute Person person,Model model){
        return "Register/register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String proccesAndSubmit(@ModelAttribute("person") Person person,
                                   BindingResult bindingResult,
                                   Model model){
        PersonValidator personValidator = new PersonValidator();
        personValidator.validate(person,bindingResult);
        List<Person> persons = personDao.getPersons();
        if (personDao.getPerson(person.getDni()) != null) {
            bindingResult.rejectValue("dni", "exists", "Este DNI ya está registrado");
            return "Register/register";
        }
        if (personDao.getPersonByEmail(person.getEmail()) != null){
            bindingResult.rejectValue("email", "exists", "Este email ya está registrado");
            return "Register/register";
        }

        if (bindingResult.hasErrors()) {
            return "Register/register";
        }
        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        String passwordEncrypted = encryptor.encryptPassword(person.getPassword());
        person.setPassword(passwordEncrypted);
        if ("OviUser".equals(person.getPreference())) {
            return "redirect:/Register/registerOviUser";
        } else {
            return "redirect:/Register/registerPapPati";
        }
    }

    @GetMapping("/registerOviUser")
    public String showRegisterOviUser(@ModelAttribute("person") Person person, Model model) {
        OviUser oviUser = new OviUser();
        oviUser.setDni(person.getDni());
        oviUser.setBirthdayDate(person.getBirthdayDate());
        model.addAttribute("oviuser", oviUser);
        return "Register/registerOviUser";
    }
    @RequestMapping(value="/registerOviUser", method = RequestMethod.POST)
    public String processOviSubmit(@ModelAttribute("oviuser") OviUser oviUser,
                                   BindingResult bindingResult,
                                   SessionStatus sessionStatus, HttpSession session) {
        RegisterOviValidator registerOviValidator = new RegisterOviValidator();
        registerOviValidator.validate(oviUser,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerOviUser";
        }
        Person person = (Person) session.getAttribute("person");
        personDao.addPerson(person);
        oviUserDao.addOviUser(oviUser);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/registerPapPati")
    public String showRegisterPapPati(@ModelAttribute("person")Person person, Model model) {
        PapPati papPati = new PapPati();
        papPati.setDni(person.getDni());

        model.addAttribute("pappati", papPati);
        return "Register/registerPapPati";
    }
    @RequestMapping(value="/registerPapPati", method = RequestMethod.POST)
    public String processPapPatisubmit(@ModelAttribute("pappati") PapPati papPati,
                                       BindingResult bindingResult,
                                       SessionStatus sessionStatus,
                                       HttpSession session) {
        PapPatiValidator papPatiValidator = new PapPatiValidator();
        papPatiValidator.validate(papPati,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerPapPati";
        }
        Person person = (Person) session.getAttribute("person");
        personDao.addPerson(person);
        papPatiDao.addPapPati(papPati);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
