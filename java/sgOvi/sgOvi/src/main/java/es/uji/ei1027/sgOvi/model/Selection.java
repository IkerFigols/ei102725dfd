package es.uji.ei1027.sgOvi.model;

import java.time.LocalDate;

public class Selection {
    private String idSelection;
    private LocalDate date;
    private String state;
    private String idCommunication;
    private String idPap;
    private String idAsReq;

    public String getIdSelection() {
        return idSelection;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getIdCommunication() {
        return idCommunication;
    }

    public String getIdPap() {
        return idPap;
    }

    public String getIdAsReq() {
        return idAsReq;
    }

    public void setIdSelection(String idSelection) {
        this.idSelection = idSelection;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setIdCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
    }

    public void setIdPap(String idPap) {
        this.idPap = idPap;
    }

    public void setIdAsReq(String idAsReq) {
        this.idAsReq = idAsReq;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "idSelection='" + idSelection + '\'' +
                ", date=" + date +
                ", state='" + state + '\'' +
                ", idCommunication='" + idCommunication + '\'' +
                ", idPap='" + idPap + '\'' +
                ", idAsReq='" + idAsReq + '\'' +
                '}';
    }
}