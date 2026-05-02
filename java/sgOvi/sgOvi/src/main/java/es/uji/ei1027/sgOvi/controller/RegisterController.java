package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        personDao.addPerson(person);
        if ("OviUser".equals(person.getPreference())) {
            return "redirect:/Register/registerOviUser?dni="+person.getDni()+"&birthdayDate="+person.getBirthdayDate();
        } else {
            return "redirect:/Register/registerPapPati?dni="+person.getDni();
        }
    }

    @GetMapping("/registerOviUser")
    public String showRegisterOviUser(@RequestParam("dni") String dni, @RequestParam("birthdayDate") LocalDate date, Model model) {
        OviUser oviUser = new OviUser();
        oviUser.setDni(dni);
        oviUser.setBirthdayDate(date);
        model.addAttribute("oviuser", oviUser);
        return "Register/registerOviUser";
    }
    @RequestMapping(value="/registerOviUser", method = RequestMethod.POST)
    public String processOviSubmit(@ModelAttribute("oviuser") OviUser oviUser,
                                   BindingResult bindingResult) {
        RegisterOviValidator registerOviValidator = new RegisterOviValidator();
        registerOviValidator.validate(oviUser,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerOviUser";
        }
        oviUserDao.addOviUser(oviUser);
        return "redirect:/";
    }

    @GetMapping("/registerPapPati")
    public String showRegisterPapPati(@RequestParam("dni") String dni, Model model) {
        PapPati papPati = new PapPati();
        papPati.setDni(dni);

        model.addAttribute("pappati", papPati);
        return "Register/registerPapPati";
    }
    @RequestMapping(value="/registerPapPati", method = RequestMethod.POST)
    public String processPapPatisubmit(@ModelAttribute("pappati") PapPati papPati,
                                       BindingResult bindingResult) {
        PapPatiValidator papPatiValidator = new PapPatiValidator();
        papPatiValidator.validate(papPati,bindingResult);
        if (bindingResult.hasErrors()) {
            return "Register/registerPapPati";
        }
        papPatiDao.addPapPati(papPati);
        return "redirect:/";
    }
}
