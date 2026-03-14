package es.uji.ei1027.sgOvi.model;

import java.time.LocalDate;

public class Assistance_Request {
    private String idAsReq;
    private LocalDate data;
    private String state;
    private String description;
    private String reason;
    private String idOviUser;

    public String getIdAsReq() {
        return idAsReq;
    }

    public LocalDate getData() {
        return data;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getReason() {
        return reason;
    }

    public String getIdOviUser() {
        return idOviUser;
    }

    public void setIdAsReq(String idAsReq) {
        this.idAsReq = idAsReq;
    }

    public void setData(LocalDate date) {
        this.data = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setIdOviUser(String idOviUser) {
        this.idOviUser = idOviUser;
    }

    @Override
    public String toString() {
        return "Assistance_Request{" +
                "idAsReq='" + idAsReq + '\'' +
                ", date=" + data +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", reason='" + reason + '\'' +
                ", idOviUser='" + idOviUser + '\'' +
                '}';
    }
}
