package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.ActivityDao;
import es.uji.ei1027.sgOvi.model.Person; // O el modelo de usuario que uses
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping("/list")
    public String listActivities(Model model, HttpSession session){

        Person user = (Person) session.getAttribute("user");

        model.addAttribute("activities", activityDao.getRecentActivities());

        return "Activity/list";
    }
    @RequestMapping("/listAll")
    public String listAllActivities(Model model, HttpSession session) {

        model.addAttribute("activities", activityDao.getActivities());

        return "Activity/listAll";
    }
}