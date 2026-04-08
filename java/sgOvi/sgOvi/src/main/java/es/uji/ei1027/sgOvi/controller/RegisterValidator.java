package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Person.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        Person person = (Person) obj;

        if(person.getDni() == null || person.getDni().length() != 9)
            errors.rejectValue("dni", "required",
                    "Es necesario introducir el dni");
        if(person.getPhoneNumber() == null || person.getPhoneNumber().length() != 9)
            errors.rejectValue("phoneNumber", "required",
                    "Es necesario introducir el número de telefono ");

        if(person.getEmail() == null || person.getEmail().isEmpty())
            errors.rejectValue("email", "required",
                "Es necesario introducir el email");

        if(person.getName() == null || person.getName().isEmpty())
            errors.rejectValue("name", "required",
                    "Es necesario introducir el nombre");
        if(person.getSurname() == null || person.getSurname().isEmpty())
            errors.rejectValue("surname", "required",
                    "Es necesario introducir los apellido");
    }
}

