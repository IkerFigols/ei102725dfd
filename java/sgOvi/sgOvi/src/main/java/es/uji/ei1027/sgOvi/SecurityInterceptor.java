package es.uji.ei1027.sgOvi;

import es.uji.ei1027.sgOvi.service.PersonInstructorDTO;
import es.uji.ei1027.sgOvi.service.PersonOviUserDTO;
import es.uji.ei1027.sgOvi.service.PersonPapPatiDTO;
import es.uji.ei1027.sgOvi.service.PersonTechnicianDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI() + "?"+ request.getQueryString();
        if (session.getAttribute("user") == null) {
            if (uri.contains("/index")|| uri.isEmpty() || uri.equals("/") || uri.contains("/login") ||uri.contains("/css/") || uri.contains("/js/")) {
                return true;
            }
            session.setAttribute("nextURL", uri);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        String role =(String) session.getAttribute("rol");
        String menuError= "";
        switch (role) {
            case ("OVI_USER") -> {
                menuError="/Ovi_User/menuOviUser";
            }
            case ("PAP_PATI") -> {
                menuError="/Pap_Pati/menuPapPati";
            }
            case ("TECHNICIAN") -> {
                menuError="/Technician/menuTechnician";
            }
            case ("INSTRUCTOR") -> {
                menuError="/Instructor/menuInstructor"; //no implementado
            }
        }
        if(uri.contains("/Ovi_User/list") || uri.contains("Person/"))
            return true;

        // Bloqueo para Technician entrando en OviUser y viceversa
        if (uri.contains("/Ovi_User/") && !role.equals("OVI_USER")) {
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }

        if (uri.contains("/Technician/") && !role.equals("TECHNICIAN")) {
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }
        if (uri.contains("/Pap_Pati/") && !role.equals("PAP_PATI")) {
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }
        if (uri.contains("/Instructor/") && !role.equals("INSTRUCTOR")) {
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }
        if (uri.contains("/Contract") && role.equals("TECHNICIAN")) {
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }
        if (uri.contains("/Assistance_Request/")) {
            if(uri.contains("/Assistance_Request/communication") && !role.equals("TECHNICIAN"))
                return true;
            if(uri.contains("/Assistance_Request/") && (role.equals("PAP_PATI") || role.equals("INSTRUCTOR"))){
                response.sendRedirect(request.getContextPath() + menuError);
                return false;
            }
            return true;
        }
        if(uri.contains("/Activity/") && role.equals("TECHNICIAN")){
            response.sendRedirect(request.getContextPath() + menuError);
            return false;
        }
        return true;
    }
}
