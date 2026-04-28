package es.uji.ei1027.sgOvi.model;
import es.uji.ei1027.sgOvi.model.enums.State;

public class OviUser {
    private String dni;
    private String address;
    private String legalGuardian;
    private State state;
    private String reason;


    public OviUser(){
        state = State.fromString("PENDING");
    }

    public String getDni() {
        return dni;
    }


    public String getAddress() {
        return address;
    }

    public String getLegalGuardian() {
        return legalGuardian;
    }

    public State getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }


    public void setDni(String dni) {
        this.dni = dni;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setLegalGuardian(String legalGuardian) {
        this.legalGuardian = legalGuardian;
    }

    public void setState(String state) {
        this.state = State.fromString(state);
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    @Override
    public String toString() {
        return "Ovi_User{" +
                "dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", legalGuardian='" + legalGuardian + '\'' +
                ", state=" + state.getDescription() +
                ", reason='" + reason + '\'' +
                '}';
    }

}
