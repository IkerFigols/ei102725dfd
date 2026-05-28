package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.ActivityDao;
import es.uji.ei1027.sgOvi.dao.AttendanceDao;
import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Attendance;
import es.uji.ei1027.sgOvi.controller.exception.OviException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CodeGenerator codeGenerator;

    @Override
    public void registerUserToActivity(String idActivity, String dni, String role) {
        Activity activity = activityDao.getActivity(idActivity);
        List<Attendance> currentAttendances = attendanceDao.getAttendancesByActivity(idActivity);
        //Control de inscripción
        for (Attendance att: currentAttendances) {
            if ("OVI_USER".equals(role) && dni.equals(att.getIdOviUser())) {
                throw new OviException("Ya estás inscrito en esta actividad.", "Inscripción Duplicada");
            }
            if ("PAP_PATI".equals(role) && dni.equals(att.getIdPapPati())) {
                throw new OviException("Ya estás inscrito en esta actividad.", "Inscripción Duplicada");
            }
        }
        //Control de plazas
        if (activity.getCapacity() != null && currentAttendances.size() >= activity.getCapacity()) {
            throw new OviException(
                    "Lo sentimos, la actividad: '" + activity.getTittle() + "' está completa.",
                    "Actividad Completa"
            );
        }
        Attendance attendance = new Attendance();

        String idAtt = codeGenerator.generateCode("ATT");
        attendance.setIdAtt(idAtt);

        attendance.setIdActivity(idActivity);
        attendance.setAttend(false);

        if ("OVI_USER".equals(role)) {
            attendance.setIdOviUser(dni);
            attendance.setIdPapPati(null);
        } else if ("PAP_PATI".equals(role)) {
            attendance.setIdOviUser(null);
            attendance.setIdPapPati(dni);
        }
        attendanceDao.addAttendance(attendance);
    }
    @Override
    public void unregisterUserFromActivity(String idActivity, String dni, String role) {
        attendanceDao.deleteAttendanceByUserAndActivity(idActivity, dni, role);
    }
}