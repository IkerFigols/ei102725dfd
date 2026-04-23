package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    PersonDao personDao;

    @Autowired
    OviUserDao oviUserDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Autowired
    TechnicianDao technicianDao;

    @Autowired
    InstructorDao instructorDao;


    @Override
    public String userValidator(String dni, String password) {

        if (dni == null || dni.isEmpty())
            return null;
        if (password == null || password.isEmpty())
            return null;

        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

        for (Person persona : personDao.getPersons()) {
            if (persona.getDni().equals(dni) && encryptor.checkPassword(password, persona.getPassword())) {
                if (persona.getUserType() != null)
                    return persona.getUserType();

                for (Ovi_User oviUser : oviUserDao.getOviUsers()) {
                    if (oviUser.getDni().equals(persona.getDni())) {
                        persona.setUserType("OVI");
                        return persona.getUserType();
                    }
                }
                for (PapPati papPati : papPatiDao.getPapPatis()) {
                    if (papPati.getDni().equals(persona.getDni())) {
                        persona.setUserType("PAP");
                        return persona.getUserType();
                    }
                }
                for (Technician technician : technicianDao.getTechnicians()) {
                    if (technician.getDni().equals(persona.getDni())) {
                        persona.setUserType("TECH");
                        return persona.getUserType();
                    }
                }
                for (Instructor instructor : instructorDao.getInstructors()) {
                    if (instructor.getDni().equals(persona.getDni())) {
                        persona.setUserType("INS");
                        return persona.getUserType();
                    }
                }
            }
        }
        return null;
    }
}
