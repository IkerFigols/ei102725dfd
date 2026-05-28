package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.service.DTOs.PersonDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UpdateProfileValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return PersonDTO.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        PersonDTO personDTO = (PersonDTO) obj;
        PersonValidator personValidator = new PersonValidator();
        errors.pushNestedPath("person");
        personValidator.validate(personDTO.getPerson(),errors);
        errors.popNestedPath();


        if(personDTO.getPapPati() != null){
            errors.pushNestedPath("papPati");
            PapPatiValidator papPatiValidator = new PapPatiValidator();
            papPatiValidator.validate(personDTO.getPapPati(),errors);
            errors.popNestedPath();
        }
        if(personDTO.getOviUser() != null){
            errors.pushNestedPath("oviUser");
            OviUserValidator oviUserValidator = new OviUserValidator();
            oviUserValidator.validate(personDTO.getOviUser(),errors);
            errors.popNestedPath();
        }
        if(personDTO.getInstructor() != null){
            errors.pushNestedPath("instructor");
             InstructorValidator instructorValidator = new InstructorValidator();
            instructorValidator.validate(personDTO.getInstructor(),errors);
            errors.popNestedPath();
        }
    }
}

