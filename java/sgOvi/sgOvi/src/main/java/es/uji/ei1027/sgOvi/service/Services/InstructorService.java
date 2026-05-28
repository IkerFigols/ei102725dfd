package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.service.DTOs.PersonInstructorDTO;

import java.util.List;

public interface InstructorService {
    Instructor getInstructor(String dni);
    void deleteInstructor(String dni);
    void updateInstructor(Instructor instructor);
    void addInstructor(Instructor instructor);
    List<PersonInstructorDTO> listByName(String sortSel);
}
