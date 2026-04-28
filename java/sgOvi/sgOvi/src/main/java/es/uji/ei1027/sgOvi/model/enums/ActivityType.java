package es.uji.ei1027.sgOvi.model.enums;

public enum ActivityType {

    DISSEMINATION("Divulgación"),
    TRAINING("Formación");

    private final String description;

    // 2. Constructor privado
    private ActivityType(String description) {
        this.description = description;
    }

    // 3. Getter para la descripción
    public String getDescription() {
        return description;
    }
    public static ActivityType fromString(String text) {
        for (ActivityType s : ActivityType.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}
