package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AssistanceRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Assistance_Request.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        Assistance_Request req = (Assistance_Request) obj;
        if (req.getDescription().trim().isEmpty())
            errors.rejectValue("description", "required",
                    "Es necesario introducir el motivo de la asistencia");
        if(req.getDescription().length() >= 250)
            errors.rejectValue("description", "length0",
                    "El motivo es demasiado largo");
        if(req.getCity() == null || req.getCity().isEmpty())
            errors.rejectValue("city","required","Debes introducir una ciudad");
        if(req.getProvince() == null || req.getProvince().isEmpty())
            errors.rejectValue("province","required","Debes introducir una provincia");
        if(req.getShiftPreference() == null)
            errors.rejectValue("shiftPreference","required","Debes introducir una preferencia de horario");

        if(req.getCity() != null && req.getCity().length()> 50)
            errors.rejectValue("city","required","El nombre de la ciudad no puede ser mayor a 50 caracteres");
        if(req.getProvince() != null && req.getProvince().length()>50)
            errors.rejectValue("province","required","El nombre de la provincia no puede ser mayor a 50 caracteres");
        if((req.getReason() != null) && !req.getState().name().equals("REJECTED")){
            errors.rejectValue("reason", "required", "Para un solicitud ACEPTADA no hay razón");
        }
        if(( req.getReason() == null) && req.getState().name().equals("REJECTED")){
            errors.rejectValue("reason", "required", "Es necesario incluir una razón de denegación");
        }
    }
}

