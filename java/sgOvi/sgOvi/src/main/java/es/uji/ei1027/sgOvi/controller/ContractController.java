package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.ContractDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.service.ContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Contracts")
public class ContractController {

    private ContractDao contractDao;
    private SelectionDao selectionDao;
    private AssistanceReqDao assistanceReqDao;
    private PersonDao personDao;

    @Autowired
    public void setDaos(ContractDao contractDao, SelectionDao selectionDao,
                        AssistanceReqDao assistanceReqDao, PersonDao personDao) {
        this.contractDao = contractDao;
        this.selectionDao = selectionDao;
        this.assistanceReqDao = assistanceReqDao;
        this.personDao = personDao;
    }

    @RequestMapping("/list")
    public String listContracts(Model model, HttpSession session) {
        Person user = (Person) session.getAttribute("user");
        String rol = (String) session.getAttribute("rol");

        if (user == null) return "redirect:/login";

        List<Contract> contracts = contractDao.getContractsByPerson(user.getDni());
        List<ContractDTO> contractListWithNames = new ArrayList<>();

        for (Contract c : contracts) {
            // Obtenemos la selección asociada al contrato
            Selection selection = selectionDao.getSelection(c.getIdSelection());
            // Obtenemos la solicitud original para saber quién es el OVI
            Assistance_Request request = assistanceReqDao.getAssistanceRequest(selection.getIdAsReq());

            String targetDni = "";
            if ("OVI_USER".equals(rol)) {
                // Si soy OVI, mi contrato es con el asistente (idPap)
                targetDni = selection.getIdPapPati();
            } else {
                // Si soy PAP_PATI, mi contrato es con el usuario (idOviUser)
                targetDni = request.getIdOviUser();
            }

            Person targetPerson = personDao.getPerson(targetDni);
            String name = (targetPerson != null) ? targetPerson.getName() : "Desconocido";

            contractListWithNames.add(new ContractDTO(c, name));
        }

        model.addAttribute("contracts", contractListWithNames);
        return "Contracts/list";
    }
}