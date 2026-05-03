package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PapPatiPreferencesValidator implements Validator {

    private PapPati originalPapPati;

    public PapPatiPreferencesValidator(PapPati originalPapPati) {
        this.originalPapPati = originalPapPati;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PapPati.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PapPati newPapPati = (PapPati) target;
        //Para comprobar que no está vacío el turno
        if (newPapPati.getShift() == null) {
            errors.rejectValue("shift", "obligatorio", "Debes seleccionar un turno de preferencia.");
            return; // Paramos aquí si es nulo para evitar fallos
        }

        //Comprobacion de que se ha seleccionado otro turno o se ha cambiado el carnet de conducir
        if (originalPapPati != null) {
            boolean mismoTurno = newPapPati.getShift().equals(originalPapPati.getShift());
            boolean mismoCarnet = newPapPati.getDrivingLicense() == originalPapPati.getDrivingLicense();

            // Si ni el turno ni el carnet han cambiado, lanzamos error
            if (mismoTurno && mismoCarnet) {
                errors.rejectValue("shift", "igual", "Debes seleccionar un turno distinto o cambiar el estado del carnet para guardar.");
            }
        }
    }
}