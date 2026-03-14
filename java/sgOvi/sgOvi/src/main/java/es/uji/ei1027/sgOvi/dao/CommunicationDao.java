package es.uji.ei1027.sgOvi.dao;
import es.uji.ei1027.sgOvi.model.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring els DAOs van anotats amb @Repository
public class CommunicationDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addCommunication( Communication communication) {
        jdbcTemplate.update("INSERT INTO Communication VALUES(?, ?, ?)",
                communication.getIdCommunication(),
                communication.getData(),
                communication.getInformation()
        );
    }


    public void deleteCommunication(String idCommunication) {
        jdbcTemplate.update("DELETE FROM Communication WHERE idCommunication LIKE " + idCommunication + "'");
    }

    public void updateCommunication(Communication communication) {
        jdbcTemplate.update("UPDATE Communication SET data = "+ communication.getData() + ", information = " + communication.getInformation() + " WHERE idCommunication LIKE " + communication.getIdCommunication() + "'");
    }

    public Communication getCommunication(String idCommunication) {
        try {
            Communication n = jdbcTemplate.queryForObject(
                    "SELECT * FROM Communication WHERE idCommunication = '" + idCommunication + "'",
                    new CommunicationRowMapper());
            return n;

        } catch (DataAccessException e) {
            return new Communication();
        }
    }

    public List<Communication> getCommunications() {
        try {
            return jdbcTemplate.query("SELECT * FROM Communication", new CommunicationRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Communication>();
        }
    }
}
