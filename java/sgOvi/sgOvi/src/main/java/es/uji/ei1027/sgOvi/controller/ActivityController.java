package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.ActivityDao;
import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping("/list")
    public String listActivities(Model model, HttpSession session){

        Person user = (Person) session.getAttribute("user");

        List<Activity> myActivities = activityDao.getUserActivities(user.getDni());
        model.addAttribute("activities", myActivities);

        return "Activity/list";
    }
    @RequestMapping("/listAll")
    public String listAllActivities(Model model, HttpSession session) {

        model.addAttribute("activities", activityDao.getActivities());

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
}