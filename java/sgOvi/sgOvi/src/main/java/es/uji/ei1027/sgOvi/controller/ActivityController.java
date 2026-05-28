package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.dao.ActivityDao;
import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import es.uji.ei1027.sgOvi.service.Services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Comparator;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private AttendanceService attendanceService;

    //Método auxiliar para la ordenación
    private List<Activity> sortActivities(List<Activity> activities, String sort) {
        if (sort == null) return activities;
        switch (sort) {
            case "dateAsc":
                activities.sort(Comparator.comparing(Activity::getDate));
                break;
            case "dateDesc":
                activities.sort(Comparator.comparing(Activity::getDate).reversed());
                break;
            case "titleAsc":
                activities.sort(Comparator.comparing(Activity::getTittle, String.CASE_INSENSITIVE_ORDER));
                break;
        }
        return activities;
    }

    @RequestMapping("/list")
    public String listActivities(Model model, HttpSession session,
                                 @RequestParam (value="sort", defaultValue = "dateDesc") String sort){

        Person user = (Person) session.getAttribute("user");

        List<Activity> myActivities = activityDao.getUserActivities(user.getDni());

        myActivities = sortActivities(myActivities, sort);

        model.addAttribute("activities", myActivities);
        model.addAttribute("currentSort", sort);

        return "Activity/list";
    }
    @RequestMapping("/listAll")
    public String listAllActivities(Model model, HttpSession session,
                                    @RequestParam (value="sort", defaultValue = "dateDesc") String sort) {

        List<Activity> activities = activityDao.getActivities();

        activities = sortActivities(activities, sort);


        model.addAttribute("activities", activities);
        model.addAttribute("currentSort", sort);

        return "Activity/listAll";
    }

    @RequestMapping("/someActivities")
    public String listSomeActivities(Model model){
        model.addAttribute("activities", activityDao.getRecentActivities());
        return "Activity/someActivities";
    }

    @RequestMapping("/infoActivity/{id}")
    public String infoActivity(@PathVariable("id") String id, Model model) {
        model.addAttribute("activity", activityDao.getActivity(id));
        return "Activity/infoActivity";
    }
    @RequestMapping("/inscription/{id}")
    public String inscriptionActivity(@PathVariable("id") String id, Model model) {

        model.addAttribute("activity", activityDao.getActivity(id));

        return "Activity/inscription";
    }
    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public String processInscription(@RequestParam("idActivity") String idActivity,
                                     HttpSession session) {
        Person user = (Person) session.getAttribute("user");
        String rol = (String) session.getAttribute("rol");

        try {
            attendanceService.registerUserToActivity(idActivity, user.getDni(), rol);
            return "redirect:/Activity/listAll?status=success";

        } catch (OviException e) {
            throw e;

        }
    }
    @RequestMapping("/unsubscription/{id}")
    public String unsubscriptionActivity(@PathVariable("id") String id, Model model) {
        model.addAttribute("activity", activityDao.getActivity(id));
        return "Activity/unsubscription";
    }

    @RequestMapping(value = "/unsubscription", method = RequestMethod.POST)
    public String processUnsubscription(@RequestParam("idActivity") String idActivity,
                                        HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        //NO se si esto funciona, se estaba usando una string en vez del enum
        RolUser role = RolUser.fromString ((String) session.getAttribute("rol"));

        try {
            attendanceService.unregisterUserFromActivity(idActivity, user.getDni(), role);
            return "redirect:/Activity/list?status=success_unsub";
        } catch (OviException e) {
            throw e;
        }
    }
}