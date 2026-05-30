package es.uji.ei1027.sgOvi;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        if (session.getAttribute("user") == null ){
            if(uri.contains("/Register"))
                return true;
            if (uri.contains("/index")|| uri.isEmpty() || uri.equals("/") || uri.contains("/login")) {
                return true;
            }
            if (!uri.contains("/profile") && !uri.contains("/css/") && !uri.contains("/js/") && !uri.contains("/images/")
                    && !uri.contains("/error") && !uri.equals("/") && !uri.isBlank()) {
                String query = request.getQueryString();
                String fullURL = (query == null) ? uri : uri + "?" + query;
                session.setAttribute("nextURL", fullURL);
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }


        String role =(String) session.getAttribute("rol");

        if(uri.contains("/Register"))
            throw new OviException("No se puede acceder al registro una vez ya estas registrado", "Acceso no autorizado");

        if (uri.contains("/Ovi_User/") && !role.equals("OVI_USER")) {
           throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }

        if (uri.contains("/Technician/") && !role.equals("TECHNICIAN")) {
            throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }
        if (uri.contains("/Pap_Pati/") && !role.equals("PAP_PATI")) {
            throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }
        if (uri.contains("/Instructor/") && !role.equals("INSTRUCTOR")) {
            throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }
        if (uri.contains("/Contract") && role.equals("TECHNICIAN")) {
            throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }
        if (uri.contains("/Assistance_Request/")) {
            if(uri.contains("/Assistance_Request/communication") && (role.equals("PAP_PATI"))){
                return true;
            }
            if(uri.contains("/Assistance_Request/") && (!role.equals("OVI_USER"))){
                throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
            }
        }
        if(uri.contains("/Activity/") && role.equals("TECHNICIAN")){
            throw new OviException("Estas intentando acceder a zonas que no debes", "Acceso no autorizado");
        }

        return true;
    }
}
