package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;

import java.util.List;

public interface PapPatiService {
    void addPapPati(PapPati papPati);
    void addPerson(Person person);
    void updatePapPati(PapPati papPati);
    Person getPerson(String dni);
    PapPati getPapPati(String dni);

    List<PersonPapPatiDTO> listByName(String stateSel, String sortSel);
    List<PersonPapPatiDTO> orderList(List<PersonPapPatiDTO> listaDTOs, String sortSel);
    List<PersonPapPatiDTO> getPapPatiTrainingActivities(String idActivity);
}
