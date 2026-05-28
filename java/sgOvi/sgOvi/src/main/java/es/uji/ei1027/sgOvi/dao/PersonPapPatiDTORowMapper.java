package es.uji.ei1027.sgOvi.dao;

import es.uji.ei1027.sgOvi.model.PapPati;
import es.uji.ei1027.sgOvi.model.Person;
import es.uji.ei1027.sgOvi.service.DTOs.PersonPapPatiDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PersonPapPatiDTORowMapper  implements RowMapper<PersonPapPatiDTO> {

    @Override
    public PersonPapPatiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
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
        person.setBirthdayDate(rs.getObject("birthdayDate", LocalDate.class));

        PapPati papPati = new PapPati();
        papPati.setDni(rs.getString("dni"));
        papPati.setAddress(rs.getString("address"));
        papPati.setType(rs.getString("type"));
        papPati.setAvailable(rs.getBoolean("available"));
        papPati.setTraining(rs.getString("training"));
        papPati.setDocument(rs.getString("document"));
        papPati.setReason(rs.getString("reason"));
        papPati.setState(rs.getString("state"));
        papPati.setExperience(rs.getInt("experience"));
        papPati.setDrivingLicense(rs.getBoolean("drivingLicense"));
        papPati.setShift(rs.getString("shift"));

        PersonPapPatiDTO  personPapPatiDTO= new PersonPapPatiDTO();
        personPapPatiDTO.setPapPati(papPati);
        personPapPatiDTO.setPerson(person);
        return personPapPatiDTO;
    }
}
