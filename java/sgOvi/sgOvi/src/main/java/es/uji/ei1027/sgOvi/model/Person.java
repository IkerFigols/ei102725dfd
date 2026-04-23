package es.uji.ei1027.sgOvi.model;

public class Person {
    private String dni;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String gender;
    private String password;
    private String city;
    private String province;

    private String preference; // CAMBIAR NOMBRE
    private boolean dataProtection;
    private String userType; // OVI, PAP, TECH, INS



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

    public String getCity(){return city;}

    public String getProvince(){return province;}

    public String getUserType(){return userType;}

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

    public void setUserType(String userType) {this.userType = userType;}

    public void setPassword(String password){this.password = password;}
    public void setCity(String city) { this.city = city; }
    public void setProvince(String province) { this.province = province;}
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
                ", city = '"+ city +'\''+
                ", province = '"+province+'\''+
                ", userType = '"+userType+'\''+
                '}';
    }
}