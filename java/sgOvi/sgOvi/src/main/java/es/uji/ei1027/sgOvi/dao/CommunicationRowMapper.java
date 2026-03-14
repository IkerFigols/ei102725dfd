package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.Communication;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CommunicationRowMapper implements RowMapper<Communication> {
    public Communication mapRow(ResultSet rs, int rowNum) throws SQLException {
        Communication communication = new Communication();
        communication.setIdCommunication(rs.getString("idCommunication"));
        communication.setData((LocalDate) rs.getObject("data"));
        communication.setInformation(rs.getString("information"));
        return communication;
    }
}
