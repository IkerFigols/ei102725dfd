package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PapPatiValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return PapPati.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        PapPati papPati = (PapPati) obj;

        if(papPati.getAddress() == null || papPati.getAddress().isBlank() || papPati.getAddress().length() > 100)
            errors.rejectValue("address", "required",
                    "Es necesario introducir la dirección en menos de 100 caracteres");
        if(papPati.getTraining() == null || papPati.getTraining().isBlank())
            errors.rejectValue("training", "required",
                    "Es necesario introducir las especialidades del asistente y no debe ser mayor a 200 caracteres");
        if(papPati.getDocument() == null || papPati.getDocument().isBlank())
            errors.rejectValue("document", "required",
                    "Es necesario introducir el enlace de los documentos del asistente");
        if(papPati.getType() == null)
            errors.rejectValue("type", "required",
                    "Es necesario introducir el tipo de asistente");

        if(papPati.getExperience() == null)
            errors.rejectValue("experience","required","Es necesario introducir los años de experiencia");

        if(papPati.getShift() == null){
            errors.rejectValue("shift","required","Es necesario introducir en que horario puedes trabajar");
        }
        if(papPati.getTraining() != null && papPati.getTraining().length() > 200 )
            errors.rejectValue("training","required","Las especialidades deben tener como máximo 200 caracteres");
        if(papPati.getDocument() != null && papPati.getDocument().length() > 100 )
            errors.rejectValue("document","required","El enlace al documento debe tener como máximo 100 caracteres");
        if(papPati.getState().name().equals("REJECTED") && papPati.getAvailable()){
            errors.rejectValue("available", "invalid", "Un PAP/PATI rechazado no puede estar marcado como disponible" );
        }
        if(!papPati.getState().name().equals("REJECTED") && !papPati.getReason().isEmpty()){
            errors.rejectValue("reason", "required", "Para un PAP/PATI ACEPTADO o PENDIENTE no hay razón");
        }
        if(papPati.getState().name().equals("REJECTED") && papPati.getReason().isEmpty()){
            errors.rejectValue("reason", "required", "Es necesario incluir una razón de denegación");
        }
        if (papPati.getReason().length() > 250){
            errors.rejectValue("reason", "length", "Reason no puede tener mas de 250 carácteres");
        }




    }
}

