package es.uji.ei1027.sgOvi.model;

public class Person {
    private String dni;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String gender;
    private String preference;
    private boolean dataProtection;
    private String password;
    private String location;

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPreference(){return preference;}

    public boolean getDataProtection(){return dataProtection;}

    public String getPassword(){return password;}

    public String getLocation(){return location;}

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPreference(String preference){this.preference = preference;}

    public void setDataProtection(boolean dataProtection){this.dataProtection = dataProtection;}

    public void setPassword(String password){this.password = password;}
    public void setLocation(String location) { this.location = location; }
    @Override
    public String toString() {
        return "Person{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", preference ='"+preference+'\''+
                ", dataProtection = '"+dataProtection+ '\''+
                ", password = '"+password+'\''+
                ", location = '"+location+'\''+
                '}';
    }
}