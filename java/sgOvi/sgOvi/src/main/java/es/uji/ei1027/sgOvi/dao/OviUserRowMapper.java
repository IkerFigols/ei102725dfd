package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.OviUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class OviUserRowMapper implements RowMapper<OviUser> {
    public OviUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        OviUser oviUser = new OviUser();
        oviUser.setDni(rs.getString("dni"));
        oviUser.setState(rs.getString("state"));
        oviUser.setBirthdayDate(rs.getObject("birthdayDate", LocalDate.class));
        oviUser.setUserPreferences(rs.getString("userPreferences"));
        oviUser.setAddress(rs.getString("address"));
        oviUser.setReason(rs.getString("reason"));
        oviUser.setLegalGuardian(rs.getString("legalGuardian"));
        return oviUser;
    }
}
