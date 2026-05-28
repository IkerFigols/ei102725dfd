package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.InstructorDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InstructorServiceImp implements InstructorService{

    @Autowired
    private InstructorDao instructorDao;
    @Autowired
    private PersonDao personDao;

    @Override
    public Instructor getInstructor(String dni) {
        return instructorDao.getInstructor(dni);
    }

    @Override
    public void deleteInstructor(String dni) {
        instructorDao.deleteInstructor(dni);
    }

    @Override
    public void updateInstructor(Instructor instructor) {
        instructorDao.updateInstructor(instructor);
    }

    @Override
    public void addInstructor(Instructor instructor) {
        instructorDao.addInstructor(instructor);
    }

    @Override
    public List<PersonInstructorDTO> listByName(String sortSel) {
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
