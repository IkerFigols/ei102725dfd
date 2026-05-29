package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Attendance;
import es.uji.ei1027.sgOvi.model.enums.RolUser;

import java.util.List;

public interface ActivityAttendanceService {
    void addActivity(Activity activity);
    void deleteActivity(String idActivity);
    Activity getActivity(String idActivity);
    void updateActivity(Activity activity);

    void addAttendance(Attendance attendance);
    void getAttendance(String idAttendance);
    void registerUserToActivity (String idActivity, String dni, String rol);
    void deleteAttendanceByUserAndActivity(String idActivity, String idUser, RolUser role);

    void deleteAttendance(String idAttendance);
    List<Activity> getInstructorActivities(String idInstructor);
    void unregisterUserFromActivity(String idActivity, String dni, RolUser role);
    List<Activity> getActivities();
    List<Activity> getUserActivities(String dni);
    List<Activity> getRecentActivities();
    List<Attendance> getAttendancesFromActivity(String idActivity);
    boolean isSuscribed(String idActivity, String dni);
    List<Activity> sortActivities(List<Activity> activities, String sort);
}
