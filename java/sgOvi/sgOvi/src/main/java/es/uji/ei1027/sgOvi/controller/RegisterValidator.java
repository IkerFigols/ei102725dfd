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
        if (!person.getDni().matches("^\\d{8}[A-Z]$"))
            errors.rejectValue("dni", "format",
                    "El DNI debe tener exactamente 8 números y 1 letra mayúscula");
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

        if(person.getGender() == null || person.getGender().isEmpty())
            errors.rejectValue("gender", "required", "Es necesario seleccionar un genero");
        if(person.getPreference() == null || person.getPreference().isEmpty())
            errors.rejectValue("preference", "required", "Es necesario seleccionar una preferencia");

        if (!person.getDataProtection()) {
            errors.rejectValue("dataProtection", "required", "Debes aceptar la protección de datos para continuar");
        }

        if(person.getPassword() == null || person.getPassword().isEmpty() || person.getPassword().length() <= 6 )
            errors.rejectValue("password", "required",
                    "Es necesario introducir una contraseña de mas de 6 caracteres");
        if(person.getDni() != null && person.getDni().length() > 9 )
            errors.rejectValue("dni","required","El dni debe tener como máximo 9 caracteres");

        if(person.getName() != null && person.getName().length() > 50 )
            errors.rejectValue("name","required","El nombre debe tener como máximo 50 caracteres");
        if(person.getSurname() != null && person.getSurname().length() > 50 )
            errors.rejectValue("surname","required","Los apellidos debe tener como máximo 50 caracteres");
        if(person.getPhoneNumber() != null && person.getPhoneNumber().length() > 9 )
            errors.rejectValue("phoneNumber","required","El telefono debe tener como máximo 9 caracteres");
        if(person.getEmail() != null && person.getEmail().length() > 50 )
            errors.rejectValue("email","required","El email debe tener como máximo 50 caracteres");
        if(person.getGender() != null && person.getGender().length() > 10 )
            errors.rejectValue("dni","required","El genero debe tener como máximo 10 caracteres");

        if(person.getPassword() != null && person.getPassword().length() > 9 )
            errors.rejectValue("password","required","La contraseña debe tener como máximo 100 caracteres");
        if(person.getLocation() != null && person.getLocation().length() > 9 )
            errors.rejectValue("location","required","La localidad debe tener como máximo 50 caracteres");

    }
}

