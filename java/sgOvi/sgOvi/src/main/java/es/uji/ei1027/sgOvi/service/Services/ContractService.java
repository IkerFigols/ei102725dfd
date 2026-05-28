package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.model.Contract;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.ContractDTO;

import java.util.List;

public interface ContractService {
    public Contract getContract(String idContract);
    public void updateContract(Contract contract);
    public void deleteContract(String idContract);
    public void addContract(Contract contract);

    public void updateStateAp(String idAsReq, State state);
    public List<ContractDTO> listContractPerson(String dni);
    public ContractDTO listContractByAp(String idAsReq);

}
