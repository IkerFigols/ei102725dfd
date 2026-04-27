package es.uji.ei1027.sgOvi.model;
import java.time.LocalDate;

public class OviUser {
    private String dni;
    private LocalDate birthdayDate;
    private String address;
    private String legalGuardian;
    private String state;
    private String reason;
    private String userPreferences;

    public OviUser(){
        state = "PENDING";
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public String getAddress() {
        return address;
    }

    public String getLegalGuardian() {
        return legalGuardian;
    }

    public String getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLegalGuardian(String legalGuardian) {
        this.legalGuardian = legalGuardian;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }

    @Override
    public String toString() {
        return "Ovi_User{" +
                "dni='" + dni + '\'' +
                ", birthdayDate=" + birthdayDate +
                ", address='" + address + '\'' +
                ", legalGuardian='" + legalGuardian + '\'' +
                ", accepted=" + state +
                ", reason='" + reason + '\'' +
                ", userPreferences='" + userPreferences + '\'' +
                '}';
    }

}
