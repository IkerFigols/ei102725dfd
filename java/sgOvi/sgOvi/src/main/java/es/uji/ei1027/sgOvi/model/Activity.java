package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.ActivityType;

import java.time.LocalDate;

public class Activity {
    private String idActivity;
    private ActivityType type;
    private String tittle;
    private String description;
    private LocalDate date;
    private String address;
    private Integer Capacity;   //Integer para que pueda admitir nulos
    private String sponsor;
    private String idInstructor;

    public String getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(String idActivity) {
        this.idActivity = idActivity;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ActivityType.fromString(type);
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public void setCapacity(Integer capacity) {
        Capacity = capacity;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(String idInstructor) {
        this.idInstructor = idInstructor;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "idActivity='" + idActivity + '\'' +
                ", activityType='" + type.getDescription() + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", Capacity=" + Capacity +
                ", sponsor='" + sponsor + '\'' +
                ", idInstructor='" + idInstructor + '\'' +
                '}';
    }
}
