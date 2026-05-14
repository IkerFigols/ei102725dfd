package es.uji.ei1027.sgOvi.service;

import java.util.List;

public interface ListByName {
    List<PersonOviUserDTO> personUserList(String stateSel, String sortSel);
    List<PersonPapPatiDTO> personPapPatiList(String stateSel, String sortSel);
    List<PersonInstructorDTO> personInstructorList(String sort);
}
