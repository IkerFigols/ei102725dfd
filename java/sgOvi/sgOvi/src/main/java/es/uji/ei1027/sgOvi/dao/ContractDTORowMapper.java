package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Contract;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.ContractDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ContractDTORowMapper implements RowMapper<ContractDTO> {
    @Override
    public ContractDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();
        contract.setIdContract(rs.getString("idContract"));
        contract.setStartDate(rs.getObject("startDate", LocalDate.class));
        contract.setEndDate(rs.getObject("endDate",LocalDate.class));
        contract.setDocument(rs.getString("document"));
        contract.setSalary(rs.getDouble("salary"));
        contract.setSchedule(rs.getString("schedule"));
        contract.setIdSelection(rs.getString("idSelection"));


        String nameUser = rs.getString("nameUser");
        String namePap = rs.getString("namePap");
        String dni = rs.getString("dni");


        String name = (nameUser != null) ? nameUser : ((namePap != null) ? namePap : "");


        if (dni != null) {
            return new ContractDTO(contract, name, dni);
        }
        return new ContractDTO(contract, name);
    }
}
