package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Contract;

public class ContractDTO {
    private Contract contract;
    private String nameOviUser;
    private String namePapPati;
    private String dni;

    public ContractDTO(Contract contract, String nameOviUser, String namePapPati, String dni) {
        this.contract = contract;
        this.nameOviUser = nameOviUser;
        this.namePapPati = namePapPati;
    }

    // Getters y Setters
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }


    public String getNameOviUser() {
        return nameOviUser;
    }

    public String getNamePapPati() {
        return namePapPati;
    }

    public void setNameOviUser(String nameOviUser) {
        this.nameOviUser = nameOviUser;
    }

    public void setNamePapPati(String namePapPati) {
        this.namePapPati = namePapPati;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
