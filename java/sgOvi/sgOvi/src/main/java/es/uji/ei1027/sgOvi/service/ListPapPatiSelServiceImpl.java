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
    public ArrayList<PersonPapPatiDTO> listCompatiblePapPati(String idAsReq) {
        Assistance_Request asReq = assistanceReqDao.getAssistanceRequest(idAsReq);
        //DESCOMENTAR LA LINEA DE ABAJO CUANDO SEPAMOS SEGURO SI UN PATI PUEDE ASISTIR A UN ADULTO --> Recoedar volver a añadir el type a la consulta a la bbdd
        //String type = ChronoUnit.YEARS.between(personDao.getPerson(asReq.getIdOviUser()).getBirthdayDate(), LocalDate.now()) >= 18 ? "PAP" : "PATI";
        Boolean drivingLicense = asReq.getDrivingLicense();
        String province = asReq.getProvince();
        ShiftType shiftType = asReq.getShiftPreference();
        int minAge = asReq.getAge();
        int minExperience = asReq.getExperience();

        ArrayList<PapPati> compatiblePapPatis = papPatiDao.findCompatiblePapPatis(drivingLicense, shiftType,  province, minAge, minExperience);
        System.out.println("Hay " + compatiblePapPatis.size() + " coincidencias");
        ArrayList<PersonPapPatiDTO> listaDtos = new ArrayList<>();
        for (PapPati papPati : compatiblePapPatis){
            PersonPapPatiDTO pppdto = new PersonPapPatiDTO();
            pppdto.setPerson(personDao.getPerson(papPati.getDni()));
            pppdto.setPapPati(papPati);
            listaDtos.add(pppdto);
        }
        return listaDtos;

    }
}
