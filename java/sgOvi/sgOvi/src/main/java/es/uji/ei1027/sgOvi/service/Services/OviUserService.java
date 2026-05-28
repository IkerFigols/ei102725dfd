package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonOviUserDTO;

import java.util.List;

public interface OviUserService {
    OviUser getOviUser(String dni);

    Person getPerson(String dni);

    void updateOviUser(OviUser oviUser);

    void deleteOviUser(String dni);

    void addOviUser(OviUser oviUser);

    void addPerson(Person person);

    List<PersonOviUserDTO> getPersonUserList(String stateSel, String sortSel);
}
