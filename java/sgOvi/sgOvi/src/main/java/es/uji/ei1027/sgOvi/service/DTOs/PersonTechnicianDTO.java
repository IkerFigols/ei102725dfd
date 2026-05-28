package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.Technician;

public class PersonTechnicianDTO{
    Person person;
    Technician technician;

    public Person getPerson() {
        return person;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }
}
