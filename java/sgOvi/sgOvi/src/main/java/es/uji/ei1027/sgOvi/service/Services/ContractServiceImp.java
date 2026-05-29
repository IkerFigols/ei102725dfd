package es.uji.ei1027.sgOvi.service.Services;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.ContractDao;
import es.uji.ei1027.sgOvi.dao.OviUserDao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import es.uji.ei1027.sgOvi.model.Contract;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.DTOs.ContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
@Service
public class ContractServiceImp implements ContractService{

    @Autowired
    ContractDao contractDao;

    @Autowired
    AssistanceReqDao assistanceReqDao;


    @Override
    public Contract getContract(String idContract) {
        return contractDao.getContract(idContract);
    }

    @Override
    public void updateContract(Contract contract) {
        contractDao.updateContract(contract);
    }

    @Override
    public void deleteContract(String idContract) {
        contractDao.deleteContract(idContract);
    }

    @Override
    public void addContract(Contract contract) {
        contractDao.addContract(contract);
    }

    @Override
    public List<ContractDTO> listContractPerson(String dni) {
        return contractDao.getContractsByPerson(dni);
    }


    @Override
    public void updateStateAp(String idAsReq, State state) {
        Assistance_Request ap = assistanceReqDao.getAssistanceRequest(idAsReq);
        ap.setState(state.name());
        assistanceReqDao.updateAssistanceRequest(ap);
    }

    @Override
    public ContractDTO getContractByAp(String idAsReq) {
        return contractDao.getContractByAP(idAsReq);
    }

    public List<ContractDTO> sortContracts(List<ContractDTO> contracts, String sort) {
        if (sort == null) return contracts;
        switch (sort) {
            case "dateAsc":
                // Ordena por fecha más antigua primero
                contracts.sort(Comparator.comparing(dto -> dto.getContract().getStartDate(),
                        Comparator.nullsLast(Comparator.naturalOrder())));
                break;
            case "dateDesc":
                // Ordena por fecha más reciente primero
                contracts.sort(Comparator.comparing((ContractDTO dto) -> dto.getContract().getStartDate(),
                        Comparator.nullsLast(Comparator.reverseOrder())));
                break;
        }
        return contracts;
    }
}

