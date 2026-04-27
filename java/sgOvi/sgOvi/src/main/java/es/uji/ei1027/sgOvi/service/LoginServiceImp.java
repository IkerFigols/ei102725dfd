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

        BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
        Person persona = personDao.getPerson(dni);
        if (persona.getDni().equals(dni) && encryptor.checkPassword(password, persona.getPassword())) {
            if(oviUserDao.getOviUser(dni) != null) {
                return "Ovi_User";
            }
            if(papPatiDao.getPapPati(dni) != null) {
                return "Pap_Pati";
            }
            if(instructorDao.getInstructor(dni) != null) {
                return "Intructor";
            }
            if(technicianDao.getTechnician(dni) != null) {
                return "Technician";
            }
        }
        return null;
    }
}