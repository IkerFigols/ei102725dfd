package es.uji.ei1027.sgOvi.model;

import es.uji.ei1027.sgOvi.model.enums.ShiftType;
import es.uji.ei1027.sgOvi.model.enums.StaffType;
import es.uji.ei1027.sgOvi.model.enums.State;

public class PapPati {
    private String dni;
    private String address;
    private StaffType type;
    private boolean available;
    private String training;
    private String document;
    private String reason;
    private State state;
    private Integer experience;
    private boolean drivingLicense;
    private ShiftType shift;

    public PapPati() {
        state = State.fromString("PENDING");
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setDrivingLicense(boolean drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void setShift(String shift) {
        this.shift = ShiftType.fromString(shift);
    }

    public Integer getExperience() {
        return experience;
    }

    public boolean getDrivingLicense() {
        return drivingLicense;
    }

    public ShiftType getShift() {
        return shift;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StaffType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = StaffType.fromString(type);
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public State getState() {
        return state;
    }

    public void setState(String state) {
        this.state = State.fromString(state);
    }

    @Override
    public String toString() {
        return "PapPati{" +
                "dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type.name() + '\'' +
                ", available=" + available +
                ", training='" + training + '\'' +
                ", document='" + document + '\'' +
                ", reason='" + reason + '\'' +
                ", state='" + state.getDescription() + '\'' +
                ", experience=" + experience +
                ", drivingLicense=" + drivingLicense +
                ", shift='" + shift.getDescription() + '\'' +
                '}';
    }
}
