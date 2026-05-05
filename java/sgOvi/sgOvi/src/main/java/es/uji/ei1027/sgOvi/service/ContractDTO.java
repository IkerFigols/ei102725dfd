package es.uji.ei1027.sgOvi.service;

import es.uji.ei1027.sgOvi.model.Contract;

public class ContractDTO {
    private Contract contract;
    private String otherPersonName;

    public ContractDTO(Contract contract, String otherPersonName) {
        this.contract = contract;
        this.otherPersonName = otherPersonName;
    }

    // Getters y Setters
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public String getOtherPersonName() { return otherPersonName; }
    public void setOtherPersonName(String otherPersonName) { this.otherPersonName = otherPersonName; }
}
