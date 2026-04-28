package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.PapPati; // Ajusta el nombre de la clase según tu modelo
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PapPatiPreferencesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PapPati.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PapPati papPati = (PapPati) target;

        // Fíjate que aquí usamos el nombre del campo de tu diseño lógico: papPatiPreferences
        if (papPati.getPapPatiPreferences() == null || papPati.getPapPatiPreferences().trim().isEmpty()) {
            errors.rejectValue("papPatiPreferences", "required", "Debes introducir tus preferencias de asistente");
        }

        if (papPati.getPapPatiPreferences().length() > 250) {
            errors.rejectValue("papPatiPreferences", "tooLong", "Las preferencias no pueden superar los 250 caracteres");
        }
    }
}