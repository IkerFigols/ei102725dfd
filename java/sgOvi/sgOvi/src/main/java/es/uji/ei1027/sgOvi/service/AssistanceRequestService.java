package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.*;


import java.util.List;

public interface AssistanceRequestService {
    public List<Selection> getSelectionsAP(String idAsReq);
    public Person getPerson(String dni);
    public PapPati getPapPati(String dni);
    public void updateStateSelection(String idSelection, String state);
    public String getDniAP(String idSelection);
    public List<PapPatiSelectionDTO> getPapPatisSelectionDTO(String idAsReq);
    public List<Communication> getComunicationsSelection(String idSelection);
    public void addCommunication (Communication communication);
    public void generateContract(String idSelection, Assistance_Request ap);
    public void rejectOtherCandidates(String idAsReq,String idPapPati);
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPati(String idPapPati);
}
