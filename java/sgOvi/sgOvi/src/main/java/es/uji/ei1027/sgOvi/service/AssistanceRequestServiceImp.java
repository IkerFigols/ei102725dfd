package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
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
}
