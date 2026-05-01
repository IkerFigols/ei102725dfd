package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.State;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Selection {
    private String idSelection;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private State state;
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
                ", idPap='" + idPap + '\'' +
                ", idAsReq='" + idAsReq + '\'' +
                '}';
    }
}