package es.uji.ei1027.sgOvi.model;
import java.time.LocalDate;

public class Ovi_User {
    private String dni;
    private LocalDate birthdayDate;
    private String password;
    private String address;
    private String legalGuardian;
    private boolean accepted;
    private String reason;
    private String userPreferences;

    public Ovi_User(){
        accepted = true;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getLegalGuardian() {
        return legalGuardian;
    }

    public Boolean getAccepted() {
        return accepted;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLegalGuardian(String legalGuardian) {
        this.legalGuardian = legalGuardian;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
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
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", legalGuardian='" + legalGuardian + '\'' +
                ", accepted=" + accepted +
                ", reason='" + reason + '\'' +
                ", userPreferences='" + userPreferences + '\'' +
                '}';
    }

}
