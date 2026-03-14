package es.uji.ei1027.sgOvi.model;

public class Instructor {
    private String dni;
    private boolean expertise;

    public String getDni() {
        return dni;
    }

    public boolean getExpertise() {
        return expertise;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setExpertise(boolean expertise) {
        this.expertise = expertise;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "dni='" + dni + '\'' +
                ", expertise=" + expertise +
                '}';
    }
}