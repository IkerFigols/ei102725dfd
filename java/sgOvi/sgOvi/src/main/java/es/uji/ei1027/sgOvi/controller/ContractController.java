package es.uji.ei1027.sgOvi.controller;

import es.uji.ei1027.sgOvi.dao.ContractDao;
import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Contracts")
public class ContractController {

    private ContractDao contractDao;

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @RequestMapping("/list")
    public String listContracts(Model model, HttpSession session) {

        Person user = (Person) session.getAttribute("user");

        model.addAttribute("contracts", contractDao.getContractsByPerson(user.getDni()));

        return "Contracts/list";
    }
}