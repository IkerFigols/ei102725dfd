package es.uji.ei1027.sgOvi.controller;
import es.uji.ei1027.sgOvi.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @RequestMapping("/profile")
    public String getProfile(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("user");
        String rol = (String) session.getAttribute("rol");
        PersonDTO dto;
        switch (rol){
            case("OVI_USER") -> {
                PersonOviUserDTO personOviUserDTO = new PersonOviUserDTO();
                personOviUserDTO.setOviUser(profileService.getOviUser(person.getDni()));
                personOviUserDTO.setPerson(person);
                model.addAttribute("dto",personOviUserDTO);

            }
            case("PAP_PATI") ->{
                PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
                personPapPatiDTO.setPapPati(profileService.getPapPati(person.getDni()));
                personPapPatiDTO.setPerson(person);
                model.addAttribute("dto",personPapPatiDTO);
            }
            case("TECHNICIAN") -> {
                PersonTechnicianDTO personTechnicianDTO = new PersonTechnicianDTO();
                personTechnicianDTO.setTechnician(profileService.getTechnician(person.getDni()));
                personTechnicianDTO.setPerson(person);
                model.addAttribute("dto",personTechnicianDTO);
            }
            case("INSTRUCTOR") ->{
                PersonInstructorDTO personInstructorDTO = new PersonInstructorDTO();
                personInstructorDTO.setInstructor(profileService.getInstructor(person.getDni()));
                personInstructorDTO.setPerson(person);
                model.addAttribute("dto",personInstructorDTO);
            }
        }
        return "/profile";
    }

//implementar para actualizar resultadoa


}
