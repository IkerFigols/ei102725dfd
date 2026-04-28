package es.uji.ei1027.sgOvi.model.enums;

public enum StaffType {


    PAP,
    PATI;

    public static StaffType fromString(String text) {
        for (StaffType s : StaffType.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
}
