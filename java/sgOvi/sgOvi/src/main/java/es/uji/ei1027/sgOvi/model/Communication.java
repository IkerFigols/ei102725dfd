package es.uji.ei1027.sgOvi.model;
import java.time.LocalDate;

public class Communication {
    private String idCommunication;
    private LocalDate data;
    private String information;
    private String idSelection;

    public String getIdCommunication() {
        return idCommunication;
    }

    public LocalDate getData() {
        return data;
    }

    public String getInformation() {
        return information;
    }

    public String getIdSelection(){return idSelection;}

    public void setIdCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
    }

    public void setData(LocalDate date) {
        this.data = date;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setIdSelection(String idSelection){this.idSelection = idSelection;}
    @Override
    public String toString() {
        return "Communication{" +
                "idCommunication='" + idCommunication + '\'' +
                ", data=" + data +
                ", information='" + information + '\'' +
                ", idSelection='" + idSelection + '\'' +
                '}';
    }
}

