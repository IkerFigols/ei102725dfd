package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.State;

import java.time.LocalDate;

public class Selection {
    private String idSelection;
    private LocalDate date;
    private State state;
    private String idCommunication;
    private String idPap;
    private String idAsReq;

    public String getIdSelection() {
        return idSelection;
    }

    public LocalDate getDate() {
        return date;
    }

    public State getState() {
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
        this.state = State.fromString(state);
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
                ", state='" + state.getDescription() + '\'' +
                ", idCommunication='" + idCommunication + '\'' +
                ", idPap='" + idPap + '\'' +
                ", idAsReq='" + idAsReq + '\'' +
                '}';
    }
}