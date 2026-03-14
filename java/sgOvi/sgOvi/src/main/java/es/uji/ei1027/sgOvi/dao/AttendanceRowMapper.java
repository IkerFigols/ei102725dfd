package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Attendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AttendanceRowMapper implements RowMapper<Attendance> {
    public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setIdAtt(rs.getString("idAtt"));
        attendance.setIdOviUser(rs.getString("idOviUser"));
        attendance.setIdPapPati(rs.getString("idPapPati"));
        attendance.setIdActivity(rs.getString("idActivity"));
        return attendance;
    }
}