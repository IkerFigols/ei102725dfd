package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade la asistencia a la base de datos */
    public void addAttendance(Attendance attendance) {
        jdbcTemplate.update("INSERT INTO Attendance VALUES(?, ?, ?, ?)",
                attendance.getIdAtt(),
                attendance.getIdOviUser(),
                attendance.getIdPapPati(),
                attendance.getIdActivity()
        );
    }

    /* Borra la asistencia */
    public void deleteAttendance(String idAtt) {
        jdbcTemplate.update("DELETE FROM Attendance WHERE idAtt = '" + idAtt + "'");
    }

    /* Actualiza la asistencia */
    public void updateAttendance(Attendance attendance) {
        jdbcTemplate.update("UPDATE Attendance SET idOviUser = '" + attendance.getIdOviUser()
                + "', idPapPati = '" + attendance.getIdPapPati()
                + "', idActivity = '" + attendance.getIdActivity()
                + "' WHERE idAtt = '" + attendance.getIdAtt() + "'");
    }

    /* Obtiene la asistencia de un id */
    public Attendance getAttendance(String idAtt) {
        try {
            Attendance a = jdbcTemplate.queryForObject(
                    "SELECT * FROM Attendance WHERE idAtt = '" + idAtt + "'",
                    new AttendanceRowMapper());
            return a;

        } catch (DataAccessException e) {
            return new Attendance();
        }
    }

    /* Obtiene todas las asistencias */
    public List<Attendance> getAttendances() {
        try {
            return jdbcTemplate.query("SELECT * FROM Attendance", new AttendanceRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Attendance>();
        }
    }
}