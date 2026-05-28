package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.OviUser;
import es.uji.ei1027.sgOvi.model.Person;

public class RequestPersonUserDTO {
    Person person;
    OviUser oviUser;
    Assistance_Request assistanceRequest;

    public Person getPerson() {
        return person;
    }

    public OviUser getOviUser() {
        return oviUser;
    }

    public Assistance_Request getAssistanceRequest() {
        return assistanceRequest;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setOviUser(OviUser oviUser) {
        this.oviUser = oviUser;
    }

    public void setAssistanceRequest(Assistance_Request assistanceRequest) {
        this.assistanceRequest = assistanceRequest;
    }
}
