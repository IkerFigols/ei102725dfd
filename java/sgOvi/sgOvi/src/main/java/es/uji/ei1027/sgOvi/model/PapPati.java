package es.uji.ei1027.sgOvi.model;

public class PapPati {
    private String dni;
    private String address;
    private String type;
    private boolean available;
    private String training;
    private String document;
    private String reason;
    private String state;
    private String papPatiPreferences;

    public PapPati() {
        state = "PENDING";
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", available=" + available +
                ", training='" + training + '\'' +
                ", document='" + document + '\'' +
                ", reason='" + reason + '\'' +
                ", accepted=" + state +
                ", papPatiPreferences='" + papPatiPreferences + '\'' +
                '}';
    }
}
