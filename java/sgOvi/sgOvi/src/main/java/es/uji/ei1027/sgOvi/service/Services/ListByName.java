package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonOviUserDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;

import java.util.List;

public interface ListByName {
    List<PersonOviUserDTO> personUserList(String stateSel, String sortSel);

    List<PersonPapPatiDTO> personPapPatiList(String stateSel, String sortSel);

    List<PersonInstructorDTO> personInstructorList(String sort);

    List<PersonPapPatiDTO> personPapPatiList(List<PersonPapPatiDTO> listaDTOs, String sortSel);
}
