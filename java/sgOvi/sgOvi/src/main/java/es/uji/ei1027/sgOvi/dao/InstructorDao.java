package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade el instructor a la base de datos */
    public void addInstructor(Instructor instructor) {
        jdbcTemplate.update("INSERT INTO Instructor VALUES(?, ?)",
                instructor.getDni(),
                instructor.getExpertise()
        );
    }

    /* Borra el instructor */
    public void deleteInstructor(String dni) {
        jdbcTemplate.update("DELETE FROM Instructor WHERE dni = '" + dni + "'");
    }

    /* Actualzia el instructor */
    public void updateInstructor(Instructor instructor) {
        jdbcTemplate.update("UPDATE Instructor SET expertise = " + instructor.getExpertise()
                + " WHERE dni = '" + instructor.getDni() + "'");
    }

    /* Obtiene un instructor */
    public Instructor getInstructor(String dni) {
        try {
            Instructor i = jdbcTemplate.queryForObject(
                    "SELECT * FROM Instructor WHERE dni = '" + dni + "'",
                    new InstructorRowMapper());
            return i;

        } catch (DataAccessException e) {
            return new Instructor();
        }
    }

    /* Obtiene todos los instructores */
    public List<Instructor> getInstructors() {
        try {
            return jdbcTemplate.query("SELECT * FROM Instructor", new InstructorRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Instructor>();
        }
    }
}