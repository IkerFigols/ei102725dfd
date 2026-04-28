package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.OviUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OviUserPreferencesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OviUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OviUser oviUser = (OviUser) target;

        if (oviUser.getUserPreferences() == null || oviUser.getUserPreferences().trim().isEmpty()) {
            errors.rejectValue("userPreferences", "required", "Debes introducir tus preferencias");
        }

        if (oviUser.getUserPreferences().length() > 250) {
            errors.rejectValue("userPreferences", "tooLong", "Las preferencias no pueden superar los 250 caracteres");
        }
    }
}