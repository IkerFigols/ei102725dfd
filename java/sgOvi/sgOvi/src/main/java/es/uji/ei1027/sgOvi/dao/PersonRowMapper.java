package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PersonRowMapper implements RowMapper<Person> {
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setDni(rs.getString("dni"));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setPhoneNumber(rs.getString("phoneNumber"));
        person.setEmail(rs.getString("email"));
        person.setGender(rs.getString("gender"));
        person.setPassword(rs.getString("password"));
        person.setCity(rs.getString("city"));
        person.setProvince(rs.getString("province"));
        return person;
    }
}