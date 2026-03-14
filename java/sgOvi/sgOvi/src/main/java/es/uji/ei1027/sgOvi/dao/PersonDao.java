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

@Repository // En Spring els DAOs van anotats amb @Repository
public class PersonDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Afegeix la persona a la base de dades */
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

    /* Esborra la persona de la base de dades */
    public void deletePerson(String dni) {
        jdbcTemplate.update("DELETE FROM Person WHERE dni = '" + dni + "'");
    }

    /* Actualitza els atributs de la persona (excepte el dni, que és la clau primària) */
    public void updatePerson(Person person) {
        jdbcTemplate.update("UPDATE Person SET name = '" + person.getName()
                + "', surname = '" + person.getSurname()
                + "', phoneNumber = '" + person.getPhoneNumber()
                + "', email = '" + person.getEmail()
                + "', gender = '" + person.getGender()
                + "' WHERE dni = '" + person.getDni() + "'");
    }

    /* Obté la persona amb el dni donat. Torna un objeto vacío si no existe. */
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

    /* Obté totes les persones. Torna una llista buida si no n'hi ha cap. */
    public List<Person> getPersons() {
        try {
            return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Person>();
        }
    }
}