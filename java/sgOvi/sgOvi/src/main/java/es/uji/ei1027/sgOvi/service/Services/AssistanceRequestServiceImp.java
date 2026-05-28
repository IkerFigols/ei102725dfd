package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.AssistanceRequestSelectionDTO;
import es.uji.ei1027.sgOvi.service.DTOs.PapPatiSelectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssistanceRequestServiceImp implements  AssistanceRequestService{

    @Autowired
    SelectionDao selectionDao;

    @Autowired
    AssistanceReqDao assistanceReqDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Autowired
    CommunicationDao communicationDao;

    @Autowired
    CodeGenerator codeGenerator;

    @Override
    public List<Selection> getSelectionsAP(String idAsReq) {
        if (idAsReq == null || assistanceReqDao.getAssistanceRequest(idAsReq) == null)
            return new ArrayList<>();
        return selectionDao.getSelectionsAPRequest(idAsReq);
    }

    @Override
    public Person getPerson(String dni) {
        return personDao.getPerson(dni);
    }

    @Override
    public PapPati getPapPati(String dni) {
        return papPatiDao.getPapPati(dni);
    }

    @Override
    public void updateStateSelection(String idSelection, String state) {
        selectionDao.updateState(idSelection, state);
    }

    public String getDniAP(String idSelection){
        return assistanceReqDao.getAssistanceRequest(selectionDao.getSelection(idSelection).getIdAsReq()).getIdOviUser();
    }

    @Override
    public List<PapPatiSelectionDTO> getPapPatisSelectionDTO(String idAsReq, String state) {
        List<PapPatiSelectionDTO> papPatiSelectionDTOS = selectionDao.getPapSelByAP(idAsReq, state);
        return papPatiSelectionDTOS;
    }

    @Override
    public List<Communication> getComunicationsSelection(String idSelection) {
        return communicationDao.getCommunicationsSelectionOrdered(idSelection);
    }

    @Override
    public void addCommunication(Communication communication) {
        communicationDao.addCommunication(communication);
    }

    @Override
    public Assistance_Request getAssistanceRequest(String idAsReq) {
        return assistanceReqDao.getAssistanceRequest(idAsReq);
    }

    @Override
    public void deleteAssistanceRequest(String idAsReq) {
        assistanceReqDao.deleteAssistanceRequest(idAsReq);
    }

    @Override
    public void updateAssistanceRequest(Assistance_Request assistanceRequest) {
        assistanceReqDao.updateAssistanceRequest(assistanceRequest);
    }

    @Override
    public void addAssistanceRequest(Assistance_Request assistanceRequest) {
        assistanceReqDao.addAssistanceRequest(assistanceRequest);
    }

    @Override
    public List<Assistance_Request> getAssistanceRequestsByOviUser(String dni, State state) {
        return assistanceReqDao.getAssistanceRequestsByOviUser(dni,state.name());
    }

    /*@Override
    public void generateContract(String idSelection, Assistance_Request ap) {
        // simulación de contrato con dos meses de duración
        Contract contract = new Contract();
        contract.setIdContract(codeGenerator.generateCode("CON"));
        contract.setIdSelection(idSelection);
        contract.setSchedule(ap.getShiftPreference().name());
        contract.setStartDate(ap.getDate());

        LocalDate date = ap.getDate().plusMonths(2);
        contract.setEndDate(date);
        contract.setDocument("/document/"+idSelection.toLowerCase()+".pdf");
        contract.setSalary(1000);
        contractDao.addContract(contract);
    }
    */

    @Override
    public void rejectOtherCandidates(String idAsReq, String idPapPati) {
        selectionDao.rejectSelections(idAsReq,idPapPati);
    }
    @Override
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPati(String idPapPati) {
        List<AssistanceRequestSelectionDTO> resultado = new ArrayList<>();
        List<Selection> selecciones = selectionDao.getSelectionsByPapPati(idPapPati);

        for (Selection sel : selecciones) {
            Assistance_Request ar = assistanceReqDao.getAssistanceRequest(sel.getIdAsReq());
            if (ar != null) {
                AssistanceRequestSelectionDTO dto = new AssistanceRequestSelectionDTO();
                dto.setAssistanceRequest(ar);
                dto.setIdSelection(sel.getIdSelection());
                dto.setSelectionState(sel.getState().name());
                dto.setSelectionDate(sel.getDate());

                Person ovi = personDao.getPerson(ar.getIdOviUser());
                dto.setOviUserName(ovi != null ? ovi.getName() : "Desconocido");

                resultado.add(dto);
            }
        }

        //ORDENACIÓN POR ESTADO: 1. APPROVED, 2. PENDING, 3. REJECTED
        resultado.sort((a, b) -> {
            return Integer.compare(
                    prioridadEstado(a.getSelectionState()),
                    prioridadEstado(b.getSelectionState())
            );
        });

        return resultado;
    }
    private int prioridadEstado(String estado) {
        switch (estado) {
            case "APPROVED": return 1;
            case "PENDING":  return 2;
            case "REJECTED": return 3;
            default:         return 4;
        }
    }
    @Override
    public List<AssistanceRequestSelectionDTO> getRequestsByPapPatiFiltered(String idPapPati, String state) {
        return selectionDao.getRequestsByPapPatiFiltered(idPapPati, state);
    }

    @Override
    public Selection getSelection(String idSelection) {
        return selectionDao.getSelection(idSelection);
    }

    @Override
    public void addSelection(Selection selection) {
        selectionDao.addSelection(selection);
    }

    @Override
    public void deleteSelection(String idSelection) {
        selectionDao.deleteSelection(idSelection);
    }

    @Override
    public void updateSelection(Selection selection) {
        selectionDao.updateSelection(selection);
    }

    @Override
    public void deleteCommunication(String idCommunication) {
        communicationDao.deleteCommunication(idCommunication);
    }
}