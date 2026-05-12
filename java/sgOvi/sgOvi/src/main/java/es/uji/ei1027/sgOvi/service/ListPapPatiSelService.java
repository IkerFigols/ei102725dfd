package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.PapPati;

import java.util.ArrayList;
import java.util.List;

public interface ListPapPatiSelService {
    public List<PapPati> getPapPatiSelection(String idAsReq);
    public ArrayList<PersonPapPatiDTO> listCompatiblePapPati(String idAsReq);
}