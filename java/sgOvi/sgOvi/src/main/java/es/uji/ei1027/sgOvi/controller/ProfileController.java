package es.uji.ei1027.sgOvi.controller;
import es.uji.ei1027.sgOvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.SequencedSet;

@Controller
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @RequestMapping("/profile")
    public String getProfile(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("user");
        PersonDTO dto = new PersonDTO();
        String rol = (String) session.getAttribute("rol");
        person.setPreference(rol);
        person.setDataProtection(true);
        switch (rol){
            case("OVI_USER") -> {
                dto.setOviUser(profileService.getOviUser(person.getDni()));
                dto.setPerson(person);
                model.addAttribute("urlBefore","/Ovi_User/menuOviUser");
                model.addAttribute("dto",dto);
            }
            case("PAP_PATI") ->{

                dto.setPapPati(profileService.getPapPati(person.getDni()));
                dto.setPerson(person);
                model.addAttribute("urlBefore","/Pap_Pati/menuPapPati");
                model.addAttribute("dto",dto);
            }
            case("TECHNICIAN") -> {

                dto.setTechnician(profileService.getTechnician(person.getDni()));
                dto.setPerson(person);
                model.addAttribute("urlBefore","/Technician/menuTechnician");
                model.addAttribute("dto",dto);
            }
            case("INSTRUCTOR") ->{
                dto.setInstructor(profileService.getInstructor(person.getDni()));
                dto.setPerson(person);
                model.addAttribute("urlBefore","/Instructor/menuInstructor");
                model.addAttribute("dto",dto);
            }
        }
        return "profile";
    }


    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public String proccessProfile(@ModelAttribute("dto") PersonDTO dto, BindingResult bindingResult, HttpSession session){
        UpdateProfileValidator validator = new UpdateProfileValidator();
        validator.validate(dto,bindingResult);
        BasicPasswordEncryptor basicPasswordEncryptor = new BasicPasswordEncryptor();

        if (dto.getPerson().getPastPassword() != null && !dto.getPerson().getPastPassword().isBlank() &&
                dto.getPerson().getNewPassword() != null && !dto.getPerson().getNewPassword().isBlank()) {

            if (basicPasswordEncryptor.checkPassword(dto.getPerson().getPastPassword(), dto.getPerson().getPassword())) {
                dto.getPerson().setPassword(basicPasswordEncryptor.encryptPassword(dto.getPerson().getNewPassword()));
            } else {
                bindingResult.rejectValue("person.pastPassword", "required", "Contraseña incorrecta");
            }
        }
        System.out.println("Estoy antes del binding");
        System.out.println(dto.getPerson().toString());
        System.out.println(dto.getPapPati().toString());
        if(bindingResult.hasErrors())
            return "profile";
        System.out.println("Pase el binding");
        String rol = (String) session.getAttribute("rol");
        System.out.println("----VOY A ACTUALIZAR----");
        System.out.println(dto.getPerson().toString());
        profileService.updatePerson(dto.getPerson());

        switch (rol) {
            case ("OVI_USER") -> {
                System.out.println("----PASE POR OVI USER----");
                System.out.println(dto.getOviUser().toString());
                profileService.updateOviUser(dto.getOviUser());
            }

            case ("PAP_PATI") -> {
                profileService.updatePapPati(dto.getPapPati());
            }
            case ("TECHNICIAN") -> {
                profileService.updateTechnician(dto.getTechnician());
            }
            case ("INSTRUCTOR") -> {
                profileService.updateInstructor(dto.getInstructor());
            }

        }
        session.setAttribute("user", dto.getPerson());
        return "redirect:/profile";
    }

}
