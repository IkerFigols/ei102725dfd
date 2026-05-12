package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.InstructorDao;
import es.uji.ei1027.sgOvi.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InstructorValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Instructor instructor = (Instructor) obj;
        if( instructor.getExpertise().length() > 100){
            errors.rejectValue("expertise", "length", "La especialidad no puede superar los 100 carácteres");
        }
    }
}
