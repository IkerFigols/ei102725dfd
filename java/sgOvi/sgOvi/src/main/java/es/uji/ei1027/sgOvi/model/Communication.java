package es.uji.ei1027.sgOvi.model;
import java.time.LocalDate;

public class Communication {
    private String idCommunication;
    private LocalDate data;
    private String information;

    public String getIdCommunication() {
        return idCommunication;
    }

    public LocalDate getData() {
        return data;
    }

    public String getInformation() {
        return information;
    }

    public void setIdCommunication(String idCommunication) {
        this.idCommunication = idCommunication;
    }

    public void setData(LocalDate date) {
        this.data = date;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Communication{" +
                "idCommunication='" + idCommunication + '\'' +
                ", data=" + data +
                ", information='" + information + '\'' +
                '}';
    }
}

