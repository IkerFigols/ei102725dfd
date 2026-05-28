package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonOviUserDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonTechnicianDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class profileServiceImp implements ProfileService{

    @Autowired
    OviUserDao oviUserDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Autowired
    TechnicianDao technicianDao;

    @Autowired
    InstructorDao instructorDao;


    @Override
    public PersonPapPatiDTO getPersonPapPatDTO() {
        return null;
    }

    @Override
    public PersonOviUserDTO getPersonOviUserDTO() {
        return null;
    }

    @Override
    public OviUser getOviUser(String dni) {
        return oviUserDao.getOviUser(dni);
    }

    @Override
    public PapPati getPapPati(String dni) {
        return papPatiDao.getPapPati(dni);
    }



    @Override
    public void updateOviUser(OviUser oviUser) {
        oviUserDao.updateOviUser(oviUser);
    }

    @Override
    public void updatePapPati(PapPati papPati) {
        papPatiDao.updatePapPati(papPati);
    }


    @Override
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }

}
