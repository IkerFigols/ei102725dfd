package es.uji.ei1027.sgOvi.model;

public class UserDetails {
    private String dni;
    private String password;

    public String getDni() {
        return dni;
    }

    public String getPassword() {
        return password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
