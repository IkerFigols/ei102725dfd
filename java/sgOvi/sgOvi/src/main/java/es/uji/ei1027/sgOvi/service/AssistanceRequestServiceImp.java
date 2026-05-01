package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    ContractDao contractDao;

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
    public List<Communication> getComunicationsSelection(String idSelection) {
        return communicationDao.getCommunicationsSelectionOrdered(idSelection);
    }

    @Override
    public void addCommunication(Communication communication) {
        communicationDao.addCommunication(communication);
    }

    @Override
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

    @Override
    public void rejectOtherCandidates(String idAsReq, String idPapPati) {
        selectionDao.rejectSelections(idAsReq,idPapPati);
    }
}
