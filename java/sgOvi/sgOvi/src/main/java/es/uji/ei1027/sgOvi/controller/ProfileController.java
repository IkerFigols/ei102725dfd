package es.uji.ei1027.sgOvi.controller;
import es.uji.ei1027.sgOvi.service.DTOs.PersonDTO;
import es.uji.ei1027.sgOvi.service.Services.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        }
        return "profile";
    }


    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public String proccessProfile(@ModelAttribute("dto") PersonDTO dto, BindingResult bindingResult, HttpSession session, RedirectAttributes flash){
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
        if(bindingResult.hasErrors())
            return "profile";

        String rol = (String) session.getAttribute("rol");

        profileService.updatePerson(dto.getPerson());

        switch (rol) {
            case ("OVI_USER") -> {
                profileService.updateOviUser(dto.getOviUser());
            }

            case ("PAP_PATI") -> {
                profileService.updatePapPati(dto.getPapPati());
            }
        }
        session.setAttribute("user", dto.getPerson());
        flash.addFlashAttribute("lista","/profile");
        flash.addFlashAttribute("mensaje", "Se han actualizado los datos del perfil correctamente");
        return "redirect:/actionConfirmation";
    }

}
