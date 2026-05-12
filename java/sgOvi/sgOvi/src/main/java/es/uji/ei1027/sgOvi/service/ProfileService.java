package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.*;

public interface ProfileService {
    public PersonPapPatiDTO getPersonPapPatDTO();
    public PersonOviUserDTO getPersonOviUserDTO();
    public PersonInstructorDTO getPersonInstructorDTO();
    public PersonTechnicianDTO getPersonTechnicianDTO();
    public OviUser getOviUser(String dni);
    public PapPati getPapPati(String dni);
    public Technician getTechnician (String dni);
    public Instructor getInstructor(String dni);
    public void updateOviUser(OviUser oviUser);
    public void updatePapPati(PapPati papPati);
    public void updateTechnician(Technician technician);
    public void updateInstructor(Instructor instructor);
    public void updatePerson(Person person);
}
