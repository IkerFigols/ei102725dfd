package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonOviUserDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonTechnicianDTO;

public interface ProfileService {
    public PersonPapPatiDTO getPersonPapPatDTO();
    public PersonOviUserDTO getPersonOviUserDTO();
    public OviUser getOviUser(String dni);
    public PapPati getPapPati(String dni);
    public void updateOviUser(OviUser oviUser);
    public void updatePapPati(PapPati papPati);
    public void updatePerson(Person person);
}
