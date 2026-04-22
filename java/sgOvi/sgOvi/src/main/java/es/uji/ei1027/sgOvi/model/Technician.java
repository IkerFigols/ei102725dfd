package es.uji.ei1027.sgOvi.model;

public class Technician {
    private String dni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "dni='" + dni + '\'' +
                '}';
    }
}
