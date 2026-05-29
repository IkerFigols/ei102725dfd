package es.uji.ei1027.sgOvi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    @RequestMapping("/menuIndex")
    public String getMenu(){
        return "redirect:/";
    }

    @RequestMapping("/miAreaPersonal")
    public String getAreaPersonal(HttpSession session){
        String role = (String) session.getAttribute("rol");
        if (role == null)
            return "redirect:/login";
        switch (role){
            case "OVI_USER" -> {
                return "redirect:/Ovi_User/menuOviUser";
            }
            case "PAP_PATI" -> {
                return "redirect:/Pap_Pati/menuPapPati";
            }
            case "TECHNICIAN" -> {
                return "redirect:/Technician/menuTechnician";
            }
            case "INSTRUCTOR" -> {
                return "redirect:/Instructor/menuInstructor"; // no implementado
            }
            default -> {
                return "redirect:/";
            }
        }
    }

    @RequestMapping("/actionConfirmation")
    public String mostrarConfirmacion() {
        return "actionConfirmation";
    }

}
