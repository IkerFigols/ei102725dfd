package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.enums.ShiftType;
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

        if (papPati.getShift() == null) {
            errors.rejectValue("shift", "obligatorio", "Debes seleccionar un turno de preferencia.");
        }
    }
}