package es.uji.ei1027.sgOvi.model;

public class PapPati {
    private String dni;
    private String password;
    private String address;
    private String type;
    private boolean available;
    private String training;
    private String document;
    private String reason;
    private boolean accepted;
    private String papPatiPreferences;

    public PapPati() {}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getPapPatiPreferences() {
        return papPatiPreferences;
    }

    public void setPapPatiPreferences(String papPatiPreferences) {
        this.papPatiPreferences = papPatiPreferences;
    }

    @Override
    public String toString() {
        return "PapPati{" +
                "dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", available=" + available +
                ", training='" + training + '\'' +
                ", document='" + document + '\'' +
                ", reason='" + reason + '\'' +
                ", accepted=" + accepted +
                ", papPatiPreferences='" + papPatiPreferences + '\'' +
                '}';
    }
}
