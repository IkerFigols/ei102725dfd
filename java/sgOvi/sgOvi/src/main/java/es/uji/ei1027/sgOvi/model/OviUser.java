package es.uji.ei1027.sgOvi.model;
import es.uji.ei1027.sgOvi.model.enums.State;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class OviUser {
    private String dni;
    private String address;
    private String legalGuardian;
    private State state;
    private String reason;

    private LocalDate birthdayDate;


    public OviUser(){
        state = State.fromString("PENDING");
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
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
