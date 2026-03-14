package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.Ovi_User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class OviUserRowMapper implements RowMapper<Ovi_User> {
    public Ovi_User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ovi_User oviUser = new Ovi_User();
        oviUser.setDni(rs.getString("dni"));
        oviUser.setAccepted(rs.getBoolean("accepted"));
        oviUser.setBirthdayDate(rs.getObject("birthdayDate", LocalDate.class));
        oviUser.setUserPreferences(rs.getString("userPreferences"));
        oviUser.setPassword(rs.getString("password"));
        oviUser.setAddress(rs.getString("address"));
        oviUser.setReason(rs.getString("reason"));
        oviUser.setLegalGuardian(rs.getString("legalGuardian"));
        return oviUser;
    }
}
