package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.PapPatiDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.enums.ShiftType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class ListPapPatiSelServiceImpl implements  ListPapPatiSelService{

    @Autowired
    AssistanceReqDao assistanceReqDao;
    @Autowired
    PersonDao personDao;
    @Autowired
    PapPatiDao papPatiDao;

    @Override
    public List<PapPati> getPapPatiSelection(String idAsReq) {
        if (idAsReq == null || assistanceReqDao.getAssistanceRequest(idAsReq) == null)
            return new ArrayList<>();

        List<PapPati> res = papPatiDao.getCandidatesPapPati(idAsReq);
        return res;
    }

    @Override
    public ArrayList<PapPati> listCompatiblePapPati(String idAsReq) {
        Assistance_Request asReq = assistanceReqDao.getAssistanceRequest(idAsReq);
        String type = ChronoUnit.YEARS.between(personDao.getPerson(asReq.getIdOviUser()).getBirthdayDate(), LocalDate.now()) >= 18 ? "PAP" : "PATI";
        Boolean drivingLicense = asReq.getDrivingLicense();
        String city = asReq.getCity();
        String province = asReq.getProvince();
        ShiftType shiftType = asReq.getShiftPreference();
        int minAge = asReq.getAge();
        int minExperience = asReq.getExperience();

        ArrayList<PapPati> compatiblePapPatis = papPatiDao.findCompatiblePapPatis(type, drivingLicense, shiftType,  province, minAge, minExperience);
        System.out.println("Hay " + compatiblePapPatis.size() + " coincidencias");

        return compatiblePapPatis;

    }
}
