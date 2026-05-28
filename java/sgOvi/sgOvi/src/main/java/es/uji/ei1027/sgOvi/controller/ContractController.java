package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.dao.AssistanceReqDao;
import es.uji.ei1027.sgOvi.dao.ContractDao;
import es.uji.ei1027.sgOvi.dao.PersonDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.service.CodeGenerator;
import es.uji.ei1027.sgOvi.service.ContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/Contracts")
public class ContractController {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private SelectionDao selectionDao;

    @Autowired
    public void setDaos(ContractDao contractDao, SelectionDao selectionDao) {
        this.contractDao = contractDao;
        this.selectionDao = selectionDao;
    }

    private List<ContractDTO> sortContracts(List<ContractDTO> contracts, String sort) {
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

    @RequestMapping("/list")
    public String listContracts(Model model, HttpSession session,
                                @RequestParam(value="sort", defaultValue = "dateDesc") String sort) {
        Person user = (Person) session.getAttribute("user");
        model.addAttribute("contracts", sortContracts(contractDao.getContractsByPerson2(user.getDni()),sort));
        model.addAttribute("currentSort", sort);

        return "Contracts/list";
    }

    //Metodo auxiliar para generar el nombre del documento
    private String generateDocumentName(String idSelection) {
        String prefijo = idSelection.toLowerCase().replace("sel0000", "sel");
        return "/docs/" + prefijo + ".pdf";
    }
    @RequestMapping(value="/add/{idSelection}", method=RequestMethod.GET)
    public String addContract(Model model, @PathVariable String idSelection, HttpSession session) {
        if (!"OVI_USER".equals(session.getAttribute("rol"))) {
            throw new OviException("No tienes permiso para acceder a la gestión de contratos.", "Acceso no autorizado");
        }


        Contract contract = new Contract();
        contract.setIdSelection(idSelection);

        Selection selection = selectionDao.getSelection(idSelection);
        model.addAttribute("idAsReq", selection.getIdAsReq());
        model.addAttribute("contract", contract);
        return "Contracts/add";
    }
    @RequestMapping(value="/add/{idSelection}", method = RequestMethod.POST)
    public String processAddSubmit(@PathVariable String idSelection,
                                   @ModelAttribute("contract") Contract contract,
                                   Model model,
                                   BindingResult bindingResult) {

        contract.setIdSelection(idSelection);

        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        Selection selection = selectionDao.getSelection(idSelection);
        String idAsReq = selection.getIdAsReq();

        if (bindingResult.hasErrors()) {
            model.addAttribute("idAsReq", idAsReq);
            return "Contracts/add";
        }


        contract.setIdContract(codeGenerator.generateCode("CON"));
        contract.setDocument(generateDocumentName(contract.getIdSelection()));
        contractDao.addContract(contract);

        return "redirect:/Assistance_Request/papPatiSelection/" + idAsReq + "?status=success_add";
    }
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable String id, HttpSession session) {
        if (!"OVI_USER".equals(session.getAttribute("rol"))) {
            return "redirect:/Contracts/list";
        }
        model.addAttribute("contract", contractDao.getContract(id));
        return "Contracts/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("contract") Contract contract,
                                      BindingResult bindingResult) {

        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Contracts/update";
        }
        //Comprobar que se cambia algún atributo
        Contract contractOriginal = contractDao.getContract(contract.getIdContract());

        if (contractOriginal.getStartDate().equals(contract.getStartDate()) &&
                contractOriginal.getEndDate().equals(contract.getEndDate()) &&
                contractOriginal.getSalary() == contract.getSalary() &&
                contractOriginal.getSchedule().equals(contract.getSchedule())) {

            bindingResult.rejectValue("schedule", "sinCambios", "Debes modificar algún campo para poder actualizar el contrato.");
        }
        if (bindingResult.hasErrors()) {
            return "Contracts/update";
        }
        contract.setDocument(generateDocumentName(contract.getIdSelection()));

        contractDao.updateContract(contract);
        return "redirect:/Contracts/list?status=success_update";
    }

}