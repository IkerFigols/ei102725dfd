package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;

import java.util.ArrayList;
import java.util.List;

public interface ListPapPatiSelService {
    public List<PapPati> getPapPatiSelection(String idAsReq);
    public ArrayList<PersonPapPatiDTO> listCompatiblePapPati(String idAsReq);
}