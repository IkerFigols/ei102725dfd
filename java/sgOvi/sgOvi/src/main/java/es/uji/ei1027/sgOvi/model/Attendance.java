package es.uji.ei1027.sgOvi.model;

public class Attendance {
    private String idAtt;
    private String idOviUser;
    private String idPapPati;
    private String idActivity;
    private boolean attend;

    public boolean getAttend() {
        return attend;
    }

    public void setAttend(boolean attend) {
        this.attend = attend;
    }

    public String getIdAtt() {
        return idAtt;
    }

    public String getIdOviUser() {
        return idOviUser;
    }

    public String getIdPapPati() {
        return idPapPati;
    }

    public String getIdActivity() {
        return idActivity;
    }

    public void setIdAtt(String idAtt) {
        this.idAtt = idAtt;
    }

    public void setIdOviUser(String idOviUser) {
        this.idOviUser = idOviUser;
    }

    public void setIdPapPati(String idPapPati) {
        this.idPapPati = idPapPati;
    }

    public void setIdActivity(String idActivity) {
        this.idActivity = idActivity;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "idAtt='" + idAtt + '\'' +
                ", idOviUser='" + idOviUser + '\'' +
                ", idPapPati='" + idPapPati + '\'' +
                ", idActivity='" + idActivity + '\'' +
                ", attend=" + attend +
                '}';
    }
}