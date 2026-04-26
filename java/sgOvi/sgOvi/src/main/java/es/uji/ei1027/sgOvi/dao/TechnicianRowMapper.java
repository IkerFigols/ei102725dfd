package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Technician;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TechnicianRowMapper implements RowMapper<Technician> {
        public Technician mapRow(ResultSet rs, int rowNum) throws SQLException {
            Technician technician = new Technician();
            technician.setDni(rs.getString("dni"));
            return technician;
        }
}