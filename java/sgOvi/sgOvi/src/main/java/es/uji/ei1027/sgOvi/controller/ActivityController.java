package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Activity")
public class ActivityController {

    @Autowired
    ActivityDao activityDao;

    @RequestMapping("/list")
    public String listRecentActivities(Model model){
        model.addAttribute("activities", activityDao.getRecentActivities());
        return "Activity/list";
    }
}
