package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Contract;
import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class

ContractRowMapper implements RowMapper<Contract> {
    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();
        contract.setIdContract(rs.getString("idContract"));
        contract.setStartDate(rs.getDate("starDate"));
        contract.setEndDate(rs.getDate("endDate"));
        contract.setDocument(rs.getString("document"));
        contract.setSalary(rs.getDouble("salary"));
        contract.setSchedule(rs.getString("schedule"));
        contract.setIdSelection(rs.getString("idSelection"));

        return contract;
    }
}
