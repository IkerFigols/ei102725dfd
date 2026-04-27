package es.uji.ei1027.sgOvi.controller;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
public class LoginValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDetails.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserDetails user = (UserDetails) target;
        if (user.getDni() == null || user.getDni().isEmpty()){
            errors.rejectValue("dni","required","Debes introducir un DNI");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty())
            errors.rejectValue("password","required","Debes introducir una contraseña");

    }
}