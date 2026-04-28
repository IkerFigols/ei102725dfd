package es.uji.ei1027.sgOvi.model.enums;

public enum ShiftType {

    MORNING("Mañana"),
    AFTERNOON("Tarde"),
    ANY("Cualquiera");

    private final String description;

    // 2. Constructor privado
    private ShiftType(String description) {
        this.description = description;
    }

    // 3. Getter para la descripción
    public String getDescription() {
        return description;
    }
    public static ShiftType fromString(String text) {
        for (ShiftType s : ShiftType.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}
