package es.uji.ei1027.sgOvi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        if (session.getAttribute("user") == null) {
            if (uri.contains("/index")|| uri.isEmpty() || uri.equals("/") || uri.contains("/login") ||uri.contains("/css/") || uri.contains("/js/")) {
                System.out.println("uri: "+uri);
                return true;
            }
            System.out.println("uri: "+uri);
            session.setAttribute("nextURL", uri);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        String role =(String) session.getAttribute("rol");
        if(uri.contains("/Ovi_User/list") || uri.contains("Person/"))
            return true;

        // Bloqueo para Technician entrando en OviUser y viceversa
        if (uri.contains("/Ovi_User/") && !role.equals("OVI_USER")) {
            response.sendRedirect(request.getContextPath() + "/error/403");
            return false;
        }

        if (uri.contains("/Technician/") && !role.equals("TECHNICIAN")) {
            response.sendRedirect(request.getContextPath() + "/error/403");
            return false;
        }
        if (uri.contains("/Pap_Pati/") && !role.equals("PAP_PATI")) {
            response.sendRedirect(request.getContextPath() + "/error/403");
            return false;
        }
        if (uri.contains("/Instructor/") && !role.equals("INSTRUCTOR")) {
            response.sendRedirect(request.getContextPath() + "/error/403");
            return false;
        }

        // 3. ZONAS COMUNES (Assistance Request)
        if (uri.contains("/Assistance_Request/")) {
            if (role.equals("TECHNICIAN") || role.equals("OVI_USER") || role.equals("PAP_PATI")) {
                return true;
            }
        }

        return true;
    }
}
