package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.Communication;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.model.Selection;
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
    public void updateState( String idSelection, String state) {
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
}
