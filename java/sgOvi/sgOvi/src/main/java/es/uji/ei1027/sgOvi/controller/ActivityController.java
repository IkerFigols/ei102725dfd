package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.enums.ActivityType;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.service.Services.ActivityAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    private ActivityAttendanceService activityAttendanceService;



    @RequestMapping("/list")
    public String listActivities(Model model, HttpSession session,
                                 @RequestParam (value="sort", defaultValue = "dateDesc") String sort){

        Person user = (Person) session.getAttribute("user");

        List<Activity> myActivities = activityAttendanceService.getUserActivities(user.getDni());

        myActivities = activityAttendanceService.sortActivities(myActivities, sort);

        model.addAttribute("activities", myActivities);
        model.addAttribute("currentSort", sort);

        return "Activity/list";
    }
    @RequestMapping("/listAll")
    public String listAllActivities(Model model, HttpSession session,
                                    @RequestParam (value="sort", defaultValue = "dateDesc") String sort) {

        List<Activity> activities = activityAttendanceService.getActivities();

        activities = activityAttendanceService.sortActivities(activities, sort);


        model.addAttribute("activities", activities);
        model.addAttribute("currentSort", sort);

        return "Activity/listAll";
    }

    @RequestMapping("/someActivities")
    public String listSomeActivities(Model model){
        model.addAttribute("activities", activityAttendanceService.getRecentActivities());
        return "Activity/someActivities";
    }

    @RequestMapping("/infoActivity/{id}")
    public String infoActivity(@PathVariable("id") String id, Model model) {
        model.addAttribute("activity", activityAttendanceService.getActivity(id));
        return "Activity/infoActivity";
    }
    @RequestMapping("/inscription/{id}")
    public String inscriptionActivity(@PathVariable("id") String id, Model model ) {

        model.addAttribute("activity", activityAttendanceService.getActivity(id));

        return "Activity/inscription";
    }
    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public String processInscription(@RequestParam("idActivity") String idActivity,
                                     HttpSession session, RedirectAttributes flash) {
        Person user = (Person) session.getAttribute("user");
        String rol = (String) session.getAttribute("rol");
        if(activityAttendanceService.getActivity(idActivity) == null)
            throw new OviException("No existe la actividad", "Actividad no encontrada");
        if(activityAttendanceService.isSuscribed(idActivity, user.getDni())) {
            throw new OviException("Ya estas inscrito a esta actividad", "Acción no completada");
        }
        Integer capacidad = (activityAttendanceService.getActivity(idActivity).getCapacity());

        if(capacidad != null && activityAttendanceService.getAttendancesFromActivity(idActivity).size() >= capacidad)
            throw new OviException("No se puede inscribir a la actividad ya que la capacidad esta al máximo", "Acción no completada");
        if(activityAttendanceService.getActivity(idActivity).getType() == ActivityType.TRAINING && rol.equals(RolUser.OVI_USER.name()))
            throw new OviException("Un usuario OVI no se puede inscribir a una actividad de formación, son exclusivas para asistentes personales","Acción no completada");

        activityAttendanceService.registerUserToActivity(idActivity, user.getDni(), rol);

        flash.addFlashAttribute("lista","/Activity/listAll");
        flash.addFlashAttribute("mensaje","Te has inscrito a la actividad correctamente");
        return "redirect:/actionConfirmation";

    }
    @RequestMapping("/unsubscription/{id}")
    public String unsubscriptionActivity(@PathVariable("id") String id, Model model, HttpSession session) {
        Person user = (Person) session.getAttribute("user");
        if(activityAttendanceService.getActivity(id) == null)
            throw new OviException("No existe la actividad con id: "+ id, "Acción no completada" );
        if(!activityAttendanceService.isSuscribed(id, user.getDni()))
            throw  new OviException("No te puedes desuscribir de la actividad ya que no estas suscrito a ella", "Acción no completada");
        model.addAttribute("activity", activityAttendanceService.getActivity(id));
        return "Activity/unsubscription";
    }

    @RequestMapping(value = "/unsubscription", method = RequestMethod.POST)
    public String processUnsubscription(@RequestParam("idActivity") String idActivity,
                                        HttpSession session, RedirectAttributes flash) {
        Person user = (Person) session.getAttribute("user");
        RolUser role = RolUser.fromString((String) session.getAttribute("rol"));

        activityAttendanceService.unregisterUserFromActivity(idActivity, user.getDni(), role);
        flash.addFlashAttribute("lista","/Activity/listAll");
        flash.addFlashAttribute("mensaje","Te has dado de baja de la actividad correctamente");
        return "redirect:/actionConfirmation";


    }
}