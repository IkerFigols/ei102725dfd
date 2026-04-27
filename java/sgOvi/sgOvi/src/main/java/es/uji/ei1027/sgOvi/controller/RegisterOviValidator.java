package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RegisterOviValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Ovi_User.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {


        Ovi_User user = (Ovi_User) obj;

        if(user.getDni() == null || user.getDni().length() != 9)
            errors.rejectValue("dni", "required",
                    "Es necesario introducir el dni");
        if (!user.getDni().matches("^\\d{8}[A-Z]$")) //incluir NIE
            errors.rejectValue("dni", "format",
                    "El DNI debe tener exactamente 8 números y 1 letra mayúscula");

        if(user.getBirthdayDate() == null)
            errors.rejectValue("birthdayDate", "required",
                    "Es necesario introducir la fecha");
        if(user.getBirthdayDate() != null) {
            long edad = ChronoUnit.YEARS.between(user.getBirthdayDate(), LocalDate.now());
            if (edad < 18 && (user.getLegalGuardian() == null || user.getLegalGuardian().isEmpty()))
                errors.rejectValue("legalGuardian", "required",
                        "Es necesario introducir el nombre del tutor legal del menor");
        }
        if(user.getAddress() == null || user.getAddress().isEmpty() || user.getAddress().length() > 50)
            errors.rejectValue("address", "required",
                    "Es necesario introducir la dirección en menos de 50 caracteres");
        if(user.getUserPreferences() == null || user.getUserPreferences().isEmpty() || user.getUserPreferences().length() > 250)
            errors.rejectValue("userPreferences", "required",
                    "Es necesario introducir las preferencias del usuario y no debe ser mayor a 250 caracteres");
        if(user.getDni() != null && user.getDni().length() > 9 )
            errors.rejectValue("dni","required","El dni debe tener como máximo 9 caracteres");

        if(user.getAddress() != null && user.getAddress().length() > 50 )
            errors.rejectValue("address","required","La dirección debe tener como máximo 50 caracteres");
        if(user.getLegalGuardian() != null && user.getLegalGuardian().length() > 50 )
            errors.rejectValue("legalGuardian","required","El nombre del tutor legal debe tener como máximo 50 caracteres");
        if(user.getUserPreferences() != null && user.getUserPreferences().length() > 250 )
            errors.rejectValue("userPreferences","required","Las preferencias del usuario deben tener como máximo 250 caracteres");
    }
}

