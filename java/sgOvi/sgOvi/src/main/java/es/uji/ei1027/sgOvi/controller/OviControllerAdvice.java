package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class OviControllerAdvice {
    @ExceptionHandler(value = OviException.class)
    public ModelAndView handleClubesportiuEx(OviException ex){

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorName", ex.getErrorName());
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}
