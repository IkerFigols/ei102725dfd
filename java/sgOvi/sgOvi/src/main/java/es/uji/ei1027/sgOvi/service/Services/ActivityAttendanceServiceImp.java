package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.dao.ActivityDao;
import es.uji.ei1027.sgOvi.dao.AttendanceDao;
import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Attendance;
import es.uji.ei1027.sgOvi.model.enums.RolUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ActivityAttendanceServiceImp implements ActivityAttendanceService{

    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private CodeGenerator codeGenerator;

    @Override
    public void addActivity(Activity activity) {
        activityDao.addActivity(activity);
    }

    @Override
    public void deleteActivity(String idActivity) {
        activityDao.deleteActivity(idActivity);
    }

    @Override
    public Activity getActivity(String idActivity) {
        return activityDao.getActivity(idActivity);
    }

    @Override
    public void updateActivity(Activity activity){
        activityDao.updateActivity(activity);
    }

    @Override
    public void addAttendance(Attendance attendance) {
        attendanceDao.addAttendance(attendance);
    }

    @Override
    public void getAttendance(String idAttendance) {
        attendanceDao.getAttendance(idAttendance);
    }

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
    public void deleteAttendanceByUserAndActivity(String idActivity, String idUser, RolUser role){
        attendanceDao.deleteAttendanceByUserAndActivity(idActivity, idUser, role);
    }

    @Override
    public void deleteAttendance(String idAttendance) {
        attendanceDao.deleteAttendance(idAttendance);
    }

    @Override
    public List<Activity> getInstructorActivities(String idInstructor) {
        return activityDao.getInstructorActivities(idInstructor);
    }

    @Override
    public void unregisterUserFromActivity(String idActivity, String dni, RolUser role) {
        attendanceDao.deleteAttendanceByUserAndActivity(idActivity, dni, role);
    }

    @Override
    public List<Activity> getActivities() {
        return activityDao.getActivities();
    }
}
