package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Activity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.sgOvi.model.enums.ActivityType;

import java.time.LocalDate;

public class ActivityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Activity activity = (Activity) target;

        if(activity.getTittle().length() > 100){
            errors.rejectValue("tittle", "length", "Debe incluirse un titulo de actividad de no más de 100 carácteres");
        }
        if(activity.getType().name().equals(ActivityType.DISSEMINATION.name()) && activity.getCapacity() == null)
            errors.rejectValue("capacity", "invalid", "Las actividades de Diseminacion tienen limite de aforo");
        if(activity.getType().name().equals(ActivityType.TRAINING.name()) && activity.getCapacity() != null){
            errors.rejectValue("capacity", "invalid", "Las actividades de Formación tienen no un limite de aforo");
        }
        if(activity.getDescription().length() > 300){
            errors.rejectValue("description", "length", "La descripción no puede ser mas larga que 300 carácteres");
        }
        if(activity.getDate().isBefore(LocalDate.now())){
            errors.rejectValue("date", "invalid", "No se puede añadir una actividad a un dia que ya ha pasado");
        }
        if(activity.getAddress().length() > 100){
            errors.rejectValue("address", "length", "La dirección no puede ser mas larga que 100 carácteres");
        }
        if(activity.getCapacity() != null && activity.getCapacity() < 1){
            errors.rejectValue("capacity", "invalid", "La capacidad no puede ser menor que 1");
        }
        if( activity.getSponsor() != null && activity.getSponsor().length() > 100){
            errors.rejectValue("sponsor", "length", "El sponsor no puede ser mas largo que 100 carácteres");
        }
    }
}
