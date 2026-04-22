package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Activity;
import es.uji.ei1027.sgOvi.model.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TechnicianDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix una nova activitat a la base de dades */
    public void addTechnician(Technician technician) {
        jdbcTemplate.update(
                "INSERT INTO Technician (dni) VALUES (?)",
                technician.getDni()
        );
    }

    /* Technician no necesita updtate ya que su único campo es su CP*/
    public void updateTechnician(Technician technician) {

    }

    public void deleteTechnician(String dni) {
        jdbcTemplate.update("DELETE FROM Technician WHERE dni=?", dni);
    }

    public Technician getTechnician(String dni) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Technician WHERE dni=?",
                    new TechnicianRowMapper(),
                    dni
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Technician> getTechnicians() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Technician",
                    new TechnicianRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
