package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PapPatiSelectionDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;


import java.util.ArrayList;
import java.util.List;

public interface AssistanceRequestService {
    public List<Selection> getSelectionsAP(String idAsReq);
    public Person getPerson(String dni);
    public PapPati getPapPati(String dni);
    public void updateStateSelection(String idSelection, String state);
    public String getDniAP(String idSelection);
    public List<PapPatiSelectionDTO> getPapPatisSelectionDTO(String idAsReq, String state);
    public List<Communication> getComunicationsSelection(String idSelection);
    // public void generateContract(String idSelection, Assistance_Request ap);
    public void rejectOtherCandidates(String idAsReq,String idPapPati);
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPati(String idPapPati);
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPatiFiltered(String idPapPati, String state);
    public Selection getSelection(String idSelection);
    public void addSelection(Selection selection);
    public void deleteSelection(String idSelection);
    public void updateSelection(Selection selection);
    public void deleteCommunication(String idCommunication);
    public void addCommunication (Communication communication);
    public Assistance_Request getAssistanceRequest(String idAsReq);
    public void deleteAssistanceRequest(String idAsReq);
    public void updateAssistanceRequest(Assistance_Request assistanceRequest);
    public void addAssistanceRequest(Assistance_Request assistanceRequest);
    public List<Assistance_Request> getAssistanceRequestsByOviUser(String dni, State state);
    public List<Assistance_Request> getAssistanceRequests();
    public ArrayList<PersonPapPatiDTO> listCompatiblePapPati(String idAsReq);
    List<Assistance_Request> sortAssistance(List<Assistance_Request> assistanceRequests, String sort);
    List<PapPatiSelectionDTO> sortSelection(List<PapPatiSelectionDTO> papPatiSelectionDTOS, String sort);
    List<Selection> getSelectionApprovedByAp(String idAsReq);
}