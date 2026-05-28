package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Instructor;
import es.uji.ei1027.sgOvi.model.Person;

public class PersonInstructorDTO {
    private Person person;
    private Instructor instructor;

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Person getPerson() {
        return person;
    }

    public Instructor getInstructor() {
        return instructor;
    }

}
