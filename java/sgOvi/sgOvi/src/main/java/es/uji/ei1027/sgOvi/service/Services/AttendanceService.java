package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.enums.RolUser;

public interface AttendanceService {
    public void registerUserToActivity (String idActivity, String dni, String rol);
    void unregisterUserFromActivity(String idActivity, String dni, RolUser role);
}
/**
 * Esta clase se tiene que eliminar :D
 */