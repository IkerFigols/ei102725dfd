package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;

public class PersonOviUserDTO  {
    Person person;
    OviUser oviUser;

    public Person getPerson() {
        return person;
    }

    public OviUser getOviUser() {
        return oviUser;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setOviUser(OviUser oviUser) {
        this.oviUser = oviUser;
    }
}
