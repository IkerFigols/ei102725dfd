package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


public class OviUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return OviUser.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        OviUser user = (OviUser) obj;
        if(user.getAddress().trim().isEmpty()){
            errors.rejectValue("address", "required", "Es necesario añadir una dirección");
        }
        if(!user.getState().name().equals("REJECTED") && (user.getReason() != null && !user.getReason().isEmpty())){
            errors.rejectValue("reason", "required", "Para un usuario ACEPTADO o PENDIENTE no hay razón");
        }
        if(user.getState().name().equals("REJECTED") && (user.getReason() == null || user.getReason().isEmpty())){
            errors.rejectValue("reason", "required", "Es necesario incluir una razón de denegación");
        }
        if (user.getReason() != null && user.getReason().length() > 250){
            errors.rejectValue("reason", "length", "Reason no puede tener mas de 250 carácteres");
        }
        if (user.getAddress().length() > 100){

            errors.rejectValue("address", "length", "La Dirección no puede ser mayor a 100 carácteres");
        }
    }

}
