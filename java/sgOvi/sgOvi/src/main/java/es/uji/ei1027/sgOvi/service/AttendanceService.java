package es.uji.ei1027.sgOvi.service;

public interface AttendanceService {
    public void registerUserToActivity (String idActivity, String dni, String rol);
    public void unregisterUserFromActivity (String idActivity, String dni, String rol);
}
