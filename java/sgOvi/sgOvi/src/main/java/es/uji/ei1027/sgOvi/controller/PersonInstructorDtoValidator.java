package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO; // Asegúrate de importar tu DTO
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class PersonInstructorDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        // 1. Ahora soportamos el DTO, no la entidad Person suelta
        return PersonInstructorDTO.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        PersonInstructorDTO dto = (PersonInstructorDTO) obj;
        Person person = dto.getPerson();
        Instructor instructor = dto.getInstructor();

        if(person.getDni() == null || person.getDni().length() != 9)
            errors.rejectValue("person.dni", "required",
                    "Es necesario introducir el dni");

        if (!person.getDni().matches("^[XYZ\\d]\\d{7}[A-Z]$"))
            errors.rejectValue("person.dni", "format",
                    "El DNI debe tener exactamente 8 números y 1 letra mayúscula");

        if(person.getBirthdayDate() == null || person.getBirthdayDate().isAfter(LocalDate.now()))
            errors.rejectValue("person.birthdayDate", "required",
                    "Es necesario introducir una fecha válida");

        if(person.getPhoneNumber() == null || person.getPhoneNumber().length() != 9)
            errors.rejectValue("person.phoneNumber", "required",
                    "Es necesario introducir el número de telefono ");

        if(person.getEmail() == null || person.getEmail().isEmpty())
            errors.rejectValue("person.email", "required",
                    "Es necesario introducir el email");

        if(person.getName() == null || person.getName().isEmpty())
            errors.rejectValue("person.name", "required",
                    "Es necesario introducir el nombre");

        if(person.getSurname() == null || person.getSurname().isEmpty())
            errors.rejectValue("person.surname", "required",
                    "Es necesario introducir los apellidos");

        if(person.getGender() == null)
            errors.rejectValue("person.gender", "required", "Es necesario seleccionar un genero");

        if(person.getCity() == null || person.getCity().isEmpty())
            errors.rejectValue("person.city", "required","Es necesario introducir la ciudad en la que resides");

        if(person.getProvince() == null || person.getProvince().isEmpty())
            errors.rejectValue("person.province","required","Es necesario introducir la provincia en la que resides");

        if(person.getPassword() == null || person.getPassword().isEmpty() || person.getPassword().length() <= 6 )
            errors.rejectValue("person.password", "required",
                    "Es necesario introducir una contraseña de mas de 6 caracteres");

        // --- Validaciones de longitud máxima ---

        if(person.getDni() != null && person.getDni().length() > 9 )
            errors.rejectValue("person.dni","required","El dni debe tener como máximo 9 caracteres");

        if(person.getName() != null && person.getName().length() > 50 )
            errors.rejectValue("person.name","required","El nombre debe tener como máximo 50 caracteres");

        if(person.getSurname() != null && person.getSurname().length() > 50 )
            errors.rejectValue("person.surname","required","Los apellidos deben tener como máximo 50 caracteres");

        if(person.getPhoneNumber() != null && person.getPhoneNumber().length() > 9 )
            errors.rejectValue("person.phoneNumber","required","El telefono debe tener como máximo 9 caracteres");

        if(person.getEmail() != null && person.getEmail().length() > 50 )
            errors.rejectValue("person.email","required","El email debe tener como máximo 50 caracteres");

        if(person.getGender() != null && person.getGender().name().length() > 10 )
            errors.rejectValue("person.gender","required","El genero debe tener como máximo 10 caracteres");

        if(person.getPassword() != null && person.getPassword().length() > 100 )
            errors.rejectValue("person.password","required","La contraseña debe tener como máximo 100 caracteres");

        if(person.getCity() != null && person.getCity().length() > 50 )
            errors.rejectValue("person.city","required","La ciudad debe tener como máximo 50 caracteres");

        if(person.getProvince() != null && person.getProvince().length() > 50 )
            errors.rejectValue("person.province","required","La provincia debe tener como máximo 50 caracteres");
        if (instructor.getExpertise() != null && instructor.getExpertise().length() > 100)
            errors.rejectValue("instructor.expertise", "length", "La especialidad no puede superar los 100 carácteres");

    }

}