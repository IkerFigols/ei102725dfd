package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ListByNameImp implements ListByName{
    @Autowired
    PersonDao personDao;

    @Autowired
    OviUserDao oviUserDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Autowired
    InstructorDao instructorDao;

    //El mapa es tal que: Map<dni, List<nombre, estado>
    @Override
    public List<PersonOviUserDTO> personUserList(String stateSel, String sortSel) {

        List<PersonOviUserDTO> listaDTOs = new ArrayList<>();
        List<OviUser> listaUser = oviUserDao.getOviUsers();
        for(OviUser user : listaUser){
            PersonOviUserDTO personOviUserDTO = new PersonOviUserDTO();
            personOviUserDTO.setPerson(personDao.getPerson(user.getDni()));
            personOviUserDTO.setOviUser(user);
            listaDTOs.add(personOviUserDTO);
        }

        //filtrado
        if (stateSel != null && !stateSel.equals("ALL")) {
            listaDTOs.removeIf(dto -> !dto.getOviUser().getState().name().equals(stateSel));
        }

        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs = listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personOviUserDTO -> { return personOviUserDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }


        return listaDTOs;
    }

    public List<PersonPapPatiDTO> personPapPatiList(String stateSel, String sortSel) {

        List<PersonPapPatiDTO> listaDTOs = new ArrayList<>();
        List<PapPati> listaPapPati = papPatiDao.getPapPatis();
        for(PapPati papPati : listaPapPati){
            PersonPapPatiDTO personPapPatiDTO = new PersonPapPatiDTO();
            personPapPatiDTO.setPapPati(papPati);
            personPapPatiDTO.setPerson(personDao.getPerson(papPati.getDni()));
            listaDTOs.add(personPapPatiDTO);
        }
        //filtrado
        if (stateSel != null && !stateSel.equals("ALL")) {
            listaDTOs.removeIf(dto -> !dto.getPapPati().getState().name().equals(stateSel));
        }

        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personPapPatiDTO -> { return personPapPatiDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }

        return listaDTOs;
    }

    @Override
    public List<PersonInstructorDTO> personInstructorList(String sortSel) {
        List<PersonInstructorDTO> listaDTOs = new ArrayList<>();
        List<Instructor> listaInstructor = instructorDao.getInstructors();
        for(Instructor instructor : listaInstructor){
            PersonInstructorDTO personInstructorDTO = new PersonInstructorDTO();
            personInstructorDTO.setInstructor(instructor);
            personInstructorDTO.setPerson(personDao.getPerson(instructor.getDni()));
            listaDTOs.add(personInstructorDTO);
        }

        //Sort
        if (sortSel != null) {
            switch (sortSel) {
                case "nameAsc":
                    listaDTOs.sort(Comparator.comparing(personInstructorDTO ->  { return personInstructorDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    break;
                case "nameDesc":
                    listaDTOs.sort(Comparator.comparing(personInstructorDTO ->  { return personInstructorDTO.getPerson().getName();},String.CASE_INSENSITIVE_ORDER));
                    listaDTOs = listaDTOs.reversed();
                    break;
                case "dni":
                    listaDTOs.sort(Comparator.comparing(personInstructorDTO ->  { return personInstructorDTO.getPerson().getDni();},String.CASE_INSENSITIVE_ORDER));
                    break;
            }
        }

        return listaDTOs;
    }

}
