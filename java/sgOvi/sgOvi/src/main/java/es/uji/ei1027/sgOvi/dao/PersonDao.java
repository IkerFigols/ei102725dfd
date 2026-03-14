package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añade la persona */
    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?, ?, ?)",
                person.getDni(),
                person.getName(),
                person.getSurname(),
                person.getPhoneNumber(),
                person.getEmail(),
                person.getGender()
        );
    }

    /* Borra la persona */
    public void deletePerson(String dni) {
        jdbcTemplate.update("DELETE FROM Person WHERE dni = '" + dni + "'");
    }

    /* Actualiza los atributos de una persona */
    public void updatePerson(Person person) {
        String sql = "UPDATE Person SET name = ?, surname = ?, phoneNumber = ?, email = ?, gender = ? WHERE dni = ?";

        jdbcTemplate.update(sql,
                person.getName(),
                person.getSurname(),
                person.getPhoneNumber(),
                person.getEmail(),
                person.getGender(),
                person.getDni()
        );
    }
    /* Obtiene una persona concreta */
    public Person getPerson(String dni) {
        try {
            Person p = jdbcTemplate.queryForObject(
                    "SELECT * FROM Person WHERE dni = '" + dni + "'",
                    new PersonRowMapper());
            return p;

        } catch (DataAccessException e) {
            return new Person();
        }
    }

    /* Obtiene todas las personas */
    public List<Person> getPersons() {
        try {
            return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Person>();
        }
    }
}