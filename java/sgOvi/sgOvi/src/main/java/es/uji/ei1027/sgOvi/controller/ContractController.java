package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.controller.exception.OviException;
import es.uji.ei1027.sgOvi.dao.ContractDao;
import es.uji.ei1027.sgOvi.dao.SelectionDao;
import es.uji.ei1027.sgOvi.model.*;
import es.uji.ei1027.sgOvi.model.enums.State;
import es.uji.ei1027.sgOvi.service.Services.AssistanceRequestService;
import es.uji.ei1027.sgOvi.service.Services.CodeGenerator;
import es.uji.ei1027.sgOvi.service.DTOs.ContractDTO;
import es.uji.ei1027.sgOvi.service.Services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/Contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private AssistanceRequestService assistanceRequestService;

    @Autowired
    private CodeGenerator codeGenerator;


    @RequestMapping("/list")
    public String listContracts(Model model, HttpSession session,
                                @RequestParam(value="sort", defaultValue = "dateDesc") String sort) {
        Person user = (Person) session.getAttribute("user");
        model.addAttribute("contracts", contractService.sortContracts(contractService.listContractPerson(user.getDni()),sort));
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
        Person user = (Person) session.getAttribute("user");
        Selection selection = assistanceRequestService.getSelection(idSelection);
        if(selection == null) {
            throw new OviException("No existe la selección","Selección no encontrada");
        }
        Assistance_Request ap = assistanceRequestService.getAssistanceRequest(selection.getIdAsReq());
        if(ap == null)
            throw new OviException("No existe la solicitud", "Solicitud no encontrada");

        if(!ap.getIdOviUser().equals(user.getDni()))
            throw new OviException("No puedes crear este contrato ya que no te pertenece", "Accceso no autorizado");

        Contract contract = new Contract();
        contract.setIdSelection(idSelection);
        model.addAttribute("idAsReq", selection.getIdAsReq());
        model.addAttribute("contract", contract);
        return "Contracts/add";
    }
    @RequestMapping(value="/add/{idSelection}", method = RequestMethod.POST)
    public String processAddSubmit(@PathVariable String idSelection,
                                   @ModelAttribute("contract") Contract contract,
                                   Model model,
                                   BindingResult bindingResult, RedirectAttributes flash) {

        contract.setIdSelection(idSelection);

        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        Selection selection = assistanceRequestService.getSelection(idSelection);
        String idAsReq = selection.getIdAsReq();
        if (bindingResult.hasErrors()) {
            model.addAttribute("idAsReq", idAsReq);
            return "Contracts/add";
        }
        String idContract =codeGenerator.generateCode("CON");
        contract.setIdContract(idContract);
        contract.setDocument(generateDocumentName(contract.getIdSelection()));
        contractService.updateStateAp(idAsReq, State.CLOSED_WITH_CONTRACT);
        assistanceRequestService.rejectOtherCandidates(idAsReq, selection.getIdPapPati());
        contractService.addContract(contract);

        flash.addFlashAttribute("lista","/Assistance_Request/apRequestList");
        flash.addFlashAttribute("mensaje","Se ha creado el contrato correctamente");
        return "redirect:/actionConfirmation";
    }
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable String id, HttpSession session) {
        if(contractService.getContract(id) == null)
            throw new OviException("El contrato no existe","Contrato no encontrado");
        Person user = (Person) session.getAttribute("user");
        if(!user.equals(assistanceRequestService.getAssistanceRequest(assistanceRequestService.getSelection(contractService.getContract(id).getIdSelection()).getIdAsReq()).getIdOviUser()))
            throw new OviException("No puedes actualizar un contrato que no te pertenece","Acceso no autorizado");
        model.addAttribute("contract", contractService.getContract(id));
        return "Contracts/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("contract") Contract contract,
                                      BindingResult bindingResult, RedirectAttributes flash) {

        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Contracts/update";
        }
        Contract contractOriginal = contractService.getContract(contract.getIdContract());

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
        contractService.updateContract(contract);
        flash.addFlashAttribute("lista","/Assistance_Request/apRequestList");
        flash.addFlashAttribute("mensaje","Se ha actualizado el contrato correctamente");
        return "redirect:/actionConfirmation";
    }

}