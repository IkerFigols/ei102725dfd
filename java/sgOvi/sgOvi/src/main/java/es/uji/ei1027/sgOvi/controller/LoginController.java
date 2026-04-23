package es.uji.ei1027.sgOvi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {

    @Autowired

    @RequestMapping("/login")
    public void validateLogin(){
        return;
    }
}



