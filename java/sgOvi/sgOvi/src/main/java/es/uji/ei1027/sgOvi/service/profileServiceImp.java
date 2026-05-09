package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Technician;
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
    public PersonInstructorDTO getPersonInstructorDTO() {
        return null;
    }

    @Override
    public PersonTechnicianDTO getPersonTechnicianDTO() {
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
    public Technician getTechnician(String dni) {
        return technicianDao.getTechnician(dni);
    }

    @Override
    public Instructor getInstructor(String dni) {
        return instructorDao.getInstructor(dni);
    }
}
