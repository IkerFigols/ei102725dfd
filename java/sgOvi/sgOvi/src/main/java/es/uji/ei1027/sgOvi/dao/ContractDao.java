package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Contract;
import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
public class ContractDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContract(Contract contract){
        jdbcTemplate.update(
                "INSERT INTO Contract (idContract, startDate, endDate, document, salary, schedule, idSelection) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                contract.getIdContract(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getDocument(),
                contract.getSalary(),
                contract.getSchedule(),
                contract.getIdSelection()
        );
    }

    public void updateContract(Contract contract){
        jdbcTemplate.update(
                "UPDATE Contract SET startDate=?, endDate=?, document=?, salary=?, schedule=?, idSelection=? WHERE idContract=?",
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getDocument(),
                contract.getSalary(),
                contract.getSchedule(),
                contract.getIdSelection(),
                contract.getIdContract()
        );
    }

    public void deletePapPati(String idContract){
        jdbcTemplate.update("DELEtE FROM Contract WHERE idContract=?", idContract);
    }

    public Contract getContract(String idContract){
        try{
            Contract contract = jdbcTemplate.queryForObject(
                    "SELECT * FROM Contract WHERE idContract=?",
                    new ContractRowMapper(),
                    idContract
            );
            return contract;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Contract> getContracts(){
        try{
            List<Contract> contractList = jdbcTemplate.query(
                    "SELECT * FROM Contract",
                    new ContractRowMapper()
            );
            return contractList;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
