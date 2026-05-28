package es.uji.ei1027.sgOvi.service.DTOs;

import es.uji.ei1027.sgOvi.model.Contract;

public class ContractDTO {
    private Contract contract;
    private String otherPersonName;
    private String dni;

    public ContractDTO(Contract contract, String otherPersonName) {
        this.contract = contract;
        this.otherPersonName = otherPersonName;
    }
    public ContractDTO(Contract contract, String otherPersonName, String dni) {
        this.contract = contract;
        this.otherPersonName = otherPersonName;
        this.dni = dni;
    }

    // Getters y Setters
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public String getOtherPersonName() { return otherPersonName; }
    public void setOtherPersonName(String otherPersonName) { this.otherPersonName = otherPersonName; }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
