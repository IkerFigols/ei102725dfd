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
            errors.rejectValue("description", "length",
                    "El motivo es demasiado largo");
        if (req.getIdOviUser().length() < 9)
            errors.rejectValue("idOviUser", "required", "El DNI introducido no es correcto");
        if (!req.getIdOviUser().matches("^\\d{8}[A-Z]$"))
            errors.rejectValue("idOviUser", "format",
                    "El DNI debe tener exactamente 8 números y 1 letra mayúscula");
    }
}

