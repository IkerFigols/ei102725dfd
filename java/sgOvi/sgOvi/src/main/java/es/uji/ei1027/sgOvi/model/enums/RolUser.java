package es.uji.ei1027.sgOvi.model.enums;

public enum RolUser {

    OVI_USER("Usuario Ovi"),
    PAP_PATI("Asistente Personal"),
    TECHNICIAN("Tecnico"),
    INSTRUCTOR("Instructor");

    private final String description;

    // 2. Constructor privado
    private RolUser(String description) {
        this.description = description;
    }

    // 3. Getter para la descripción
    public String getDescription() {
        return description;
    }
    public static RolUser fromString(String text) {
        for (RolUser s : RolUser.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}
