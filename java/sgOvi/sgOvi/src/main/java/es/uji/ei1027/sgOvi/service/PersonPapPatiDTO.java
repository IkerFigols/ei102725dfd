package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;

public class PersonPapPatiDTO implements PersonDTO {
    Person person;
    PapPati papPati;

    public Person getPerson() {
        return person;
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

    @Override
    public String toString() {
        return "PersonPapPatiDTO{" +
                "person=" + person +
                ", papPati=" + papPati +
                '}';
    }
}
