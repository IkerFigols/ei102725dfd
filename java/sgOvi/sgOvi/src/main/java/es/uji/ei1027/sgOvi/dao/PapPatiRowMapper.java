package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.PapPati;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PapPatiRowMapper implements RowMapper<PapPati> {
    @Override
    public PapPati mapRow(ResultSet rs, int rowNum) throws SQLException {
        PapPati papPati = new PapPati();
        papPati.setDni(rs.getString("dni"));
        papPati.setAddress(rs.getString("address"));
        papPati.setType(rs.getString("type"));
        papPati.setAvailable(rs.getBoolean("available"));
        papPati.setTraining(rs.getString("training"));
        papPati.setDocument(rs.getString("document"));
        papPati.setReason(rs.getString("reason"));
        papPati.setState(rs.getString("state"));
        papPati.setExperience(rs.getInt("experience"));
        papPati.setDrivingLicense(rs.getBoolean("drivingLicense"));
        papPati.setShift(rs.getString("shift"));

        return papPati;
    }
}
