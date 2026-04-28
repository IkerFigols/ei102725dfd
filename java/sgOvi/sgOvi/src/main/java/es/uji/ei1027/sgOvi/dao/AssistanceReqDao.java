package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Assistance_Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring els DAOs van anotats amb @Repository
public class AssistanceReqDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addAssistanceRequest( Assistance_Request assistanceRequest) {
        jdbcTemplate.update("INSERT INTO Assistance_Request VALUES(?, ?, ?, ?, ?, ?)",
                assistanceRequest.getIdAsReq(),
                assistanceRequest.getData(),
                assistanceRequest.getDescription(),
                assistanceRequest.getIdOviUser(),
                assistanceRequest.getState(),
                assistanceRequest.getReason()
        );
    }


    public void deleteAssistanceRequest(String idAsReq) {
        jdbcTemplate.update("DELETE FROM Assistance_Request WHERE idAsReq LIKE '" + idAsReq + "'");
    }

    public void updateAssistanceRequest(Assistance_Request request) {
        // 1. Usa "?" para que Spring se encargue de las comillas y formatos
        String sql = "UPDATE Assistance_Request SET data=?, description=?, idOviUser=?, state=?, reason=? WHERE idAsReq=?";

        // 2. Pasa los valores en un array de objetos
        jdbcTemplate.update(sql,
                request.getData(),
                request.getDescription(),
                request.getIdOviUser(),
                request.getState(),
                request.getReason(),
                request.getIdAsReq()
        );
    }

    public Assistance_Request getAssistanceRequest(String idAsReq) {
        try {
            Assistance_Request n = jdbcTemplate.queryForObject(
                    "SELECT * FROM Assistance_Request WHERE idAsReq = '" + idAsReq + "'",
                    new AssistanceReqRowMapper());
            return n;

        } catch (DataAccessException e) {
            return null;
        }
    }
    public List<Assistance_Request> getOviAssistanceRequest(String idOviUser) {
        try {
             return  jdbcTemplate.query(
                    "SELECT * FROM Assistance_Request WHERE idOviUser = '" + idOviUser + "'",
                    new AssistanceReqRowMapper());


        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Assistance_Request>();
        }
    }


    public List<Assistance_Request> getAssistanceRequests() {
        try {
            return jdbcTemplate.query("SELECT * FROM Assistance_Request", new AssistanceReqRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Assistance_Request>();
        }
    }
}
