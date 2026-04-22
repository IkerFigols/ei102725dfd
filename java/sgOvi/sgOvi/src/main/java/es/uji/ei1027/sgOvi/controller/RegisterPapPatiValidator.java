package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class RegisterPapPatiValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return PapPati.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        PapPati papPati = (PapPati) obj;

        if(papPati.getDni() == null || papPati.getDni().length() != 9)
            errors.rejectValue("dni", "required",
                    "Es necesario introducir el dni");
        if (!papPati.getDni().matches("^\\d{8}[A-Z]$"))
            errors.rejectValue("dni", "format",
                    "El DNI debe tener exactamente 8 números y 1 letra mayúscula");
        if(papPati.getAddress() == null || papPati.getAddress().isEmpty() || papPati.getAddress().length() > 100)
            errors.rejectValue("address", "required",
                    "Es necesario introducir la dirección en menos de 100 caracteres");
        if(papPati.getPapPatiPreferences() == null || papPati.getPapPatiPreferences().isEmpty() || papPati.getPapPatiPreferences().length() > 250)
            errors.rejectValue("papPatiPreferences", "required",
                    "Es necesario introducir las preferencias del asistente y no debe ser mayor a 250 caracteres");
        if(papPati.getTraining() == null || papPati.getTraining().isEmpty() || papPati.getTraining().length() > 200)
            errors.rejectValue("training", "required",
                    "Es necesario introducir las especialidades del asistente y no debe ser mayor a 200 caracteres");
        if(papPati.getDocument() == null || papPati.getDocument().isEmpty() || papPati.getDocument().length() > 150)
            errors.rejectValue("document", "required",
                    "Es necesario introducir el enlace de los documentos del asistente");
        if(papPati.getType() == null || papPati.getType().isEmpty())
            errors.rejectValue("type", "required",
                    "Es necesario introducir el tipo de asistente");
        if(papPati.getDni() != null && papPati.getDni().length() > 9 )
            errors.rejectValue("dni","required","El dni debe tener como máximo 9 caracteres");

        if(papPati.getAddress() != null && papPati.getAddress().length() > 50 )
            errors.rejectValue("address","required","La dirección debe tener como máximo 50 caracteres");
        if(papPati.getTraining() != null && papPati.getTraining().length() > 200 )
            errors.rejectValue("training","required","Las especialidades deben tener como máximo 200 caracteres");
        if(papPati.getDocument() != null && papPati.getDocument().length() > 100 )
            errors.rejectValue("document","required","El enlace al documento debe tener como máximo 100 caracteres");



    }
}

