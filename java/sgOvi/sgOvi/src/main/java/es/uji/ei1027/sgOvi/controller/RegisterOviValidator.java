package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.OviUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RegisterOviValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return OviUser.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {

        OviUser user = (OviUser) obj;
        if (user.getBirthdayDate() != null) {
            long edad = ChronoUnit.YEARS.between(user.getBirthdayDate(), LocalDate.now());
            if (edad < 18 && (user.getLegalGuardian() == null || user.getLegalGuardian().isEmpty()))
                errors.rejectValue("legalGuardian", "required",
                        "Es necesario introducir el nombre del tutor legal del menor");
        }
        if (user.getAddress() == null || user.getAddress().isEmpty() || user.getAddress().length() > 50)
            errors.rejectValue("address", "required",
                    "Es necesario introducir la dirección en menos de 50 caracteres");

        if (user.getLegalGuardian() != null && user.getLegalGuardian().length() > 50)
            errors.rejectValue("legalGuardian", "required", "El nombre del tutor legal debe tener como máximo 50 caracteres");
    }
}

