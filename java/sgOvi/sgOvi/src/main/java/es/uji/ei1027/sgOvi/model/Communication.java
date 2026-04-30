package es.uji.ei1027.sgOvi.model;
import java.time.LocalDate;

public class Communication {
    private String idCommunication;
    private LocalDate date;
    private String information;
    private String idSelection;

    public String getIdCommunication() {
        return idCommunication;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getInformation() {
        return information;
    }

    public String getIdSelection(){return idSelection;}

    public void setIdCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setIdSelection(String idSelection){this.idSelection = idSelection;}
    @Override
    public String toString() {
        return "Communication{" +
                "idCommunication='" + idCommunication + '\'' +
                ", data=" + date +
                ", information='" + information + '\'' +
                ", idSelection='" + idSelection + '\'' +
                '}';
    }
}

