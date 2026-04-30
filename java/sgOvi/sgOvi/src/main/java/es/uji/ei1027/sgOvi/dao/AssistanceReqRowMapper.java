package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.Assistance_Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class AssistanceReqRowMapper implements RowMapper<Assistance_Request> {
    public Assistance_Request mapRow(ResultSet rs, int rowNum) throws SQLException {
       Assistance_Request assistanceRequest = new Assistance_Request();
       assistanceRequest.setIdAsReq(rs.getString("idAsReq"));
       assistanceRequest.setDescription(rs.getString("description"));
       assistanceRequest.setDate(rs.getObject("data", LocalDate.class));
       assistanceRequest.setReason(rs.getString("reason"));
       assistanceRequest.setState(rs.getString("state"));
       assistanceRequest.setIdOviUser(rs.getString("idOviUser"));
       assistanceRequest.setExperience(rs.getInt("experience"));
       assistanceRequest.setDrivingLicense(rs.getBoolean("drivingLicense"));
       assistanceRequest.setCity(rs.getString("city"));
       assistanceRequest.setProvince(rs.getString("province"));
       assistanceRequest.setShiftPreference(rs.getString("shiftPreference"));
       assistanceRequest.setAge(rs.getInt("age"));
       return assistanceRequest;
    }
}
