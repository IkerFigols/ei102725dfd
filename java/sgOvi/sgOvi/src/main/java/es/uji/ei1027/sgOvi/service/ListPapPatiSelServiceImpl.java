package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ListPapPatiSelServiceImpl implements  ListPapPatiSelService{

    @Autowired
    AssistanceReqDao assistanceReqDao;

    @Autowired
    PapPatiDao papPatiDao;

    @Override
    public List<PapPati> getPapPatiSelection(String idAsReq) {
        if (idAsReq == null || assistanceReqDao.getAssistanceRequest(idAsReq) == null)
            return new ArrayList<>();

        List<PapPati> res = papPatiDao.getCandidatesPapPati(idAsReq);
        return res;
    }
}
