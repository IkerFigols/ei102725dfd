package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.*;

public class PersonDTO {
    private Person person;
    private PapPati papPati;
    private OviUser oviUser;
    private Technician technician;
    private Instructor instructor;


    public Person getPerson() {
        return person;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Technician getTechnician() {
        return technician;
    }

    public OviUser getOviUser() {
        return oviUser;
    }

    public PapPati getPapPati() {
        return papPati;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPapPati(PapPati papPati) {
        this.papPati = papPati;
    }

    public void setOviUser(OviUser oviUser) {
        this.oviUser = oviUser;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
