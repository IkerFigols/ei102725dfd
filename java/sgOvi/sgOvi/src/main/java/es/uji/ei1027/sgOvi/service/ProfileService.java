package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Technician;

public interface ProfileService {
    public PersonPapPatiDTO getPersonPapPatDTO();
    public PersonOviUserDTO getPersonOviUserDTO();
    public PersonInstructorDTO getPersonInstructorDTO();
    public PersonTechnicianDTO getPersonTechnicianDTO();
    public OviUser getOviUser(String dni);
    public PapPati getPapPati(String dni);
    public Technician getTechnician (String dni);
    public Instructor getInstructor(String dni);
}
