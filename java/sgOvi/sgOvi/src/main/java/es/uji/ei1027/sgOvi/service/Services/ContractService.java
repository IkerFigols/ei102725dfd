package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.Contract;

import java.util.List;

public interface ContractService {
    public Contract getContract(String idContract);
    public void updateContract(Contract contract);
    public void deleteContract(String idContract);
    public void addContract(Contract contract);

    public void updateStateAp(String idAsReq, String state);
    public List<ContractDTO> listContractPerson(String dni, String idAsReq);
    public List<ContractDTO> listContractByAp(String idAsReq);
}
