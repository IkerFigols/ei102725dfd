package es.uji.ei1027.sgOvi.model.enums;

public enum Gender {

    MALE("Masculino"),
    FEMALE("Mujer"),
    UNDEFINED("Otro");

    private final String description;

    // 2. Constructor privado
    private Gender(String description) {
        this.description = description;
    }

    // 3. Getter para la descripción
    public String getDescription() {
        return description;
    }
    public static Gender fromString(String text) {
        for (Gender s : Gender.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}
