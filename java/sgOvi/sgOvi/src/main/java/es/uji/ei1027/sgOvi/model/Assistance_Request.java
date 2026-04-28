package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.ShiftType;
import es.uji.ei1027.sgOvi.model.enums.State;

import java.time.LocalDate;

public class Assistance_Request {
    private String idAsReq;
    private LocalDate data;
    private State state;
    private String description;
    private String reason;
    private String idOviUser;
    private Integer experience;
    private Boolean drivingLicense;
    private String city;
    private String province;
    private ShiftType shiftPreference;
    private Integer age;

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setDrivingLicense(Boolean drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setShiftPreference(String shiftPreference) {
        this.shiftPreference = ShiftType.fromString(shiftPreference);
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperience() {
        return experience;
    }

    public Boolean getDrivingLicense() {
        return drivingLicense;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public ShiftType getShiftPreference() {
        return shiftPreference;
    }

    public Integer getAge() {
        return age;
    }

    public String getIdAsReq() {
        return idAsReq;
    }

    public LocalDate getData() {
        return data;
    }

    public State getState() {
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
        this.state = State.fromString(state);
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
                ", data=" + data +
                ", state='" + state.getDescription() + '\'' +
                ", description='" + description + '\'' +
                ", reason='" + reason + '\'' +
                ", idOviUser='" + idOviUser + '\'' +
                ", experience=" + experience +
                ", drivingLicense=" + drivingLicense +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", shiftPreference='" + shiftPreference.getDescription() + '\'' +
                ", age=" + age +
                '}';
    }
}
