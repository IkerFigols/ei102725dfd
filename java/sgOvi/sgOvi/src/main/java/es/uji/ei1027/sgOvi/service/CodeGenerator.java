package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.dao.*;
import es.uji.ei1027.sgOvi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CodeGenerator {
    @Autowired
    AssistanceReqDao assistanceReqDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    AttendanceDao attendanceDao;
    @Autowired
    CommunicationDao communicationDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    SelectionDao selectionDao;


    public String generateCode(String base){
        String last;
        switch (base){
            case "ACT" :
                last = activityDao.getMaxId();
                if (last == null)
                    last = "ACT000000" ;
                break;
            case "ASR":
                last = assistanceReqDao.getMaxId();
                if (last == null)
                    last = "ASR000000" ;
                break;
            case "ATT":
                last = attendanceDao.getMaxId();
                if (last == null)
                    last = "ATT000000" ;
                break;
            case "COM":
                last = communicationDao.getMaxId();
                if (last == null)
                    last = "COM000000" ;
                break;
            case "CON":
                last = contractDao.getMaxId();
                if (last == null)
                    last = "CON000000" ;
                break;
            case "SEL":
                last = selectionDao.getMaxId();
                if (last == null)
                    last = "SEL000000" ;
                break;
            default:
                return "ERR";
        }

        int n = Integer.parseInt(last.trim().substring(3)) + 1;
        int a = Integer.toString(n).length();
        while(base.length()<=9){
            if(base.length() + a == 9) {
                base = base + n;
                break;
            }
            base = base + "0";
        }
        return base;
    }
}
