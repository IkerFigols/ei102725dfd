package es.uji.ei1027.sgOvi.model.enums;

public enum State {

    // State.PENDING.name() --> devuelve el string PENDING
    // State.APPROVED.getDescription() --> devuelve el string Aprobado
    // si usas un atributo del tipo State es el <<nombre>>.lo que sea
    PENDING("Pendiente"),
    APPROVED("Aprobado"),
    REJECTED("Rechazado"),
    CLOSED_WITH_CONTRACT("Cerrado con contrato"),
    CLOSED_WITH_CONTRACT_DONE("Finalizado con contrato");

    private final String description;

    // 2. Constructor privado
    private State(String description) {
        this.description = description;
    }

    // 3. Getter para la descripción
    public String getDescription() {
        return description;
    }

    public static State fromString(String text) {
        for (State s : State.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }

}
