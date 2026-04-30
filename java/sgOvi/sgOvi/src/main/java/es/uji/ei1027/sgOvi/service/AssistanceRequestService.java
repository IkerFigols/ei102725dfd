package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.Selection;

import java.util.List;

public interface AssistanceRequestService {
    public List<Selection> getSelectionsAP(String idAsReq);
    public Person getPerson(String dni);
    public PapPati getPapPati(String dni);
    public void updateState(String idSelection, String state);
}
